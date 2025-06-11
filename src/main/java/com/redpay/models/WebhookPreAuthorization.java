package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.responses.OperationsAuthorizeResponse;
import com.redpay.responses.SubscriptionDataResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa el payload de un webhook de pre-autorización enviado por el
 * sistema RedPay.
 * <p>
 * Este objeto contiene la información necesaria para procesar la
 * pre-autorización de una orden,
 * incluyendo el token asociado, datos de operaciones, estado, y la firma de
 * integridad.
 * </p>
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class WebhookPreAuthorization {

    /**
     * Identificador único del token asociado a la pre-autorización.
     */
    @NonNull
    @JsonProperty
    String token_uuid;

    /**
     * Indica si se trata de una notificación "med"
     */
    @JsonProperty
    Boolean is_med;

    /**
     * Monto asociado a la transacción que se está pre-autorizando.
     */
    @JsonProperty
    int amount;

    /**
     * Objeto que contiene la respuesta de operaciones de autorización.
     */
    @NonNull
    @JsonProperty
    OperationsAuthorizeResponse operations;

    /**
     * Datos adicionales de la suscripción (si aplica).
     */
    @JsonProperty
    SubscriptionDataResponse data;

    /**
     * Identificador del Collector asociado a la orden.
     */
    @JsonProperty
    String collector_id;

    /**
     * Identificador del Payer asociado a la orden.
     */
    @JsonProperty
    String payer_id;

    /**
     * Mensaje adicional que acompaña al webhook.
     */
    @JsonProperty
    String message;

    /**
     * Código de estado de la autorización.
     */
    @NonNull
    @JsonProperty
    String status_code;

    /**
     * Estado correspondiente a un token de suscripción.
     */
    @JsonProperty
    String status;

    /**
     * Datos extra opcionales enviados en el webhook.
     */
    @JsonProperty
    String extra_data;

    /**
     * Marca de tiempo (timestamp) indicando cuándo se generó la autorización.
     */
    @NonNull
    @JsonProperty
    String timestamp;

    /**
     * Firma de integridad utilizada para validar la autenticidad del webhook.
     */
    @NonNull
    @JsonProperty
    String signature;
}
