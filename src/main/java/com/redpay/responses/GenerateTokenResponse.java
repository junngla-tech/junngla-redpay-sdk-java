package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta generada al crear un token de pago.
 * <p>
 * Esta clase encapsula la información del token, el UUID de la operación y la firma de integridad
 * asociada a la respuesta.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@NonNull
public class GenerateTokenResponse {

    /**
     * Token de pago generado.
     * <p>
     * Se mapea en JSON con la clave "payment_token".
     * </p>
     */
    @JsonProperty("payment_token")
    private TokenResponse token;

    /**
     * UUID de la operación asociada.
     */
    @JsonProperty
    private String operation_uuid;

    /**
     * Firma de integridad de la respuesta.
     */
    @JsonProperty
    private String signature;
}
