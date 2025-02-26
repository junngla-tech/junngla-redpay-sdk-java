package com.redpay.requests;

import com.redpay.enums.TokenType;
import com.redpay.enums.UserType;
import com.redpay.models.TokenData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud para un token de tipo T3 (Envío de dinero - Alias Send).
 * <p>
 * Esta clase extiende {@link TokenBaseRequest} e incluye datos específicos para tokens T3,
 * tales como el tipo de usuario y datos adicionales asociados al token.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TokenT3Request extends TokenBaseRequest {

    /**
     * Tipo de usuario asociado a la solicitud.
     */
    private UserType userType;

    /**
     * Datos asociados al token T3.
     */
    private TokenData data;

    /**
     * Retorna el tipo de token correspondiente a esta solicitud.
     *
     * @return {@link TokenType#T3}
     */
    @Override
    public TokenType getTokenType() {
        return TokenType.T3;
    }
}
