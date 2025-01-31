package com.redpay.models;

import com.redpay.enums.AccountAuthorization;
import com.redpay.enums.SbifCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class AuthorizationAccount  {

    Integer number;
    SbifCode sbif_code;
    AccountAuthorization type;
}
