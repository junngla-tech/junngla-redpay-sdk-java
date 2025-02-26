package com.redpay.requests;

import com.redpay.enums.UserType;
import com.redpay.models.ValidateAuthorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Solicitud para la validación de autorización de un usuario tipo Collector.
 * <p>
 * Extiende {@link ValidateAuthorization} e incluye el identificador de la autorización.
 * El tipo de usuario se establece como COLLECTOR.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString(callSuper = true)
public class ValidateAuthorizationCollectorRequest extends ValidateAuthorization {

    /**
     * UUID de la autorización a validar.
     */
    private String authorization_uuid;

    /**
     * Retorna el tipo de usuario, que en este caso es COLLECTOR.
     *
     * @return El valor correspondiente al tipo de usuario COLLECTOR.
     */
    @Override
    public String getUser_type() {
        return UserType.COLLECTOR.getValue();
    }
}
