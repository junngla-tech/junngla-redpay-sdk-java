package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa una devolución (chargeback) en el sistema.
 * <p>
 * Esta clase encapsula los detalles relacionados con una devolución, incluyendo identificadores,
 * referencias, montos y cuentas de devolución con firmas asociadas.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chargeback {

    /**
     * UUID de la devolución.
     */
    @JsonProperty
    private String uuid;

    /**
     * Referencia asociada a la devolución.
     */
    @JsonProperty
    private String reference;

    /**
     * UUID de la liquidación asociada a la devolución.
     */
    @JsonProperty("settlement_uuid")
    private String settlement_uuid;

    /**
     * Identificador del usuario enrolador asociado a la devolución.
     */
    @JsonProperty("enroller_user_id")
    private String user_id;

    /**
     * Monto asociado a la devolución. Puede ser un monto parcial o total.
     */
    @JsonProperty
    private int amount;

    /**
     * Cuenta de devolución configurada por el enrolador.
     * <p>
     * Este campo se mapea en JSON con la clave "filler".
     * </p>
     */
    @JsonProperty("filler")
    private SignedAuthorizationAccount signedAuthorizationAccount;

    /**
     * Cuenta de devolución automatica firmada.
     * <p>
     * Este campo se mapea en JSON con la clave "debit_filler".
     * </p>
     */
    @JsonProperty("debit_filler")
    private SignedAuthorizationAccount signedAuthorizationAccountAutomatic;
}
