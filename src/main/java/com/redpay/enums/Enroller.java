package com.redpay.enums;

import lombok.Getter;

/**
 * Enum que representa los tipos de roles que puede tener un enrolador.
 * Cada valor es un rol que puede tener un enrolador.
 */
@Getter
public enum Enroller {
    COLLECTOR("collector"),
    PAYER("payer"),
    DUAL("dual");

    private final String role;

    /**
     * Constructor que asigna el valor del rol al enrolador.
     *
     * @param role Valor que representa el rol del enrolador.
     */
    Enroller(String role) {
        this.role = role;
    }
}
