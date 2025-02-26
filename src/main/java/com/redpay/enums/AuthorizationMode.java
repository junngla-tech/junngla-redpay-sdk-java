package com.redpay.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Locale;

/**
 * Enum que representa los diferentes modos de operación de una devolución.
 * Utilizado para definir el propósito de la operación en servicios de RedPay.
 * <p>
 * - Authorize: Representa una autorización de transacción. <br>
 * - Chargeback: Representa una devolución de transacción. <br>
 * - Chargeback_Automatic: Representa una devolución automática de transacción. <br>
 * </p>
 */
@Getter
public enum AuthorizationMode {
    Authorize("authorize"),
    Chargeback("chargeback"),
    Chargeback_Automatic("chargeback_automatic");

    private final String authorize;

    /**
     * Constructor que inicializa el modo de autorización con su representación en String.
     *
     * @param authorize Valor que representa el modo de autorización.
     */
    AuthorizationMode(String authorize) {
        this.authorize = authorize;
    }

    /**
     * Devuelve el valor del modo de autorización en minúsculas.
     * <p>
     * Este método se utiliza para la serialización a JSON, asegurando que
     * el valor devuelto sea siempre en minúsculas.
     * </p>
     *
     * @return El modo de autorización en minúsculas.
     */
    @JsonValue
    public String getCode() {
        return authorize.toLowerCase(Locale.ROOT);
    }

}