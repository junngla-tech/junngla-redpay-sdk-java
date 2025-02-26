package com.redpay.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa una solicitud para revocar un token.
 * <p>
 * Esta solicitud contiene el identificador del usuario (comercio) y el UUID del
 * token que se desea revocar.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class RevokeTokenRequest {

    /**
     * Identificador del usuario (comercio). Se mapea en JSON con la clave
     * "enroller_user_id".
     */
    @JsonProperty("enroller_user_id")
    private String user_id;

    /**
     * UUID del token a revocar.
     */
    private String token_uuid;
}
