package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de datos al validar un token.
 * <p>
 * Esta clase encapsula el monto asociado a la validación del token.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ValidateTokenDataResponse {

    /**
     * Monto asociado a la validación del token.
     */
    @JsonProperty
    private int amount;
}
