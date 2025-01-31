package com.redpay.enums;

/**
 * Enum que representa los diferentes modos de operación de una devolución.
 * Utilizado para definir el propósito de la operación en servicios de RedPay.
 * <p>
 * - Authorize: Representa una autorización de transacción. <br>
 * - Chargeback: Representa una devolución de transacción. <br>
 * - Chargeback_Automatic: Representa una devolución automática de transacción. <br>
 * </p>
 */
public enum AuthorizationMode {
    Authorize("authorize"),

    Chargeback("chargeback"),

    Chargeback_Automatic("chargeback_automatic");

    private final String authorize;

    AuthorizationMode(String authorize) {
        this.authorize = authorize;
    }

    public String getAuthorize() {
        return authorize;
    }
}
