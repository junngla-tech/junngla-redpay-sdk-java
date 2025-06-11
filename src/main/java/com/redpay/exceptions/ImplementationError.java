package com.redpay.exceptions;

import lombok.Getter;
import lombok.ToString;

/**
 * Se lanza cuando ocurre un error en la implementación del código del desarrollador.
 * <p>
 * Mensaje por defecto: "An error ocurred in your implementation".
 * </p>
 */
@Getter
@ToString(callSuper = true)
public class ImplementationError extends RuntimeException {

    /**
     * Crea una nueva instancia de ImplementationError con el mensaje por defecto.
     */
    public ImplementationError() {
        super("An error ocurred in your implementation");
    }

    /**
     * Crea una nueva instancia de ImplementationError que envuelve la causa original.
     *
     * @param cause La excepción original que causó este error.
     */
    public ImplementationError(Throwable cause) {
        super("An error ocurred in your implementation", cause);
    }
}
