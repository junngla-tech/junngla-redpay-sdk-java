package com.redpay.exceptions;

import lombok.Getter;
import lombok.ToString;

/**
 * Excepción personalizada que se lanza cuando se intenta procesar una orden que ha sido revocada.
 */
@Getter
@ToString(callSuper = true)
public class OrderIsRevokedError extends RedPayBaseError {

    /**
     * Constructor que permite asignar valores a operationUuid, statusCode y signature.
     *
     * @param operationUuid Identificador único de la operación (opcional).
     * @param statusCode    Código de estado del error (opcional).
     * @param signature     Firma del error (opcional).
     */
    public OrderIsRevokedError(String operationUuid, String statusCode, String signature) {
        super("Order is revoked", operationUuid, statusCode, signature);
    }

    /**
     * Constructor sin parámetros adicionales.
     */
    public OrderIsRevokedError() {
        this(null, null, null);
    }
}
