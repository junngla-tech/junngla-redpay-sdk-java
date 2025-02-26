package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de liquidación de una autorización.
 * <p>
 * Contiene información relevante de la liquidación, incluyendo el UUID, monto,
 * estado y referencia.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SettlementResponse {

    /**
     * UUID de la liquidación.
     */
    @JsonProperty
    private String uuid;

    /**
     * Monto asociado a la liquidación.
     */
    @JsonProperty
    private int amount;

    /**
     * Estado de la liquidación.
     * <p>
     * El estado en la respuesta de autorización siempre será "PENDING", es
     * decir, pendiente de liquidación. En el siguente proceso (webhook de
     * liquidación) se notificará el estado "SETTLED" una vez que los fondos
     * hayan sido transferidos.
     * </p>
     */
    @JsonProperty
    private String status;

    /**
     * Referencia asociada a la liquidación.
     */
    @JsonProperty
    private String reference;
}
