package com.redpay.requests;

import com.redpay.enums.TokenType;
import com.redpay.models.TokenData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud para un token de tipo T2 (Cobro de suscripción).
 * <p>
 * Esta clase extiende {@link TokenBaseRequest} e incluye datos específicos para tokens T2.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TokenT2Request extends TokenBaseRequest {

    /**
     * Datos asociados al token T2.
     */
    private TokenData data;

    /**
     * Retorna el tipo de token correspondiente a esta solicitud.
     *
     * @return {@link TokenType#T2}
     */
    @Override
    public TokenType getTokenType() {
        return TokenType.T2;
    }
}
