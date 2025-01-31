package com.redpay.requests;

import com.redpay.models.ValidateAuthorization;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class ValidateAuthorizationCollectorRequest extends ValidateAuthorization {
    String authorization_id;
}
