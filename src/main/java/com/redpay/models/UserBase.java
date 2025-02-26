package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase base abstracta que representa a un usuario en el sistema.
 * <p>
 * Esta clase define los atributos comunes a todos los usuarios y declara el método abstracto
 * {@code getUser_type()} que debe ser implementado por las subclases para indicar el tipo de usuario.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public abstract class UserBase {
    
    /**
     * Identificador del usuario enrolador.
     * <p>
     * Se mapea en JSON con la clave "enroller_user_id".
     * </p>
     */
    @JsonProperty("enroller_user_id")
    private String user_id;
    
    /**
     * Cuenta asociada al usuario.
     */
    private UserAccount account;
    
    /**
     * Correo electrónico del usuario.
     */
    private String email;
    
    /**
     * Nombre completo del usuario.
     */
    private String name;
    
    /**
     * Identificador tributario del usuario.
     */
    private String tax_id;

    /**
     * Método abstracto que retorna el tipo de usuario.
     *
     * @return El tipo de usuario.
     */
    public abstract String getUser_type();
}
