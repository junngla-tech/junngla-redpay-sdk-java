package com.redpay.models;

import java.util.List;

import com.redpay.enums.ScheduleMode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa el cronograma de liquidación.
 * <p>
 * Define el modo de liquidación y la lista de valores que indican los períodos en que se realiza la liquidación.
 * Por ejemplo, si el modo es DAYS_OF_WEEK, la lista de valores podría representar los días de la semana en los que se liquida.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class SettlementSchedule {
    /**
     * Modo que define cómo se interpretan los valores del cronograma.
     */
    private ScheduleMode mode;
    
    /**
     * Lista de valores enteros que indican los períodos (por ejemplo, 1,2,3) para la liquidación.
     */
    private List<Integer> value;
}
