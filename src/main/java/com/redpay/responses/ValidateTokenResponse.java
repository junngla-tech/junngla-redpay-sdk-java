package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.enums.TokenType;
import com.redpay.models.TokenData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de validación de un token.
 * <p>
 * Esta clase encapsula la información resultante de la validación de un token, 
 * incluyendo detalles, glosa, el UUID del token, el tipo de token, datos adicionales,
 * el UUID de la operación y la firma de la respuesta.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidateTokenResponse {

    /**
     * Glosa de la validación.
     */
    @NonNull
    @JsonProperty
    private String gloss;

    /**
     * Detalle de la validación.
     */
    @NonNull
    @JsonProperty
    private String detail;

    /**
     * UUID del token validado.
     */
    @NonNull
    @JsonProperty
    private String token_uuid;

    /**
     * Tipo del token validado.
     */
    @NonNull
    @JsonProperty
    private TokenType token_type;

    /**
     * Datos adicionales asociados al token.
     */
    @JsonProperty
    private TokenData data;

    /**
     * UUID de la operación de validación.
     */
    @NonNull
    @JsonProperty
    private String operation_uuid;

    /**
     * Firma de integridad de la respuesta.
     */
    @NonNull
    @JsonProperty
    private String signature;
}
