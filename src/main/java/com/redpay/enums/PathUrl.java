package com.redpay.enums;

/**
 * Enum que representa los endpoints de la API de Redpay.
 * Cada valor es un endpoint de la API de Redpay.
 * <p>
 * Generate: Genera un token de pago. <br>
 * Revoke: Revoca un token de pago. <br>
 * ValidateToken: Valida un token de pago. <br>
 * Authorize: Autoriza un pago. <br>
 * ValidateAuthorization: Valida una autorización de pago. <br>
 * Chargeback: Realiza una devolución. <br>
 * User: Crea un usuario. <br>
 * UserVerify: Verifica un usuario. <br>
 */
public enum PathUrl {

    Generate("/payment-token/generate"),
    Revoke("/payment-token/revoke"),
    ValidateToken("/payment-token/check"),
    Authorize("/payment-token/authorize"),
    ValidateAuthorization("/authorization/check"),
    Chargeback("/chargeback"),
    User("/user"),
    UserVerify("/user/verify-enrollment?");

    private final String path;

    PathUrl(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}


