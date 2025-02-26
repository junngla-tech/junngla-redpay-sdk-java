package com.redpay.services.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpay.enums.PathUrl;
import com.redpay.interfaces.RoleActionsEP;
import com.redpay.models.RedPayBase;
import com.redpay.requests.AuthorizeRequest;
import com.redpay.responses.AuthorizeResponse;

import java.util.Map;

/**
 * Servicio de rol EP de RedPay que implementa la interfaz
 * {@link RoleActionsEP}.
 * <p>
 * Esta clase extiende {@link RedPayBase} y implementa la interfaz
 * {@link RoleActionsEP}, proporcionando la implementación del método para
 * autorizar tokens.
 * </p>
 */
public class RedPayEPService extends RedPayBase implements RoleActionsEP {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Autoriza un token.
     * <p>
     * Este método convierte la solicitud en un mapa de parámetros, realiza una
     * petición POST al endpoint de autorización, y deserializa la respuesta
     * JSON en un objeto {@link AuthorizeResponse}.
     * </p>
     *
     * @param authorizeRequest La solicitud de autorización que contiene los
     * datos necesarios.
     * @return La respuesta de autorización, encapsulada en un objeto
     * {@link AuthorizeResponse}.
     * @throws Exception Si ocurre un error durante la conversión, la
     * comunicación o la deserialización.
     */
    @Override
    public AuthorizeResponse authorizeToken(AuthorizeRequest authorizeRequest) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                authorizeRequest, new TypeReference<Map<String, Object>>() {
        }
        );

        String jsonResponse = client.post(PathUrl.Authorize.getPath(), body);

        return objectMapper.readValue(jsonResponse, AuthorizeResponse.class);
    }
}
