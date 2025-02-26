package com.redpay.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Locale;

/**
 * Enum que define los tipos de usuarios en el sistema.
 */
@Getter
public enum UserType {

    COLLECTOR("collector"),
    PAYER("payer");

    private final String value;

    /**
     * Constructor que asigna el valor asociado al tipo de usuario.
     *
     * @param value Valor que representa el tipo de usuario.
     */
    UserType(String value) {
        this.value = value;
    }

    /**
     * Devuelve el valor en minúsculas que representa el tipo de usuario.
     * <p>
     * Este método se utiliza para la serialización a JSON, garantizando que el valor
     * se represente en minúsculas.
     * </p>
     *
     * @return El valor en minúsculas del tipo de usuario.
     */
    @JsonValue
    public String getCode() {
        return value.toLowerCase(Locale.ROOT);
    }

}
