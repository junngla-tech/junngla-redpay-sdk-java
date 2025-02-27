package com.redpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que encapsula los secretos o credenciales utilizadas en la integración con RedPay.
 * <p>
 * Esta clase almacena las claves necesarias para validar la integridad de los mensajes y para
 * realizar operaciones de autorización y devoluciones (chargebacks).
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Secrets {

    /**
     * Secreto utilizado para la validación de la integridad de los mensajes.
     * <p>
     * Este campo es obligatorio y no puede ser nulo.
     * </p>
     */
    @NonNull
    private String integrity;

    /**
     * Secreto utilizado para la autorización.
     * Solo para enroladores pagadores o dual.
     */
    private String authorize;

    /**
     * Secreto utilizado para la devolución (chargeback).
     * Solo para enroladores recaudadores o dual.
     */
    private String chargeback;

    /**
     * Secreto utilizado para la devolución automática (chargeback automático).
     * Solo para enroladores recaudadores o dual.
     */
    private String chargeback_automatic;
}
