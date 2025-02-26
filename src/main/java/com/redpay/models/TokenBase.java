package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase abstracta base para representar un token en el sistema.
 * <p>
 * Contiene los campos básicos requeridos para la creación y validación de tokens.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class TokenBase {

    /**
     * Identificador del usuario (comercio).
     */
    @NonNull
    @JsonProperty("enroller_user_id")
    String user_id;

    /**
     * Detalle del token.
     */
    @NonNull
    String detail;

    /**
     * Datos adicionales asociados al token.
     */
    String extra_data;

    /**
     * Tiempo de vida del token en segundos (por defecto 300).
     */
    int lifetime = 300;

    /**
     * Valor que indica la reusabilidad del token (por defecto 1).
     */
    int reusability = 1;
}
