package com.redpay.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * Servicio encargado de generar y validar firmas utilizando HMAC SHA256.
 * <p>
 * Este servicio utiliza Jackson para convertir el objeto payload a una
 * representación JSON, luego extrae sus campos en un orden consistente
 * (ordenando las entradas por clave) y finalmente genera una firma HMAC SHA256
 * utilizando la clave secreta proporcionada.
 * </p>
 */
public class RedPayIntegrityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedPayIntegrityService.class);

    /**
     * Genera una firma HMAC SHA256 para el objeto payload utilizando la clave
     * secreta proporcionada.
     *
     * @param payload Objeto a firmar.
     * @param secretKey Clave secreta para la generación de la firma.
     * @return La firma generada en formato hexadecimal.
     */
    public String generateSignature(Object payload, String secretKey) {

        List<Object> objectList = new ArrayList<>();

        try {
            // Configurar el ObjectMapper para que acepte propiedades insensibles a mayúsculas/minúsculas
            // y para ordenar las entradas del mapa por clave.
            ObjectMapper mapper = JsonMapper.builder()
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                    .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                    .build();

            // Convertir el payload a un objeto JSON genérico
            Object json = mapper.readValue(mapper.writeValueAsString(payload), Object.class);
            // Leer el JSON en un JsonNode para poder iterar sobre sus campos
            JsonNode jsonNode = mapper.readTree(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));

            for (Iterator<String> it = jsonNode.fieldNames(); it.hasNext();) {
                String key = it.next();
                JsonNode valueNode = jsonNode.get(key);

                // Se omite cualquier campo nulo o el campo "signature" para evitar incluirlo en el cálculo.
                if (valueNode == null || valueNode.isNull() || Objects.equals(key, "signature")) {
                    continue;
                }
                objectList.add(key + valueNode);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format("Error SignatureService: Json Processing in Object: %s %s",
                    payload.getClass().getName(), e));
        }

        String signature = validateSignature(objectList, secretKey);

        return signature;
    }

    /**
     * Genera la firma HMAC SHA256 a partir de una lista de representaciones de
     * campos y la clave secreta.
     *
     * @param objectList Lista de representaciones en cadena de los campos del
     * objeto a firmar.
     * @param secretKey Clave secreta utilizada para la firma.
     * @return La firma generada en formato hexadecimal.
     */
    private String validateSignature(List<Object> objectList, String secretKey) {

        StringBuilder chars = new StringBuilder();
        objectList.forEach(o -> chars.append(o.toString()));

        HmacUtils hm256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKey);
        String signature = hm256.hmacHex(chars.toString());

        LOGGER.debug("SettlementRequest Signature for validation generated");
        objectList.forEach(o -> LOGGER.debug(String.format("-->%s<--", o)));
        LOGGER.debug(String.format("Signature generated is : -->%s<--", signature));

        return signature;
    }

    /**
     * Devuelve el objeto payload complementado con su firma.
     *
     * @param payload Objeto representado como Map a firmar.
     * @param secretKey Clave secreta utilizada para la firma.
     * @return El mismo Map con la clave "signature" añadida.
     */
    public Map<String, Object> getSignedObject(Map<String, Object> payload, String secretKey) {
        payload.put("signature", generateSignature(payload, secretKey));
        return payload;
    }
}
