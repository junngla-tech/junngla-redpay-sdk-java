package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.models.Chargeback;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de una operación de devolución (chargeback).
 * <p>
 * Esta clase encapsula los detalles de la devolución procesada, el UUID de la operación y la firma
 * de integridad que garantiza la validez de la respuesta.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargebackResponse {

    /**
     * Detalles de la devolución procesada.
     */
    @JsonProperty
    private Chargeback chargeback;

    /**
     * UUID de la operación asociada a la devolución.
     */
    @JsonProperty
    private String operation_uuid;

    /**
     * Firma de integridad de la operación.
     */
    @JsonProperty
    private String signature;
}
