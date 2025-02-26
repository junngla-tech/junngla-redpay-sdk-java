package com.redpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que encapsula la configuración de cuentas para diferentes operaciones.
 * <p>
 * Contiene la configuración para:
 * - Autorización (authorize)
 * - Devoluciones (chargeback)
 * - Devoluciones automáticas (chargeback_automatic)
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class ConfigurationAccounts {
    
    /**
     * Configuración de cuenta para la autorización.
     * Solo para enroladores pagadores o dual.
     */
    AccountEnrollerConfig authorize;
    
    /**
     * Configuración de cuenta para las devoluciones.
     * Solo para enroladores recaudadores o dual.
     */
    AccountEnrollerConfig chargeback;
    
    /**
     * Configuración de cuenta para las devoluciones automáticas.
     * Solo para enroladores recaudadores o dual.
     */
    AccountEnrollerConfig chargeback_automatic;
}
