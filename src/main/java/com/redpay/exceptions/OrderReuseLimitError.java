package com.redpay.exceptions;

import lombok.Getter;
import lombok.ToString;

/**
 * Excepción personalizada que se lanza cuando se excede el límite de reutilización de una orden.
 */
@Getter
@ToString(callSuper = true)
public class OrderReuseLimitError extends RedPayBaseError {

    /**
     * Constructor que permite asignar valores a operationUuid, statusCode y signature.
     *
     * @param operationUuid Identificador único de la operación (opcional).
     * @param statusCode    Código de estado del error (opcional).
     * @param signature     Firma del error (opcional).
     */
    public OrderReuseLimitError(String operationUuid, String statusCode, String signature) {
        super("Order reuse limit exceeded", operationUuid, statusCode, signature);
    }

    /**
     * Constructor sin parámetros adicionales.
     */
    public OrderReuseLimitError() {
        this(null, null, null);
    }
}
