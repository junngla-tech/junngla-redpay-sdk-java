package com.redpay.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.enums.TokenType;
import com.redpay.models.TokenBase;

import lombok.ToString;

/**
 * Clase abstracta base para las solicitudes relacionadas con tokens.
 * <p>
 * Extiende {@link TokenBase} y requiere que las subclases implementen el método
 * para obtener el tipo de token, el cual se serializa en JSON con la clave "token_type".
 * </p>
 */
@ToString(callSuper = true)
public abstract class TokenBaseRequest extends TokenBase {

    /**
     * Retorna el tipo de token asociado a la solicitud.
     *
     * @return El tipo de token representado por {@link TokenType}.
     */
    @JsonProperty("token_type")
    public abstract TokenType getTokenType();
}
