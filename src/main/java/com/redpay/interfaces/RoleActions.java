package com.redpay.interfaces;

import com.redpay.models.UserBase;
import com.redpay.models.ValidateAuthorization;
import com.redpay.requests.ValidateTokenRequest;
import com.redpay.responses.GenerateUserResponse;
import com.redpay.responses.ValidateAuthorizationResponse;
import com.redpay.responses.ValidateTokenResponse;

public interface RoleActions {

    public <T extends UserBase> GenerateUserResponse createUser(T userInstance) throws Exception;

    public <T extends UserBase> GenerateUserResponse updateUser(T userInstance) throws Exception;

    public <T extends UserBase> GenerateUserResponse updateUserPartial(T userInstance) throws Exception;

    public <T extends UserBase> GenerateUserResponse getUser(T userInstance) throws Exception;

    public <T extends UserBase> GenerateUserResponse getUserOrFail(T userInstance) throws Exception;

    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws Exception;

    public <T extends ValidateAuthorization>ValidateAuthorizationResponse validateAuthorization(T validateAuthorization) throws Exception;
}
