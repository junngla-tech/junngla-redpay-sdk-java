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

public abstract class RedPayBase implements RoleActions {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected final RedPayClient client;

    public RedPayBase() {
        this(null);
    }

    public RedPayBase(RedPayClient client) {
        this.client = (client != null) ? client : new RedPayClient();
    }

    public <T extends UserBase> GenerateUserResponse createUser(T userInstance) throws Exception {

        Map<String, Object> body = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.post(PathUrl.User.name(), body);

        return objectMapper.readValue(
                jsonResponse, GenerateUserResponse.class
        );
    }

    public <T extends UserBase> GenerateUserResponse updateUser(T userInstance) throws Exception {

        Map<String, Object> body = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.put(PathUrl.User.name(), body);

        return objectMapper.readValue(
                jsonResponse, GenerateUserResponse.class
        );
    }

    public <T extends UserBase> GenerateUserResponse updateUserPartial(T userInstance) throws Exception {

        Map<String, Object> body = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.put(PathUrl.User.name(), body);

        return objectMapper.readValue(
                jsonResponse, GenerateUserResponse.class
        );
    }

    public <T extends UserBase> GenerateUserResponse getUser(T userInstance) throws Exception {

        Map<String, Object> params = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.get(PathUrl.User.name(), params);

        return objectMapper.readValue(
                jsonResponse, GenerateUserResponse.class
        );
    }

    public <T extends UserBase> GenerateUserResponse getUserOrFail(T userInstance) throws Exception {

        Map<String, Object> params = objectMapper.convertValue(
                userInstance, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.get(PathUrl.User.name(), params);

        return objectMapper.readValue(
                jsonResponse, GenerateUserResponse.class
        );
    }

    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws Exception {

        Map<String, Object> body = objectMapper.convertValue(
                validateTokenRequest, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.post(PathUrl.ValidateToken.name(), body);

        return objectMapper.readValue(
                jsonResponse, ValidateTokenResponse.class
        );
    }

    public <T extends ValidateAuthorization> ValidateAuthorizationResponse validateAuthorization(T validateAuthorization) throws Exception {

        Map<String, Object> body = objectMapper.convertValue(
                validateAuthorization, new TypeReference<Map<String, Object>>() {
                }
        );

        String jsonResponse = client.post(PathUrl.ValidateAuthorization.name(), body);

        return objectMapper.readValue(
                jsonResponse, ValidateAuthorizationResponse.class
        );
    }
}
