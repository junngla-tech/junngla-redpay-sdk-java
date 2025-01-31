package com.redpay.requests;

import com.redpay.enums.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class ValidateTokenRequest {
    String enroller_user_id;
    String token_uuid;
    UserType user_type;
}
