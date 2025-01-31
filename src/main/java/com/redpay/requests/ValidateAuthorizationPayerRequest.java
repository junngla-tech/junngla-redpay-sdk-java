package com.redpay.requests;

import com.redpay.models.ValidateAuthorization;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAuthorizationPayerRequest extends ValidateAuthorization {
    String authorization_id;
    String validation_uuid;
}
