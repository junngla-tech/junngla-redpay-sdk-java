package com.redpay.exceptions;

import lombok.Getter;
import lombok.ToString;

/**
 * Excepción personalizada que se lanza cuando se detecta una firma inválida.
 */
@Getter
@ToString(callSuper = true)
public class InvalidSignatureError extends RedPayBaseError {

    /**
     * Constructor que permite asignar valores a operationUuid, statusCode y signature.
     *
     * @param operationUuid Identificador único de la operación (opcional).
     * @param statusCode    Código de estado del error (opcional).
     * @param signature     Firma del error (opcional).
     */
    public InvalidSignatureError(String operationUuid, String statusCode, String signature) {
        super("Invalid Signature", operationUuid, statusCode, signature);
    }

    /**
     * Constructor sin parámetros adicionales.
     */
    public InvalidSignatureError() {
        this(null, null, null);
    }
}
