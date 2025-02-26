package com.redpay.enums;

import lombok.Getter;

/**
 * Enum que define los modos de programación de retiro de dinero del Portal de Cartolas.
 * <p>
 * Este enum se utiliza en la creación de usuarios de tipo recaudador,
 * y sirve para configurar los días de retiro en el Portal de Cartolas.
 *
 * <p>
 * DAYS_OF_WEEK: Programación basada en días de la semana. <br>
 * DAYS_OF_MONTH: Programación basada en días específicos del mes.
 * </p>
 */
@Getter
public enum ScheduleMode {
    DAYS_OF_WEEK,
    DAYS_OF_MONTH,
}

