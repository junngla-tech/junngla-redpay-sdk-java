package com.redpay.interfaces;

import com.redpay.models.TokenBase;
import com.redpay.requests.ChargebackRequest;
import com.redpay.requests.RevokeTokenRequest;
import com.redpay.responses.ChargebackResponse;
import com.redpay.responses.GenerateTokenResponse;
import com.redpay.responses.RevokeTokenResponse;

/**
 * Interfaz que extiende {@link RoleActions} y agrega operaciones relacionadas con el enrolador recaudador.
 */
public interface RoleActionsER extends RoleActions {

    /**
     * Genera un token a partir de la instancia de {@link TokenBase} proporcionada.
     *
     * @param tokenInstance Instancia de {@code TokenBase} que contiene la información necesaria para generar el token.
     * @param <T>           Tipo que extiende de {@code TokenBase}.
     * @return Una respuesta {@link GenerateTokenResponse} con los detalles del token generado.
     * @throws Exception Si ocurre algún error durante la generación del token.
     */
    public <T extends TokenBase> GenerateTokenResponse generateToken(T tokenInstance) throws Exception;

    /**
     * Revoca un token a partir de la solicitud proporcionada.
     *
     * @param revokeTokenRequest Solicitud que contiene la información necesaria para revocar el token.
     * @return Una respuesta {@link RevokeTokenResponse} con los detalles de la revocación del token.
     * @throws Exception Si ocurre algún error durante la revocación del token.
     */
    public RevokeTokenResponse revokeToken(RevokeTokenRequest revokeTokenRequest) throws Exception;

    /**
     * Genera una devolución (chargeback) a partir de la solicitud proporcionada.
     *
     * @param chargebackRequest Solicitud que contiene los detalles necesarios para procesar la devolución.
     * @return Una respuesta {@link ChargebackResponse} con la información resultante del proceso de devolución.
     * @throws Exception Si ocurre algún error durante la generación de la devolución.
     */
    public ChargebackResponse generateChargeback(ChargebackRequest chargebackRequest) throws Exception;
}
