package com.redpay.requests;

import com.redpay.enums.TokenType;
import com.redpay.models.TokenData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud para un token de tipo T0 (compra).
 * <p>
 * Esta clase extiende {@link TokenBaseRequest} e incluye datos específicos para tokens T0.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TokenT0Request extends TokenBaseRequest {

    /**
     * Datos asociados al token T0.
     */
    private TokenData data;

    /**
     * Retorna el tipo de token correspondiente a esta solicitud.
     *
     * @return {@link TokenType#T0}
     */
    @Override
    public TokenType getTokenType() {
        return TokenType.T0;
    }
}
