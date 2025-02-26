package com.redpay.services.internal;

import com.redpay.interfaces.RoleActionsEP;
import com.redpay.interfaces.RoleActionsER;
import com.redpay.models.RedPayBase;
import com.redpay.models.TokenBase;
import com.redpay.requests.AuthorizeRequest;
import com.redpay.requests.ChargebackRequest;
import com.redpay.requests.RevokeTokenRequest;
import com.redpay.responses.AuthorizeResponse;
import com.redpay.responses.ChargebackResponse;
import com.redpay.responses.GenerateTokenResponse;
import com.redpay.responses.RevokeTokenResponse;

/**
 * Servicio de rol Dual de RedPay que implementa las interfaces {@link RoleActionsER} y {@link RoleActionsEP}.
 * <p>
 * Este servicio delega internamente las operaciones relacionadas con la generación y revocación de tokens (RoleActionsER)
 * a {@link RedPayERService} y las operaciones de autorización (RoleActionsEP) a {@link RedPayEPService}.
 * </p>
 */
public class RedPayDualService extends RedPayBase implements RoleActionsER, RoleActionsEP {

    private final RedPayERService erService;
    private final RedPayEPService epService;

    /**
     * Constructor por defecto que inicializa los servicios internos.
     */
    public RedPayDualService() {
        super();
        this.erService = new RedPayERService();
        this.epService = new RedPayEPService();
    }

    /**
     * Genera un token a partir de la instancia proporcionada.
     *
     * @param tokenInstance Instancia de {@link TokenBase} con la información necesaria para generar el token.
     * @param <T>           Tipo que extiende {@link TokenBase}.
     * @return Una respuesta de tipo {@link GenerateTokenResponse} con los detalles del token generado.
     * @throws Exception Si ocurre algún error durante la generación del token.
     */
    @Override
    public <T extends TokenBase> GenerateTokenResponse generateToken(T tokenInstance) throws Exception {
        return erService.generateToken(tokenInstance);
    }

    /**
     * Revoca un token utilizando la solicitud proporcionada.
     *
     * @param revokeTokenRequest Solicitud que contiene la información necesaria para revocar el token.
     * @return Una respuesta de tipo {@link RevokeTokenResponse} con los detalles de la revocación.
     * @throws Exception Si ocurre algún error durante la revocación del token.
     */
    @Override
    public RevokeTokenResponse revokeToken(RevokeTokenRequest revokeTokenRequest) throws Exception {
        return erService.revokeToken(revokeTokenRequest);
    }

    /**
     * Genera una devolución (chargeback) a partir de la solicitud proporcionada.
     *
     * @param chargebackRequest Solicitud que contiene los detalles para procesar la devolución.
     * @return Una respuesta de tipo {@link ChargebackResponse} con los detalles de la devolución.
     * @throws Exception Si ocurre algún error durante la generación de la devolución.
     */
    @Override
    public ChargebackResponse generateChargeback(ChargebackRequest chargebackRequest) throws Exception {
        return erService.generateChargeback(chargebackRequest);
    }

    /**
     * Autoriza un token utilizando la solicitud proporcionada.
     *
     * @param authorizeRequest Solicitud que contiene los datos necesarios para autorizar el token.
     * @return Una respuesta de tipo {@link AuthorizeResponse} con los detalles de la autorización.
     * @throws Exception Si ocurre algún error durante la autorización del token.
     */
    @Override
    public AuthorizeResponse authorizeToken(AuthorizeRequest authorizeRequest) throws Exception {
        return epService.authorizeToken(authorizeRequest);
    }
}
