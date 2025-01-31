package com.redpay.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Servicio encargado de generar y validar firmas utilizando HMAC SHA256.
 */
public class RedPayIntegrityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedPayIntegrityService.class);
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private final ObjectMapper objectMapper;

    public RedPayIntegrityService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    /**
     * Genera una firma HMAC SHA-256 basada en los datos de entrada y una clave secreta.
     *
     * @param payload   Objeto que se quiere firmar.
     * @param secretKey Clave secreta utilizada para generar la firma.
     * @return Cadena de firma HMAC SHA-256 en formato hexadecimal.
     */
    public String generateSignature(Object payload, String secretKey) {
        try {
            // Convertir el objeto en un JSON ordenado
            String jsonString = objectMapper.writeValueAsString(payload);
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            // Ordenar las claves y eliminar el campo "signature"
            TreeMap<String, String> sortedMap = new TreeMap<>();
            jsonNode.fields().forEachRemaining(entry -> {
                if (!entry.getKey().equals("signature")) {
                    sortedMap.put(entry.getKey(), entry.getValue().toString());
                }
            });

            // Construir la cadena base para la firma
            StringBuilder baseString = new StringBuilder();
            sortedMap.forEach((key, value) -> baseString.append(key).append(value));

            // Generar la firma HMAC SHA-256 sin usar commons-codec
            return hmacSha256(baseString.toString(), secretKey);

        } catch (JsonProcessingException e) {
            LOGGER.error("Error procesando JSON en generateSignature", e);
            throw new IllegalArgumentException("No se pudo procesar el objeto JSON");
        }
    }

    /**
     * Método que genera un hash HMAC SHA-256 usando Java puro (sin commons-codec).
     *
     * @param data      Datos a firmar.
     * @param secretKey Clave secreta.
     * @return Cadena en formato hexadecimal.
     */
    private String hmacSha256(String data, String secretKey) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Convertir bytes a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generando HMAC SHA-256", e);
        }
    }

    /**
     * Devuelve un objeto complementado con su firma.
     *
     * @param payload   Objeto que se quiere firmar.
     * @param secretKey Clave secreta utilizada para generar la firma.
     * @return Objeto firmado con el campo "signature".
     */
    public Map<String, Object> getSignedObject(Map<String, Object> payload, String secretKey) {
        payload.put("signature", generateSignature(payload, secretKey));
        return payload;
    }

    /**
     * Valida una firma comparándola con la generada a partir del objeto recibido.
     *
     * @param signedObject Objeto firmado que contiene el campo "signature".
     * @param secretKey    Clave secreta utilizada para generar la firma.
     * @return `true` si la firma es válida, `false` si no coincide.
     */
    public boolean validateSignature(Map<String, Object> signedObject, String secretKey) {
        if (!signedObject.containsKey("signature")) {
            return false;
        }
        String providedSignature = signedObject.get("signature").toString();
        String calculatedSignature = generateSignature(signedObject, secretKey);
        return providedSignature.equals(calculatedSignature);
    }

    /**
     * Valida una firma y lanza una excepción si no es válida.
     *
     * @param signedObject Objeto firmado a validar.
     * @param secretKey    Clave secreta utilizada para generar la firma.
     * @throws IllegalArgumentException Si la firma no es válida.
     */
    public void validateSignatureOrFail(Map<String, Object> signedObject, String secretKey) {
        if (!validateSignature(signedObject, secretKey)) {
            // TODO: Cambiar a excepción personalizada
            throw new IllegalArgumentException("Firma inválida");
        }
    }
}
