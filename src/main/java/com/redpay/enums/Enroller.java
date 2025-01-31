package com.redpay.enums;

/**
 * Enum que representa los tipos de roles que puede tener un enrolador.
 * Cada valor es un rol que puede tener un enrolador.
 */
public enum Enroller {
    COLLECTOR("collector"),
    PAYER("payer"),
    DUAL("dual");

    private final String role;

    Enroller(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
