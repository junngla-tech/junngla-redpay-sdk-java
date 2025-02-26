package com.redpay.requests;

import com.redpay.enums.UserType;
import com.redpay.models.ValidateAuthorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Solicitud para la validación de autorización de un usuario tipo Payer.
 * 
 * <p>
 * Es requerido que uno de los dos UUIDs sea proporcionado, ya sea el de la autorización o el de la validación. (en caso de obtener un TIMEOUT en la autorización, se debe proporcionar el UUID de la validación)
 * </p>
 * 
 * <p>
 * Esta clase extiende {@link ValidateAuthorization} e incluye el UUID de la autorización
 * y el UUID de validación asociados. El tipo de usuario se establece como PAYER.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ValidateAuthorizationPayerRequest extends ValidateAuthorization {

    /**
     * UUID de la autorización a validar.
     * <p>
     * Este valor es opcional y se obtiene de la respuesta de la autorización.
     * </p>
     */
    private String authorization_uuid;

    /**
     * UUID de la validación asociada a la autorización.
     * <p>
     * Este valor es opcional y se obtiene de la respuesta de la validación del token
     * </p>
     */
    private String validation_uuid;

    /**
     * Retorna el tipo de usuario, que en este caso es PAYER.
     *
     * @return El valor correspondiente al tipo de usuario PAYER.
     */
    @Override
    public String getUser_type() {
        return UserType.PAYER.getValue();
    }
}
