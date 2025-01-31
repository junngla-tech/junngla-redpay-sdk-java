package com.redpay.models;

import com.redpay.enums.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public abstract class ValidateAuthorization {
    String enroller_user_id;
    UserType user_type;
}
