package com.redpay.interfaces;

import com.redpay.requests.AuthorizeRequest;
import com.redpay.responses.AuthorizeResponse;

/**
 * Interfaz que extiende {@link RoleActions} y agrega operaciones específicas para el rol de un enrolador pagador.
 */
public interface RoleActionsEP extends RoleActions {

    /**
     * Autoriza un token a partir de la solicitud proporcionada.
     *
     * @param authorizeRequest Solicitud que contiene los detalles necesarios para autorizar el token.
     * @return Una respuesta {@link AuthorizeResponse} con la información resultante de la autorización.
     * @throws Exception Si ocurre algún error durante el proceso de autorización.
     */
    public AuthorizeResponse authorizeToken(AuthorizeRequest authorizeRequest) throws Exception;
}
