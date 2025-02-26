package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de validación de autorización.
 * <p>
 * Esta clase extiende {@link AuthorizeResponse} e incluye información adicional
 * relacionada con la validación, como el monto, el identificador del pagador, el código de estado
 * y datos extra.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ValidateAuthorizationResponse extends AuthorizeResponse {

    /**
     * Monto asociado a la validación.
     */
    @JsonProperty
    private int amount;

    /**
     * Identificador del pagador.
     */
    @JsonProperty
    private String payer_id;

    /**
     * Código de estado de la validación.
     */
    @JsonProperty
    private String status_code;

    /**
     * Datos extra adicionales.
     */
    @JsonProperty
    private String extra_data;
}
