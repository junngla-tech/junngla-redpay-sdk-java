package com.redpay.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.redpay.enums.ScheduleMode;
import com.redpay.enums.WithdrawalMode;

import lombok.Getter;
import lombok.ToString;

/**
 * Clase que contiene las liquidaciones predefinidas para los diferentes modos de retiro del Portal de Cartolas.
 * <p>
 * Cada modo de retiro definido en {@link WithdrawalMode} se asocia con una configuración
 * de liquidación ({@link Settlement}) que define un cronograma de liquidación ({@link SettlementSchedule}).
 * </p>
 */
@Getter
@ToString
public class PredefinedSchedules {

    /**
     * Mapa estático que relaciona cada modo de retiro con su correspondiente configuración de liquidación.
     */
    public static final Map<WithdrawalMode, Settlement> PREDEFINED = new HashMap<>();

    static {
        // Configuración para modo DAILY (diario)
        Settlement daily = new Settlement();
        SettlementSchedule dailySchedule = new SettlementSchedule();
        dailySchedule.setMode(ScheduleMode.DAYS_OF_WEEK); // Utiliza días de la semana para la liquidación
        dailySchedule.setValue(Arrays.asList(1, 2, 3, 4, 5)); // Días laborales (por ejemplo, de lunes a viernes)
        daily.setSchedule(dailySchedule);
        PREDEFINED.put(WithdrawalMode.DAILY, daily);

        // Configuración para modo BIWEEKLY (quincenal)
        Settlement biweekly = new Settlement();
        SettlementSchedule biSchedule = new SettlementSchedule();
        biSchedule.setMode(ScheduleMode.DAYS_OF_MONTH); // Utiliza días del mes para la liquidación
        biSchedule.setValue(Arrays.asList(1, 15)); // Liquidaciones el día 1 y el 15 del mes
        biweekly.setSchedule(biSchedule);
        PREDEFINED.put(WithdrawalMode.BIWEEKLY, biweekly);

        // Configuración para modo MONTHLY (mensual)
        Settlement monthly = new Settlement();
        SettlementSchedule monthlySchedule = new SettlementSchedule();
        monthlySchedule.setMode(ScheduleMode.DAYS_OF_MONTH); // Utiliza días del mes para la liquidación
        monthlySchedule.setValue(Arrays.asList(1)); // Liquidación el día 1 del mes
        monthly.setSchedule(monthlySchedule);
        PREDEFINED.put(WithdrawalMode.MONTHLY, monthly);

        // Configuración para modo MANUAL: no se define una liquidación preestablecida.
        PREDEFINED.put(WithdrawalMode.MANUAL, null);
    }
}
