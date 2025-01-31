package com.redpay.responses;

import com.redpay.enums.TokenType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateTokenResponse {
    @NonNull
    private String gloss;

    @NonNull
    private String detail;

    @NonNull
    private String token_uuid;

    @NonNull
    private TokenType token_type;

    private ValidateTokenResponse data;

    @NonNull
    private String operation_uuid;

    @NonNull
    private String signature;
}

