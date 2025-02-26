package com.redpay.services.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpay.enums.PathUrl;
import com.redpay.interfaces.RoleActionsER;
import com.redpay.models.RedPayBase;
import com.redpay.models.TokenBase;
import com.redpay.requests.ChargebackRequest;
import com.redpay.requests.RevokeTokenRequest;
import com.redpay.responses.ChargebackResponse;
import com.redpay.responses.GenerateTokenResponse;
import com.redpay.responses.RevokeTokenResponse;

import java.util.Map;

/**
 * Servicio de rol ER de RedPay que implementa la interfaz
 * {@link RoleActionsER}.
 * <p>
 * Esta clase extiende {@link RedPayBase} y proporciona implementaciones de los
 * métodos definidos en la interfaz {@link RoleActionsER}.
 * </p>
 */
public class RedPayERService extends RedPayBase implements RoleActionsER {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Genera un token a partir de la instancia de {@link TokenBase}
     * proporcionada.
     *
     * @param tokenInstance Instancia de {@code TokenBase} que contiene la
     * información para generar el token.
     * @param <T> Tipo que extiende de {@code TokenBase}.
     * @return Una respuesta de tipo {@link GenerateTokenResponse} con los
     * detalles del token generado.
     * @throws Exception Si ocurre un error durante la generación del token.
     */
    @Override
    public <T extends TokenBase> GenerateTokenResponse generateToken(T tokenInstance) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                tokenInstance, new TypeReference<Map<String, Object>>() {
        }
        );

        String jsonResponse = client.post(PathUrl.Generate.getPath(), body);

        return objectMapper.readValue(jsonResponse, GenerateTokenResponse.class);
    }

    /**
     * Revoca un token utilizando la solicitud proporcionada.
     *
     * @param revokeTokenRequest Solicitud que contiene la información para
     * revocar el token.
     * @return Una respuesta de tipo {@link RevokeTokenResponse} con los
     * detalles de la revocación.
     * @throws Exception Si ocurre un error durante la revocación del token.
     */
    @Override
    public RevokeTokenResponse revokeToken(RevokeTokenRequest revokeTokenRequest) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                revokeTokenRequest, new TypeReference<Map<String, Object>>() {
        }
        );

        String jsonResponse = client.post(PathUrl.Revoke.getPath(), body);

        return objectMapper.readValue(jsonResponse, RevokeTokenResponse.class);
    }

    /**
     * Genera una devolución (chargeback) a partir de la solicitud
     * proporcionada.
     *
     * @param chargebackRequest Solicitud que contiene la información necesaria
     * para procesar la devolución.
     * @return Una respuesta de tipo {@link ChargebackResponse} con los detalles
     * de la devolución.
     * @throws Exception Si ocurre un error durante la generación de la
     * devolución.
     */
    @Override
    public ChargebackResponse generateChargeback(ChargebackRequest chargebackRequest) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                chargebackRequest, new TypeReference<Map<String, Object>>() {
        }
        );

        String jsonResponse = client.post(PathUrl.Chargeback.getPath(), body);

        return objectMapper.readValue(jsonResponse, ChargebackResponse.class);
    }
}
