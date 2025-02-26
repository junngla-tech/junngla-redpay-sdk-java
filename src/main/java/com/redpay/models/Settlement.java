package com.redpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa una liquidación (Settlement) en el sistema.
 * <p>
 * Esta clase encapsula la información relacionada con el cronograma de liquidación asociado.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class Settlement {

    /**
     * Cronograma de liquidación asociado.
     */
    private SettlementSchedule schedule;
}
