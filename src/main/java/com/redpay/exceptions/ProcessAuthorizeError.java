package com.redpay.exceptions;

import lombok.Getter;
import lombok.ToString;

/**
 * Excepción personalizada para errores durante el procesamiento de autorizaciones.
 */
@Getter
@ToString(callSuper = true)
public class ProcessAuthorizeError extends ApiError {

    /**
     * Constructor para crear una instancia de ProcessAuthorizeError.
     *
     * @param message       Mensaje descriptivo del error.
     * @param operationUuid Identificador único de la operación (opcional).
     * @param statusCode    Código de estado del error (opcional).
     * @param signature     Firma del error (opcional).
     */
    public ProcessAuthorizeError(String message, String operationUuid, String statusCode, String signature) {
        super(message, operationUuid, statusCode, signature);
    }

    /**
     * Crea una instancia de ProcessAuthorizeError a partir de una excepción.
     * Si la excepción es un ApiError, se extraen sus parámetros; de lo contrario, se usan valores por defecto.
     *
     * @param cause La excepción original.
     * @return Una instancia de ProcessAuthorizeError.
     */
    public static ProcessAuthorizeError fromException(Throwable cause) {
        if (cause instanceof ApiError apiError) {
            return new ProcessAuthorizeError(
                "Error processing authorization orders: " + apiError.getMessage(),
                apiError.getOperationUuid(),
                apiError.getStatusCode(),
                apiError.getSignature()
            );
        } 

        return new ProcessAuthorizeError(
            "Error processing authorization orders: " + cause.getMessage(),
            null,
            null,
            null
        );
    }
}
