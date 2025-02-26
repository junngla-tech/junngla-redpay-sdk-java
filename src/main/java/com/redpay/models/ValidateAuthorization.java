package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase abstracta que representa la estructura base para la validación de autorizaciones.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public abstract class ValidateAuthorization {

    /**
     * Identificador del usuario enrolador.
     * <p>
     * Se mapea en JSON con la clave "enroller_user_id".
     * </p>
     */
    @JsonProperty("enroller_user_id")
    String user_id;
    
    /**
     * Retorna el tipo de usuario.
     *
     * @return Una cadena que representa el tipo de usuario.
     */
    public abstract String getUser_type();
}
