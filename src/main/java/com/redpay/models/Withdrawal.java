package com.redpay.models;

import com.redpay.enums.WithdrawalMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la configuración de retiro de fondos de un comercio.
 * <p>
 * Contiene el modo de retiro y como opcional una configuración personalizada de
 * liquidación.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class Withdrawal {

    /**
     * Modo de retiro, definido por la enumeración {@link WithdrawalMode}.
     */
    private WithdrawalMode mode;

    /**
     * Liquidación personalizada asociada al retiro.
     * <p>
     * Si el enrolador no desea utilizar las liquidaciones por defecto, puede
     * definir una liquidación personalizada. 
     * Para ello, debe definir el modo de liquidación MANUAL.
     * </p>
     */
    private Settlement settlement;
}
