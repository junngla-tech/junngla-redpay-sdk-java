package com.redpay.services;

import com.redpay.interfaces.RoleActions;
import com.redpay.interfaces.RoleActionsEP;
import com.redpay.interfaces.RoleActionsER;
import com.redpay.models.RedPayConfig;
import com.redpay.models.TokenBase;
import com.redpay.models.UserBase;
import com.redpay.models.ValidateAuthorization;
import com.redpay.provider.RedPayConfigProvider;
import com.redpay.requests.AuthorizeRequest;
import com.redpay.requests.ChargebackRequest;
import com.redpay.requests.RevokeTokenRequest;
import com.redpay.requests.ValidateTokenRequest;
import com.redpay.responses.AuthorizeResponse;
import com.redpay.responses.ChargebackResponse;
import com.redpay.responses.GenerateTokenResponse;
import com.redpay.responses.GenerateUserResponse;
import com.redpay.responses.RevokeTokenResponse;
import com.redpay.responses.ValidateAuthorizationResponse;
import com.redpay.responses.ValidateTokenResponse;
import com.redpay.services.internal.RedPayDualService;
import com.redpay.services.internal.RedPayEPService;
import com.redpay.services.internal.RedPayERService;

import lombok.ToString;

/**
 * Clase principal para manejar los servicios de RedPay según el tipo de enrolador.
 * <p>
 * Esta clase encapsula métodos específicos y comunes para la gestión de usuarios y transacciones.
 * Se selecciona dinámicamente la instancia de servicio a utilizar (ER, EP o Dual) en función
 * del tipo de enrolador configurado en {@link RedPayConfig}.
 * </p>
 */
@ToString
public class RedPayService {

    /**
     * Instancia del servicio seleccionado dinámicamente.
     * Puede ser una implementación de {@link RoleActionsER}, {@link RoleActionsEP} o ambas en el caso de Dual.
     */
    private final RoleActions serviceInstance;

    /**
     * Constructor de la clase {@code RedPayService}.
     * Inicializa la instancia del servicio basado en el tipo configurado.
     */
    public RedPayService() {
        this.serviceInstance = initializeService();
    }

    /**
     * Inicializa el servicio correspondiente basado en el tipo configurado.
     *
     * @return Instancia del servicio configurado ({@link RedPayERService}, {@link RedPayEPService} o {@link RedPayDualService}).
     * @throws IllegalArgumentException Si el tipo de enrolador no es soportado.
     */
    private RoleActions initializeService() {
        RedPayConfig config = RedPayConfigProvider.getInstance().getConfig();
        System.out.println("config type: " + config.getType());
        return switch (config.getType()) {
            case COLLECTOR -> new RedPayERService();
            case PAYER -> new RedPayEPService();
            case DUAL -> new RedPayDualService();
            default -> throw new IllegalArgumentException("Tipo de enrolador no soportado: " + config.getType());
        };
    }

    /**
     * Verifica si el servicio es de tipo COLLECTOR.
     *
     * @param service Instancia del servicio a verificar.
     * @return {@code true} si el servicio es de tipo COLLECTOR, de lo contrario {@code false}.
     */
    private boolean isERService(RoleActions service) {
        System.out.println("service: " + service);
        return (service instanceof RoleActionsER || service instanceof RedPayDualService);
    }

    /**
     * Verifica si el servicio es de tipo PAYER.
     *
     * @param service Instancia del servicio a verificar.
     * @return {@code true} si el servicio es de tipo PAYER, de lo contrario {@code false}.
     */
    private boolean isEPService(RoleActions service) {
        return (service instanceof RoleActionsEP || service instanceof RedPayDualService);
    }

    /**
     * Genera un token.
     * <p>
     * Este método está disponible solo para servicios de tipo COLLECTOR.
     * </p>
     *
     * @param tokenInstance Instancia del token a generar.
     * @param <T>           Tipo que extiende {@link TokenBase}.
     * @return Una respuesta que representa el token generado.
     * @throws UnsupportedOperationException Si el método no está disponible para este tipo de servicio.
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/collector-duties#payment-tokens">Documentación</a>
     */
    public <T extends TokenBase> GenerateTokenResponse generateToken(T tokenInstance) throws Exception {
        if (isERService(serviceInstance)) {
            RoleActionsER erService = (RoleActionsER) serviceInstance;
            return erService.generateToken(tokenInstance);
        }
        throw new UnsupportedOperationException("El método generateToken no está disponible para este tipo de servicio.");
    }

    /**
     * Revoca un token.
     * <p>
     * Este método está disponible solo para servicios de tipo COLLECTOR.
     * </p>
     *
     * @param revokeTokenRequest Argumentos necesarios para revocar el token.
     * @return Una respuesta que representa el resultado de la revocación.
     * @throws UnsupportedOperationException Si el método no está disponible para este tipo de servicio.
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/collector-duties#revoke-tokens">Documentación</a>
     */
    public RevokeTokenResponse revokeToken(RevokeTokenRequest revokeTokenRequest) throws Exception {
        if (isERService(serviceInstance)) {
            RoleActionsER erService = (RoleActionsER) serviceInstance;
            return erService.revokeToken(revokeTokenRequest);
        }
        throw new UnsupportedOperationException("El método revokeToken no está disponible para este tipo de servicio.");
    }

    /**
     * Realiza una devolución (chargeback).
     * <p>
     * Este método está disponible solo para servicios de tipo COLLECTOR.
     * </p>
     *
     * @param chargebackRequest Argumentos necesarios para realizar la devolución.
     * @return Una respuesta que representa el resultado del chargeback.
     * @throws UnsupportedOperationException Si el método no está disponible para este tipo de servicio.
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/collector-duties#refund-transactions">Documentación</a>
     */
    public ChargebackResponse generateChargeback(ChargebackRequest chargebackRequest) throws Exception {
        if (isERService(serviceInstance)) {
            RoleActionsER erService = (RoleActionsER) serviceInstance;
            return erService.generateChargeback(chargebackRequest);
        }
        throw new UnsupportedOperationException("El método chargeback no está disponible para este tipo de servicio.");
    }

    /**
     * Autoriza un token.
     * <p>
     * Este método está disponible solo para servicios de tipo PAYER.
     * </p>
     *
     * @param authorizeRequest Argumentos necesarios para autorizar el token.
     * @return Una respuesta que representa el resultado de la autorización.
     * @throws UnsupportedOperationException Si el método no está disponible para este tipo de servicio.
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/payer-duties#transaction-authorization">Documentación</a>
     */
    public AuthorizeResponse authorizeToken(AuthorizeRequest authorizeRequest) throws Exception {
        if (isEPService(serviceInstance)) {
            RoleActionsEP epService = (RoleActionsEP) serviceInstance;
            return epService.authorizeToken(authorizeRequest);
        }
        throw new UnsupportedOperationException("El método authorizeToken no está disponible para este tipo de servicio.");
    }

    /**
     * Valida un token.
     *
     * @param validateTokenRequest Argumentos necesarios para validar el token.
     * @return Una respuesta que representa el resultado de la validación del token.
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/payer-duties#scan-qr">Documentación</a>
     */
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws Exception {
        return serviceInstance.validateToken(validateTokenRequest);
    }

    /**
     * Valida una autorización.
     *
     * @param validateAuthorization Argumentos necesarios para validar la autorización.
     * @param <T>                   Tipo que extiende {@link ValidateAuthorization}.
     * @return Una respuesta que representa el resultado de la validación.
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/collector-duties#transaction-validation-collector">Documentación COLLECTOR</a>
     * @see <a href="https://developers.redpay.cl/site/documentation/redpay/payer-duties#transaction-validation-payer">Documentación PAYER</a>
     */
    public <T extends ValidateAuthorization> ValidateAuthorizationResponse validateAuthorization(T validateAuthorization) throws Exception {
        return serviceInstance.validateAuthorization(validateAuthorization);
    }

    /**
     * Crea un usuario.
     *
     * @param userInstance Instancia del usuario a crear.
     * @param <T>          Tipo que extiende {@link UserBase}.
     * @return Una respuesta que representa el usuario creado.
     * @see <a href="https://developers.redpay.cl/site/reference-api/redpay/api-qri-v2#tag/Gestion-de-usuarios/operation/enrollUser">API</a>
     */
    public <T extends UserBase> GenerateUserResponse createUser(T userInstance) throws Exception {
        return serviceInstance.createUser(userInstance);
    }

    /**
     * Actualiza un usuario.
     *
     * @param userInstance Instancia del usuario a actualizar.
     * @param <T>          Tipo que extiende {@link UserBase}.
     * @return Una respuesta que representa el resultado de la actualización.
     * @see <a href="https://developers.redpay.cl/site/reference-api/redpay/api-qri-v2#tag/Gestion-de-usuarios/operation/updateUser">API</a>
     */
    public <T extends UserBase> GenerateUserResponse updateUser(T userInstance) throws Exception {
        return serviceInstance.updateUser(userInstance);
    }

    /**
     * Actualiza parcialmente un usuario.
     *
     * @param userInstance Instancia del usuario a actualizar parcialmente.
     * @param <T>          Tipo que extiende {@link UserBase}.
     * @return Una respuesta que representa el resultado de la actualización parcial.
     * @see <a href="https://developers.redpay.cl/site/reference-api/redpay/api-qri-v2#tag/Gestion-de-usuarios/operation/updateUser">API</a>
     */
    public <T extends UserBase> GenerateUserResponse updateUserPartial(T userInstance) throws Exception {
        return serviceInstance.updateUserPartial(userInstance);
    }

    /**
     * Obtiene un usuario.
     *
     * @param userInstance Instancia del usuario a obtener.
     * @param <T>          Tipo que extiende {@link UserBase}.
     * @return Una respuesta que representa el usuario obtenido.
     * @see <a href="https://developers.redpay.cl/site/reference-api/redpay/api-qri-v2#tag/Gestion-de-usuarios/paths/~1user~1verify-enrollment">API</a>
     */
    public <T extends UserBase> GenerateUserResponse getUser(T userInstance) throws Exception {
        return serviceInstance.getUser(userInstance);
    }

    /**
     * Obtiene un usuario o lanza un error si no existe.
     *
     * @param userInstance Instancia del usuario a obtener.
     * @param <T>          Tipo que extiende {@link UserBase}.
     * @return Una respuesta que representa el usuario obtenido.
     * @throws RuntimeException Si la solicitud falla.
     * @see <a href="https://developers.redpay.cl/site/reference-api/redpay/api-qri-v2#tag/Gestion-de-usuarios/paths/~1user~1verify-enrollment">API</a>
     */
    public <T extends UserBase> GenerateUserResponse getUserOrFail(T userInstance) throws Exception {
        return serviceInstance.getUserOrFail(userInstance);
    }
}
