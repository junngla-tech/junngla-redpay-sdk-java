// ApiError.java
package com.redpay.exceptions;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.ToString;

/**
 * Excepción base para errores de la API de RedPay.
 * <p>
 * Estructura del error:
 * - operation_uuid (opcional)
 * - message
 * - status_code (opcional)
 * - signature (opcional)
 * </p>
 */
@Getter
@ToString(callSuper = true)
public class ApiError extends RuntimeException {
    private final String operationUuid;
    private final String statusCode;
    private final String signature;

    public ApiError(String message, String operationUuid, String statusCode, String signature) {
        super(message);
        this.operationUuid = operationUuid;
        this.statusCode = statusCode;
        this.signature = signature;
    }

    /**
     * Crea una instancia de ApiError a partir del código HTTP y el cuerpo de la respuesta.
     * <p>
     * Este método intenta mapear el cuerpo de la respuesta a un mapa para extraer los detalles del error.
     * Si falla el parseo, se crea una ApiError con el cuerpo sin procesar.
     * </p>
     *
     * @param httpStatusCode Código de estado HTTP.
     * @param responseBody   Cuerpo de la respuesta de la API.
     * @return Una instancia de ApiError con los datos extraídos.
     */
    public static ApiError fromResponse(int httpStatusCode, String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> errorMap = mapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            String errorMessage = errorMap.get("message").toString();
            String operationUuid = errorMap.containsKey("operation_uuid") ? errorMap.get("operation_uuid").toString() : null;
            String errorStatusCode = errorMap.containsKey("status_code") ? errorMap.get("status_code").toString() : null;
            String signature = errorMap.containsKey("signature") ? errorMap.get("signature").toString() : null;
            return new ApiError(errorMessage, operationUuid, errorStatusCode, signature);
        } catch (JsonProcessingException e) {
            return new ApiError("Error en la petición: HTTP " + httpStatusCode + " " + responseBody, null, null, null);
        }
    }
}
