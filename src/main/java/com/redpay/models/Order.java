package com.redpay.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una orden de pago en el sistema RedPay.
 * <p>
 * Esta clase contiene la información necesaria para identificar una orden de
 * pago,
 * incluyendo el token asociado, el usuario, el monto, el número máximo de
 * reutilizaciones
 * permitidas y la fecha de revocación (si la orden fue revocada).
 * </p>
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    /**
     * Identificador único del token asociado a la orden.
     */
    @NonNull
    String token_uuid;

    /**
     * Identificador del usuario asociado a la orden.
     */
    @NonNull
    String user_id;

    /**
     * Monto de la orden de pago.
     */
    @NonNull
    Integer amount;

    /**
     * Número máximo de reutilizaciones permitidas para esta orden.
     * Por defecto es 1.
     */
    @NonNull
    Integer reusability = 1;

    /**
     * Fecha en la que la orden fue revocada, si corresponde.
     */
    Date revoket_at;
}
