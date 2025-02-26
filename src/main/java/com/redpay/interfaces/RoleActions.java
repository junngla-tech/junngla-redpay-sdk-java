package com.redpay.interfaces;

import com.redpay.models.UserBase;
import com.redpay.models.ValidateAuthorization;
import com.redpay.requests.ValidateTokenRequest;
import com.redpay.responses.GenerateUserResponse;
import com.redpay.responses.ValidateAuthorizationResponse;
import com.redpay.responses.ValidateTokenResponse;

/**
 * Interfaz que define las acciones relacionadas con la gestión de usuarios y roles en el sistema.
 * <p>
 * Proporciona métodos para crear, actualizar, obtener usuarios, validar tokens y autorizaciones.
 * </p>
 */
public interface RoleActions {

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param userInstance Instancia de {@code UserBase} que representa el usuario a crear.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta {@code GenerateUserResponse} que contiene los detalles del usuario creado.
     * @throws Exception Si ocurre algún error durante la creación del usuario.
     */
    public <T extends UserBase> GenerateUserResponse createUser(T userInstance) throws Exception;

    /**
     * Actualiza la información de un usuario existente en el sistema.
     *
     * @param userInstance Instancia de {@code UserBase} con la información actualizada del usuario.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta {@code GenerateUserResponse} que contiene los detalles actualizados del usuario.
     * @throws Exception Si ocurre algún error durante la actualización.
     */
    public <T extends UserBase> GenerateUserResponse updateUser(T userInstance) throws Exception;

    /**
     * Actualiza parcialmente la información de un usuario existente en el sistema.
     *
     * @param userInstance Instancia de {@code UserBase} con los datos parciales a actualizar.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta {@code GenerateUserResponse} con los detalles actualizados del usuario.
     * @throws Exception Si ocurre algún error durante la actualización parcial.
     */
    public <T extends UserBase> GenerateUserResponse updateUserPartial(T userInstance) throws Exception;

    /**
     * Obtiene la información de un usuario en el sistema.
     *
     * @param userInstance Instancia de {@code UserBase} que identifica al usuario a obtener.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta {@code GenerateUserResponse} que contiene los detalles del usuario.
     * @throws Exception Si ocurre algún error durante la obtención del usuario.
     */
    public <T extends UserBase> GenerateUserResponse getUser(T userInstance) throws Exception;

    /**
     * Obtiene la información de un usuario o lanza una excepción si el usuario no se encuentra.
     *
     * @param userInstance Instancia de {@code UserBase} que identifica al usuario.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta {@code GenerateUserResponse} con los detalles del usuario.
     * @throws Exception Si ocurre algún error o si el usuario no existe.
     */
    public <T extends UserBase> GenerateUserResponse getUserOrFail(T userInstance) throws Exception;

    /**
     * Valida un token proporcionado en la solicitud.
     *
     * @param validateTokenRequest Solicitud que contiene el token a validar.
     * @return Una respuesta {@code ValidateTokenResponse} con los detalles del token.
     * @throws Exception Si ocurre algún error durante la validación del token.
     */
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws Exception;

    /**
     * Valida la autorización de una transacción.
     *
     * @param validateAuthorization Objeto que extiende de {@code ValidateAuthorization} y contiene
     *                              la información necesaria para validar la autorización.
     * @param <T>                   Tipo que extiende de {@code ValidateAuthorization}.
     * @return Una respuesta {@code ValidateAuthorizationResponse} con los detalles de la autorización.
     * @throws Exception Si ocurre algún error durante la validación de la autorización.
     */
    public <T extends ValidateAuthorization> ValidateAuthorizationResponse validateAuthorization(T validateAuthorization) throws Exception;
}
