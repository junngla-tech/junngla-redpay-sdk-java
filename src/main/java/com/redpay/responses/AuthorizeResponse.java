package com.redpay.responses;

import com.redpay.models.Settlement;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeResponse {

    @NonNull
    private String token_uuid;

    @NonNull
    private OperationsAuthorizeResponse operations;

    @NonNull
    private boolean is_med;

    @NonNull
    private Date timestamp;

    // TODO: Implementar la clase SignedAuthorizationAccount
//    private SignedAuthorizationAccount signed_authorization_account;

    private Settlement settlement;

    @NonNull
    private String collector_id;

    @NonNull
    private String operation_uuid;

    @NonNull
    private String signature;
}


