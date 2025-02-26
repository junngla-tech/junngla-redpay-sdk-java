package com.redpay.exceptions;

import lombok.Getter;

/**
 * Excepción personalizada utilizada para manejar errores del cliente RedPay.
 * <p>
 * Esta excepción encapsula el código de estado HTTP y el cuerpo de la respuesta
 * devueltos por la API, proporcionando detalles adicionales sobre el error.
 * </p>
 */
@Getter
public class RedPayClientException extends RuntimeException {
    /**
     * Código de estado HTTP de la respuesta de error.
     */
    private final int statusCode;
    
    /**
     * Cuerpo de la respuesta de error.
     */
    private final String responseBody;

    /**
     * Construye una nueva instancia de {@code RedPayClientException} con el código de estado y el cuerpo de la respuesta proporcionados.
     *
     * @param statusCode   el código de estado HTTP de la respuesta de error.
     * @param responseBody el cuerpo de la respuesta de error.
     */
    public RedPayClientException(int statusCode, String responseBody) {
        super("HTTP " + statusCode + ": " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}
