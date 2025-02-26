package com.redpay.requests;

import com.redpay.enums.TokenType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud para un token de tipo T1 (Suscripción).
 * <p>
 * Esta clase extiende {@link TokenBaseRequest} y define el tipo de token como T1.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class TokenT1Request extends TokenBaseRequest {

    /**
     * Retorna el tipo de token correspondiente a esta solicitud.
     *
     * @return {@link TokenType#T1}
     */
    @Override
    public TokenType getTokenType() {
        return TokenType.T1;
    }
}
