package com.redpay.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpay.config.ConstantsRedPay;
import com.redpay.enums.RedPayEnvironment;
import com.redpay.exceptions.ApiError;
import com.redpay.exceptions.InvalidSignatureError;
import com.redpay.provider.RedPayConfigProvider;
import com.redpay.services.RedPayIntegrityService;

/**
 * Cliente RedPay para realizar solicitudes HTTP firmadas y validar la
 * integridad de las respuestas.
 * <p>
 * Esta clase se encarga de configurar un cliente HTTP seguro (mTLS) utilizando
 * certificados, firmar las solicitudes con un servicio de integridad y validar
 * la respuesta del servidor.
 * </p>
 */
public class RedPayClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedPayClient.class);

    /**
     * Cliente HTTP utilizado para realizar las solicitudes.
     */
    private final CloseableHttpClient httpClient;

    /**
     * Configuración de RedPay obtenida a través de un proveedor de
     * configuración.
     */
    private final RedPayConfig config;

    /**
     * Servicio para la generación y validación de firmas de integridad.
     */
    private final RedPayIntegrityService integrityService;

    /**
     * Constructor que inicializa la configuración, el servicio de integridad y
     * el cliente HTTP.
     */
    public RedPayClient() {
        this.config = RedPayConfigProvider.getInstance().getConfig();
        this.integrityService = new RedPayIntegrityService();
        this.httpClient = createHttpClient();
    }

    /**
     * Crea y configura un cliente HTTP seguro (mTLS) utilizando certificados.
     *
     * @return Instancia de {@link CloseableHttpClient} configurada para mTLS.
     */
    private CloseableHttpClient createHttpClient() {
        if (config.getCertificate() == null) {
            throw new IllegalStateException("No se encontró configuración de certificados.");
        }

        String keyPath = config.getCertificate().getKey_path();
        String certPath = config.getCertificate().getCert_path();

        try {
            PrivateKey privateKey = loadPrivateKey(keyPath);
            Certificate certificate = loadCertificate(certPath);
            KeyStore keyStore = createKeyStore(privateKey, certificate);
            SSLContext sslContext = createSSLContext(keyStore);
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslSocketFactory)
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .build();

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            SocketConfig socketConfig = SocketConfig.custom()
                    .setSoTimeout(Timeout.ofSeconds(30))
                    .build();
            connectionManager.setDefaultSocketConfig(socketConfig);

            return HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error al configurar SSL", e);
            throw new RuntimeException("Error al configurar SSL", e);
        }
    }

    /**
     * Crea un KeyStore en memoria y almacena la clave privada junto con el
     * certificado.
     *
     * @param privateKey La clave privada a almacenar.
     * @param certificate El certificado a almacenar.
     * @return Un KeyStore configurado con la clave y el certificado.
     * @throws Exception Si ocurre algún error al crear el KeyStore.
     */
    private KeyStore createKeyStore(PrivateKey privateKey, Certificate certificate) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry("alias", privateKey, null, new Certificate[]{certificate});
        return keyStore;
    }

    /**
     * Crea un SSLContext utilizando el KeyStore proporcionado.
     *
     * @param keyStore El KeyStore que contiene la clave privada y el
     * certificado.
     * @return Un SSLContext configurado para mTLS.
     * @throws Exception Si ocurre algún error al construir el SSLContext.
     */
    private SSLContext createSSLContext(KeyStore keyStore) throws Exception {
        return SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, null) // Sin contraseña
                .loadTrustMaterial(null, (chain, authType) -> true)
                .build();
    }

    /**
     * Carga la clave privada en formato PKCS#1 (-----BEGIN RSA PRIVATE
     * KEY-----) utilizando BouncyCastle.
     *
     * @param keyPath Ruta al archivo de la clave privada.
     * @return La clave privada cargada.
     * @throws Exception Si ocurre algún error al cargar o procesar la clave.
     */
    private PrivateKey loadPrivateKey(String keyPath) throws Exception {
        String keyContent = new String(Files.readAllBytes(Paths.get(keyPath)), StandardCharsets.UTF_8);
        if (!keyContent.contains("-----BEGIN RSA PRIVATE KEY-----")) {
            throw new IllegalArgumentException("Se esperaba una clave en formato PKCS#1");
        }
        // Agregar BouncyCastle si aún no está agregado
        Security.addProvider(new BouncyCastleProvider());
        try (PEMParser pemParser = new PEMParser(new StringReader(keyContent))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            if (object instanceof PEMKeyPair keyPair) {
                return converter.getKeyPair(keyPair).getPrivate();
            } else if (object instanceof PrivateKeyInfo keyInfo) {
                return converter.getPrivateKey(keyInfo);
            } else {
                throw new IllegalArgumentException("Formato de clave no soportado");
            }
        }
    }

    /**
     * Carga un certificado en formato X.509 desde un archivo .crt.
     *
     * @param certPath Ruta al archivo del certificado.
     * @return El certificado cargado.
     * @throws Exception Si ocurre algún error al leer o procesar el
     * certificado.
     */
    private Certificate loadCertificate(String certPath) throws Exception {
        try (FileInputStream certInput = new FileInputStream(certPath)) {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            return certFactory.generateCertificate(certInput);
        }
    }

    /**
     * Obtiene la URL base de la API según el entorno configurado (Producción o
     * Integración).
     *
     * @return La URL base correspondiente.
     */
    private String getApiUrl() {
        return config.getEnvironment() == RedPayEnvironment.Production
                ? ConstantsRedPay.API_URL_PRODUCTION
                : ConstantsRedPay.API_URL_INTEGRATION;
    }

    /**
     * Obtiene el secreto utilizado para la firma de integridad.
     *
     * @return El secreto de integridad configurado.
     */
    private String getSecretIntegrity() {
        return config.getSecrets().getIntegrity();
    }

    /**
     * Realiza una solicitud HTTP genérica firmada utilizando el método
     * especificado.
     *
     * @param method Método HTTP ("GET", "POST", "PUT").
     * @param path Ruta del endpoint.
     * @param data Datos a enviar en la solicitud.
     * @return La respuesta en formato JSON.
     * @throws Exception Si ocurre algún error durante la solicitud o firma.
     */
    private String request(String method, String path, Map<String, Object> data) throws Exception {
        String url = getApiUrl() + path;
        ObjectMapper mapper = new ObjectMapper();

        // Configurar el mapper para omitir valores nulos al serializar
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Map<String, Object> filteredData = data.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // Firmar los datos utilizando el servicio de integridad
        Map<String, Object> signedData = integrityService.getSignedObject(filteredData, getSecretIntegrity());

        String jsonPayload = mapper.writeValueAsString(signedData);

        ClassicHttpRequest request;

        switch (method.toUpperCase()) {
            case "GET" -> {
                String queryString = toQueryString(signedData);
                String fullUrl = url + queryString;
                System.out.println("Request URL: " + fullUrl);
                request = new HttpGet(fullUrl);
            }
            case "POST" -> {
                HttpPost postRequest = new HttpPost(url);
                postRequest.setEntity(new StringEntity(jsonPayload, ContentType.parse("UTF-8")));
                request = postRequest;
            }
            case "PUT" -> {
                HttpPut putRequest = new HttpPut(url);
                putRequest.setEntity(new StringEntity(jsonPayload, ContentType.parse("UTF-8")));
                request = putRequest;
            }
            default ->
                throw new IllegalArgumentException("Método HTTP no soportado: " + method);
        }

        request.addHeader("Content-Type", "application/json");

        return httpClient.execute(request, this::handleResponse);
    }

    /**
     * Convierte un mapa de parámetros en una cadena de consulta (query string)
     * con codificación URL.
     *
     * @param params Mapa de parámetros a convertir.
     * @return La cadena de consulta generada.
     * @throws Exception Si ocurre un error durante la codificación.
     */
    private String toQueryString(Map<String, Object> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder query = new StringBuilder("?");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (query.length() > 1) {
                query.append("&");
            }
            query.append(java.net.URLEncoder.encode(entry.getKey(), "UTF-8"));
            query.append("=");
            query.append(java.net.URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
        }
        return query.toString();
    }

    /**
     * Maneja la respuesta HTTP, validando el código de estado y la firma de
     * integridad.
     *
     * @param response La respuesta HTTP recibida.
     * @return El cuerpo de la respuesta en formato String.
     * @throws IOException Si ocurre un error de entrada/salida.
     * @throws ParseException Si ocurre un error al parsear la respuesta.
     */
    private String handleResponse(ClassicHttpResponse response) throws IOException, ParseException {
        int statusCode = response.getCode();
        String responseBody = EntityUtils.toString(response.getEntity());
    
        // Si el código no es 2xx, se lanza una excepción de ApiError
        if (statusCode < 200 || statusCode >= 300) {
            LOGGER.error("Error en la respuesta: HTTP {}. Body: {}", statusCode, responseBody);
            ApiError apiError = ApiError.fromResponse(statusCode, responseBody);
            if (statusCode >= 500) {
                LOGGER.error("Error interno del servidor (HTTP {}): {}", statusCode, apiError);
            }
            throw apiError;
        }
    
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
    
            String providedSignature = responseMap.get("signature").toString();
            String computedSignature = integrityService.generateSignature(responseMap, getSecretIntegrity());
    
            if (!providedSignature.equals(computedSignature)) {
                LOGGER.error("Firma inválida. Firma proporcionada: {}. Firma calculada: {}", providedSignature, computedSignature);
                throw new InvalidSignatureError();
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Error al parsear la respuesta JSON. Body: {}", responseBody, e);
            throw new IOException("Error al parsear la respuesta JSON", e);
        }
    
        return responseBody;
    }

    /**
     * Realiza una solicitud GET firmada.
     *
     * @param path Ruta del endpoint.
     * @param params Parámetros a incluir en la solicitud.
     * @return La respuesta en formato JSON.
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
     * Realiza una solicitud GET que lanza excepción si el recurso no es
     * encontrado.
     *
     * @param path Ruta del endpoint.
     * @param params Parámetros a incluir en la solicitud.
     * @return La respuesta en formato JSON.
     * @throws Exception Si ocurre algún error durante la solicitud.
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
     *
     * @param path Ruta del endpoint.
     * @param body Cuerpo de la solicitud en forma de mapa.
     * @return La respuesta en formato JSON.
     * @throws Exception Si ocurre algún error durante la solicitud.
     */
    public String post(String path, Map<String, Object> body) throws Exception {
        return request("POST", path, body);
    }

    /**
     * Realiza una solicitud PUT firmada.
     *
     * @param path Ruta del endpoint.
     * @param body Cuerpo de la solicitud en forma de mapa.
     * @return La respuesta en formato JSON.
     * @throws Exception Si ocurre algún error durante la solicitud.
     */
    public String put(String path, Map<String, Object> body) throws Exception {
        return request("PUT", path, body);
    }
}
