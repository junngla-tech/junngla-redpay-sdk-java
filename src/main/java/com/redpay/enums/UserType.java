package com.redpay.enums;

/**
 * Enum que define los tipos de usuarios en el sistema.
 */
public enum UserType {

    COLLECTOR("collector"),
    PAYER("payer");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
