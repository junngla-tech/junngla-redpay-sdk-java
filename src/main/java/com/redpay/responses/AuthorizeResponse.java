package com.redpay.responses;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.models.SignedAuthorizationAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de una operación de autorización.
 * <p>
 * Esta clase encapsula información sobre el token generado, las operaciones
 * realizadas, la liquidación asociada, y otros datos relevantes de la
 * transacción.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizeResponse {

    /**
     * UUID del token generado.
     */
    @JsonProperty
    private String token_uuid;

    /**
     * Respuesta con las operaciones de autorización realizadas.
     */
    @JsonProperty
    private OperationsAuthorizeResponse operations;

    /**
     * Indica si la operación es de tipo MED o no.
     */
    @JsonProperty
    private boolean is_med;

    /**
     * Fecha y hora en que se generó la autorización.
     */
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Date timestamp;

    /**
     * Cuenta de autorización firmada (filler) asociada a la respuesta.
     */
    @JsonProperty("filler")
    private SignedAuthorizationAccount signedAuthorizationAccount;

    /**
     * Respuesta de liquidación asociada a la operación.
     * <p>
     * Si la configuración MED del enrolador es ALWAYS, este campo contendrá la
     * información de la liquidación.
     * </p>
     * <p>
     * Si la configuración MED del enrolador es NOT_ONUS y los usuarios de la
     * transacción (pagador y recaudador) son del mismo enrolador, este campo no
     * estará presente. Si uno de los usuarios es de un enrolador diferente,
     * este campo contendrá la información de la liquidación.
     * </p>
     * <p>
     * Si la configuración MED del enrolador es NEVER, este campo no estará
     * presente.
     * </p>
     */
    @JsonProperty
    private SettlementResponse settlement;

    /**
     * Identificador del colector asociado a la operación.
     * <p>
     * Este campo estará presente si la configuración MED del enrolador es NOT_ONUS y ambos usuarios pertenecen al mismo enrolador.
     * </p>
     */
    @JsonProperty
    private String collector_id;

    /**
     * UUID de la operación de autorización.
     */
    @JsonProperty
    private String operation_uuid;

    /**
     * Firma que garantiza la integridad de la respuesta.
     */
    @JsonProperty
    private String signature;

    /**
     * Datos de suscripción asociados a la operación, puede ser nulo.
     */
    @JsonProperty
    SubscriptionDataResponse data;
}
