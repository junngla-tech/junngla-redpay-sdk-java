package com.redpay.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.enums.UserType;
import com.redpay.enums.WithdrawalMode;
import com.redpay.models.Geo;
import com.redpay.models.PredefinedSchedules;
import com.redpay.models.Settlement;
import com.redpay.models.UserBase;
import com.redpay.models.Withdrawal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Solicitud para crear o actualizar un usuario (comercio) tipo Collector.
 * <p>
 * Incluye información adicional del usuario, como dirección tributaria, glosa y ubicación geográfica.
 * Además, gestiona la configuración del settlement según el modo de retiro.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString(callSuper = true)
public class UserCollectorRequest extends UserBase {

    /**
     * Dirección tributaria del usuario.
     */
    private String tax_address;

    /**
     * Glosa o descripción del usuario.
     */
    private String gloss;

    /**
     * Ubicación geográfica del usuario.
     */
    private Geo geo;

    /**
     * Configuración del retiro asociado al usuario (comercio).
     * <p>
     * Este campo se ignora durante la serialización JSON.
     * </p>
     */
    @JsonIgnore
    private Withdrawal withdrawal;

    /**
     * Obtiene el settlement asociado al usuario.
     * <p>
     * Si no se ha configurado withdrawal, se asume el modo DAILY por defecto.
     * Para el modo MANUAL, se requiere que el settlement esté configurado; de lo contrario,
     * se lanza una excepción. Para otros modos, se retorna el settlement predefinido o el
     * settlement configurado en withdrawal.
     * </p>
     *
     * @return El settlement configurado.
     */
    @JsonProperty("settlement")
    public Settlement getSettlement() {
        // Si withdrawal es nulo, se asume DAILY por defecto.
        if (withdrawal == null) {
            withdrawal = new Withdrawal();
            withdrawal.setMode(WithdrawalMode.DAILY);
            withdrawal.setSettlement(PredefinedSchedules.PREDEFINED.get(WithdrawalMode.DAILY));
        }
        // Para el modo MANUAL, se requiere un settlement configurado.
        if (withdrawal.getMode() == WithdrawalMode.MANUAL) {
            if (withdrawal.getSettlement() == null) {
                throw new IllegalArgumentException("Para el modo MANUAL se debe configurar un settlement.");
            }
            return withdrawal.getSettlement();
        }
        // Para otros modos, se intenta obtener un settlement predefinido.
        Settlement predefined = PredefinedSchedules.PREDEFINED.get(withdrawal.getMode());
        return (predefined != null) ? predefined : withdrawal.getSettlement();
    }

    /**
     * Retorna el tipo de usuario.
     *
     * @return El valor correspondiente al tipo de usuario COLLECTOR.
     */
    @Override
    public String getUser_type() {
        return UserType.COLLECTOR.getValue();
    }
}
