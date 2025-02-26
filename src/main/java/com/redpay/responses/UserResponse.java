package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.models.Geo;
import com.redpay.models.Settlement;
import com.redpay.models.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de un usuario en el sistema.
 * <p>
 * Contiene la información básica del usuario, su cuenta, dirección tributaria, ubicación geográfica,
 * liquidación asociada y otros atributos relevantes.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {

    /**
     * Identificador del usuario enrolador.
     * Se mapea en JSON con la clave "enroller_user_id".
     */
    @NonNull
    @JsonProperty("enroller_user_id")
    private String user_id;

    /**
     * Nombre del usuario.
     */
    @NonNull
    @JsonProperty
    private String name;

    /**
     * Correo electrónico del usuario.
     */
    @NonNull
    @JsonProperty
    private String email;

    /**
     * Identificador tributario del usuario.
     */
    @NonNull
    @JsonProperty
    private String tax_id;

    /**
     * Tipo de usuario.
     */
    @NonNull
    @JsonProperty
    private String user_type;

    /**
     * Cuenta asociada al usuario.
     */
    @NonNull
    @JsonProperty
    private UserAccount account;

    /**
     * Dirección tributaria del usuario.
     */
    @JsonProperty
    private String tax_address;

    /**
     * Glosa asociada al usuario (comercio).
     */
    @JsonProperty
    private String gloss;

    /**
     * Ubicación geográfica del usuario.
     */
    @JsonProperty
    private Geo geo;

    /**
     * Indicador de que el comercio requiere activación para operar.
     */
    @JsonProperty
    private String required_activation;

    /**
     * Liquidación asociada al comercio.
     */
    @JsonProperty
    private Settlement settlement;

    /**
     * Indica si el usuario forma parte de un circuito cerrado (closed loop).
     */
    @JsonProperty
    private Boolean closed_loop;
}
