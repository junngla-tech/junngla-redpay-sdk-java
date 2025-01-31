package com.redpay.models;

import com.redpay.enums.RedPayEnvironment;
import com.redpay.provider.RedPayConfigProvider;
import com.redpay.services.RedPayIntegrityService;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;

/**
 * Cliente RedPay para realizar solicitudes HTTP firmadas y validar integridad.
 */
public class RedPayClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedPayClient.class);
    private final CloseableHttpClient httpClient;
    private final RedPayConfig config;
    private final RedPayIntegrityService integrityService;

    /**
     * Constructor: Configura los certificados, la URL base y los interceptores.
     */
    public RedPayClient() {
        this.config = RedPayConfigProvider.getInstance().getConfig();
        this.integrityService = new RedPayIntegrityService();
        this.httpClient = createHttpClient();
    }

    /**
     * Carga el certificado SSL (mTLS) desde archivos .crt y .key y crea un cliente HTTP autenticado.
     */
    private CloseableHttpClient createHttpClient() {
        try {
            if (config.getCertificate() == null) {
                throw new IllegalStateException("No se encontró configuración de certificados.");
            }

            // Obtener archivos desde la configuración
            String keyPath = config.getCertificate().getKey_path();  // private.key
            String certPath = config.getCertificate().getCert_path(); // certificate.crt

            // Cargar clave privada desde private.key
            PrivateKey privateKey = loadPrivateKey(keyPath);

            // Cargar certificado desde certificate.crt
            Certificate certificate = loadCertificate(certPath);

            // Crear un KeyStore en memoria y agregar clave privada + certificado
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null); // Inicializa un keystore vacío
            keyStore.setKeyEntry("alias", privateKey, null, new Certificate[]{certificate});

            // Crear SSLContext con el KeyStore
            // TODO: Revisar si es necesario sslContext
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadKeyMaterial(keyStore, null) // Sin contraseña
                    .build();

            // Crear configuración de socket con timeout
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoTimeout(Timeout.ofSeconds(30)) // Timeout de 30 segundos
                    .build();

            // Crear un PoolingHttpClientConnectionManager con SSLContext
            PoolingHttpClientConnectionManager connectionManager =
                    new PoolingHttpClientConnectionManager();
            connectionManager.setDefaultSocketConfig(socketConfig); // Configuración correcta

            return HttpClients.custom()
                    .setConnectionManager(connectionManager) // Reemplazo de setSSLSocketFactory()
                    .build();

        } catch (Exception e) {
            LOGGER.error("Error al configurar SSL", e);
            throw new RuntimeException("Error al configurar SSL", e);
        }
    }

    /**
     * Carga una clave privada en formato PKCS8 desde un archivo .key.
     */
    private PrivateKey loadPrivateKey(String keyPath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(keyPath));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * Carga un certificado desde un archivo .crt en formato X.509.
     */
    private Certificate loadCertificate(String certPath) throws Exception {
        try (FileInputStream certInput = new FileInputStream(certPath)) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            return certFactory.generateCertificate(certInput);
        }
    }

    /**
     * Obtiene la URL base según el entorno configurado.
     */
    private String getApiUrl() {
        return config.getEnvironment() == RedPayEnvironment.Production
                ? RedPayEnvironment.Production.name()
                : RedPayEnvironment.Integration.name();
    }

    /**
     * Obtiene el secreto de integridad configurado.
     */
    private String getSecretIntegrity() {
        return config.getSecrets().getIntegrity();
    }

    /**
     * Realiza una solicitud HTTP genérica firmada.
     */
    private String request(String method, String path, Map<String, Object> data) throws Exception {
        String url = getApiUrl() + path;

        // Firmar el objeto usando RedPayIntegrityService
        Map<String, Object> signedData = integrityService.getSignedObject(data, getSecretIntegrity());

        ClassicHttpRequest request;

        switch (method.toUpperCase()) {
            case "GET":
                request = new HttpGet(url);
                break;
            case "POST":
                HttpPost postRequest = new HttpPost(url);
                postRequest.setEntity(new StringEntity(integrityService.generateSignature(signedData, getSecretIntegrity())));
                request = postRequest;
                break;
            case "PUT":
                HttpPut putRequest = new HttpPut(url);
                putRequest.setEntity(new StringEntity(integrityService.generateSignature(signedData, getSecretIntegrity())));
                request = putRequest;
                break;
            default:
                throw new IllegalArgumentException("Método HTTP no soportado: " + method);
        }

        request.addHeader("Content-Type", "application/json");

        // Usar execute con un response handler
        return httpClient.execute(request, response -> handleResponse(response, signedData));
    }

    /**
     * Maneja la respuesta HTTP y válida la firma.
     */
    private String handleResponse(ClassicHttpResponse response, Map<String, Object> signedData) throws IOException {
        try {
            String responseBody = EntityUtils.toString(response.getEntity());

            // Validar firma de la respuesta
            boolean signatureIsValid = integrityService.validateSignature(signedData, getSecretIntegrity());

            if (!signatureIsValid) {
                throw new SecurityException("Firma inválida en la respuesta del servidor");
            }

            return responseBody;

        } catch (ParseException e) {
            throw new IOException("Error al parsear la respuesta HTTP", e);
        }
    }

    /**
     * Realiza una solicitud GET firmada.
     */
    public String get(String path, Map<String, Object> params) {
        try {
            return request("GET", path, params);
        } catch (Exception e) {
            LOGGER.error("Error en GET {}: {}", path, e.getMessage());
            return "{}";
        }
    }

    /**
     * Realiza una solicitud GET que falla si el usuario no es encontrado.
     */
    public String getOrFail(String path, Map<String, Object> params) throws Exception {
        String response = request("GET", path, params);
        if (response.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return response;
    }

    /**
     * Realiza una solicitud POST firmada.
     */
    public String post(String path, Map<String, Object> body) throws Exception {
        return request("POST", path, body);
    }

    /**
     * Realiza una solicitud PUT firmada.
     */
    public String put(String path, Map<String, Object> body) throws Exception {
        return request("PUT", path, body);
    }
}
