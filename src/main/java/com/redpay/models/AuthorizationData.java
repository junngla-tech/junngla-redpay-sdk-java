package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que encapsula la información necesaria para la autorización.
 * <p>
 * Contiene el monto autorizado para un token T0, el monto máximo permitido para un token T1 y la cuenta de autorización firmada.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizationData {

    /**
     * Monto autorizado.
     * <p>
     * Monto autorizado para un token T0, T2, T3, T4 y T6
     * </p>
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int amount;

    /**
     * Monto máximo permitido.
     * <p>
     * Monto máximo permitido para un token T1.
     * </p>
     */
    @JsonProperty
    private int max_amount = 0;

    /**
     * Cuenta de autorización firmada.
     * <p>
     * Cuenta de autorización que contiene la información de la cuenta del enrollador
     * </p>
     */
    @JsonProperty("filler")
    private SignedAuthorizationAccount signedAuthorizationAccount;
}
