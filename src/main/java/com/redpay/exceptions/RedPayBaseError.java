package com.redpay.exceptions;

import lombok.Getter;
import lombok.ToString;

/**
 * Excepción base que representa un error de RedPay.
 * <p>
 * Estructura:
 * - operation_uuid: Identificador único de la operación.
 * - message: Mensaje descriptivo del error.
 * - status_code: Código de estado del error.
 * - signature: Firma del error.
 * </p>
 */
@Getter
@ToString(callSuper = true)
public abstract class RedPayBaseError extends RuntimeException {
    private final String operationUuid;
    private final String statusCode;
    private final String signature;

    /**
     * Constructor para crear una instancia de RedPayBaseError.
     *
     * @param message       Mensaje descriptivo del error.
     * @param operationUuid Identificador único de la operación.
     * @param statusCode    Código de estado del error.
     * @param signature     Firma del error.
     */
    public RedPayBaseError(String message, String operationUuid, String statusCode, String signature) {
        super(message);
        this.operationUuid = operationUuid;
        this.statusCode = statusCode;
        this.signature = signature;
    }
}
