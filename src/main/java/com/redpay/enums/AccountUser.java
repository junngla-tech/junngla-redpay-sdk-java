package com.redpay.enums;

/**
 * Enum que representa los tipos de cuenta que un usuario puede tener.
 *
 * <p>
 * - CUENTA_CORRIENTE: Representa una cuenta corriente con el código "001".<br>
 * - CUENTA_VISTA: Representa una cuenta vista con el código "002".
 * </p>
 */
public enum AccountUser {
    /**
     * Cuenta Corriente.
     */
    CUENTA_CORRIENTE("001"),

    /**
     * Cuenta Vista.
     */
    CUENTA_VISTA("002");

    private final String code;

    AccountUser(String code) {
        this.code = code;
    }

    /**
     * Obtiene el código asociado al tipo de cuenta.
     *
     * @return String con el código de la cuenta.
     */
    public String getCode() {
        return code;
    }
}