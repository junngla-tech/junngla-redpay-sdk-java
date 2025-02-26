package com.redpay.requests;

import com.redpay.enums.UserType;
import com.redpay.models.Geo;
import com.redpay.models.UserBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud para crear o actualizar un usuario tipo Payer.
 * <p>
 * Incluye información básica heredada de {@link UserBase} y la ubicación geográfica del usuario.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class UserPayerRequest extends UserBase {

    /**
     * Ubicación geográfica del usuario.
     * Opcional.
     */
    private Geo geo;

    /**
     * Retorna el tipo de usuario.
     *
     * @return Una cadena que representa el tipo de usuario PAYER.
     */
    @Override
    public String getUser_type() {
        return UserType.PAYER.getValue();
    }
}
