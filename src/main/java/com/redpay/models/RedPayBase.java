package com.redpay.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpay.enums.PathUrl;
import com.redpay.interfaces.RoleActions;
import com.redpay.requests.ValidateTokenRequest;
import com.redpay.responses.GenerateUserResponse;
import com.redpay.responses.ValidateAuthorizationResponse;
import com.redpay.responses.ValidateTokenResponse;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Clase base abstracta que implementa las operaciones definidas en {@link RoleActions}.
 * <p>
 * Esta clase provee implementaciones comunes para la creación, actualización, obtención y validación
 * de usuarios, tokens y autorizaciones a través del cliente HTTP {@link RedPayClient}.
 * </p>
 */
public abstract class RedPayBase implements RoleActions {

    /**
     * Objeto {@link ObjectMapper} utilizado para la conversión entre objetos Java y Mapas,
     * y para la serialización/deserialización de JSON.
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Cliente HTTP utilizado para comunicarse con los servicios de RedPay.
     */
    protected final RedPayClient client;

    /**
     * Constructor por defecto que inicializa el cliente con una instancia por defecto de {@link RedPayClient}.
     */
    public RedPayBase() {
        this(null);
    }

    /**
     * Constructor que permite especificar una instancia de {@link RedPayClient}.
     *
     * @param client Instancia de {@link RedPayClient} a utilizar. Si es nulo, se crea una instancia por defecto.
     */
    public RedPayBase(RedPayClient client) {
        this.client = (client != null) ? client : new RedPayClient();
    }

    /**
     * Crea un usuario en el sistema a partir de la instancia proporcionada.
     *
     * @param userInstance Instancia de {@code UserBase} que representa al usuario a crear.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta de tipo {@link GenerateUserResponse} que contiene los detalles del usuario creado.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    @Override
    public <T extends UserBase> GenerateUserResponse createUser(T userInstance) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {}
        );

        String jsonResponse = client.post(PathUrl.User.getPath(), body);

        return objectMapper.readValue(jsonResponse, GenerateUserResponse.class);
    }

    /**
     * Actualiza un usuario existente en el sistema a partir de la instancia proporcionada.
     *
     * @param userInstance Instancia de {@code UserBase} con la información actualizada del usuario.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta de tipo {@link GenerateUserResponse} con los detalles actualizados del usuario.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    @Override
    public <T extends UserBase> GenerateUserResponse updateUser(T userInstance) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {}
        );

        String jsonResponse = client.put(PathUrl.User.getPath(), body);

        return objectMapper.readValue(jsonResponse, GenerateUserResponse.class);
    }

    /**
     * Realiza una actualización parcial de un usuario existente en el sistema.
     * <p>
     * Combina la información actual del usuario con los nuevos datos proporcionados,
     * actualizando únicamente los campos no nulos del nuevo objeto.
     * </p>
     *
     * @param userInstance Instancia de {@code UserBase} con los datos parciales a actualizar.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta de tipo {@link GenerateUserResponse} con los detalles del usuario actualizado.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    @Override
    public <T extends UserBase> GenerateUserResponse updateUserPartial(T userInstance) throws Exception {
        GenerateUserResponse currentResponse = getUserOrFail(userInstance);

        Map<String, Object> currentUserMap = objectMapper.convertValue(
                currentResponse.getUser(), new TypeReference<Map<String, Object>>() {}
        );
        Map<String, Object> newUserMap = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {}
        );

        for (Map.Entry<String, Object> entry : newUserMap.entrySet()) {
            if (entry.getValue() != null) {
                currentUserMap.put(entry.getKey(), entry.getValue());
            }
        }

        String jsonResponse = client.put(PathUrl.User.getPath(), currentUserMap);
        return objectMapper.readValue(jsonResponse, GenerateUserResponse.class);
    }

    /**
     * Obtiene la información de un usuario a partir de la instancia proporcionada.
     *
     * @param userInstance Instancia de {@code UserBase} que identifica al usuario a obtener.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta de tipo {@link GenerateUserResponse} con los detalles del usuario.
     * @throws Exception Si ocurre algún error durante la operación.
     */
    @Override
    public <T extends UserBase> GenerateUserResponse getUser(T userInstance) throws Exception {
        return getGenerateUserResponse(userInstance);
    }

    /**
     * Obtiene la información de un usuario o lanza una excepción si el usuario no existe.
     *
     * @param userInstance Instancia de {@code UserBase} que identifica al usuario.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta de tipo {@link GenerateUserResponse} con los detalles del usuario.
     * @throws Exception Si ocurre algún error durante la operación o si el usuario no se encuentra.
     */
    @Override
    public <T extends UserBase> GenerateUserResponse getUserOrFail(T userInstance) throws Exception {
        return getGenerateUserResponse(userInstance);
    }

    /**
     * Método privado que realiza la obtención de la respuesta generada para un usuario.
     * <p>
     * Se extraen parámetros específicos (como "enroller_user_id" y "user_type") de la instancia de usuario,
     * y se realiza una solicitud GET al endpoint correspondiente.
     * </p>
     *
     * @param userInstance Instancia de {@code UserBase} que identifica al usuario.
     * @param <T>          Tipo que extiende de {@code UserBase}.
     * @return Una respuesta de tipo {@link GenerateUserResponse} con los detalles del usuario.
     * @throws com.fasterxml.jackson.core.JsonProcessingException Si ocurre un error al procesar el JSON.
     */
    private <T extends UserBase> GenerateUserResponse getGenerateUserResponse(T userInstance) throws com.fasterxml.jackson.core.JsonProcessingException {
        Map<String, Object> fullParams = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> params = Stream.of("enroller_user_id", "user_type")
                .filter(fullParams::containsKey)
                .collect(Collectors.toMap(key -> key, fullParams::get));

        String jsonResponse = client.get(PathUrl.UserVerify.getPath(), params);

        return objectMapper.readValue(jsonResponse, GenerateUserResponse.class);
    }

    /**
     * Valida un token utilizando la solicitud proporcionada.
     *
     * @param validateTokenRequest Objeto de solicitud que contiene el token a validar.
     * @return Una respuesta de tipo {@link ValidateTokenResponse} con los detalles de la validación.
     * @throws Exception Si ocurre algún error durante la validación del token.
     */
    @Override
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                validateTokenRequest, new TypeReference<Map<String, Object>>() {}
        );

        String jsonResponse = client.post(PathUrl.ValidateToken.getPath(), body);

        return objectMapper.readValue(jsonResponse, ValidateTokenResponse.class);
    }

    /**
     * Valida la autorización utilizando la información proporcionada.
     *
     * @param validateAuthorization Objeto que extiende de {@code ValidateAuthorization} y contiene
     *                              los datos necesarios para validar la autorización.
     * @param <T>                   Tipo que extiende de {@code ValidateAuthorization}.
     * @return Una respuesta de tipo {@link ValidateAuthorizationResponse} con los detalles de la autorización.
     * @throws Exception Si ocurre algún error durante la validación.
     */
    @Override
    public <T extends ValidateAuthorization> ValidateAuthorizationResponse validateAuthorization(T validateAuthorization) throws Exception {
        Map<String, Object> body = objectMapper.convertValue(
                validateAuthorization, new TypeReference<Map<String, Object>>() {}
        );

        String jsonResponse = client.post(PathUrl.ValidateAuthorization.getPath(), body);

        return objectMapper.readValue(jsonResponse, ValidateAuthorizationResponse.class);
    }
}
