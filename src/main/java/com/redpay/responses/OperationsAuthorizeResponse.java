package com.redpay.responses;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationsAuthorizeResponse {

    @NonNull
    private String generation_uuid;

    @NonNull
    private String verification_uuid;

    @NonNull
    private String authorization_uuid;
}
