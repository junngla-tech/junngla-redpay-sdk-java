package com.redpay.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud para validar un token y obtener información asociada al mismo.
 * <p>
 * Esta clase encapsula la información necesaria para validar un token, incluyendo el identificador del usuario,
 * el UUID del token y el tipo de usuario.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class ValidateTokenRequest {

    /**
     * Identificador del usuario enrolador.
     * Se mapea en JSON con la clave "enroller_user_id".
     */
    @JsonProperty("enroller_user_id")
    private String user_id;

    /**
     * UUID del token a validar.
     */
    private String token_uuid;

    /**
     * Tipo de usuario asociado a la solicitud.
     */
    private UserType user_type;
}
