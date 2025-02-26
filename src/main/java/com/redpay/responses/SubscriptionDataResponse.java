package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta que contiene los datos de suscripción (T1).
 * <p>
 * Incluye el UUID de la suscripción, mapeado en JSON con la clave
 * "subscription_uuid".
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriptionDataResponse {

    /**
     * UUID de la suscripción.
     */
    @JsonProperty("subscription_uuid")
    private String uuid;
}
