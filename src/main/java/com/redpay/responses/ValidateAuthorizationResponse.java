package com.redpay.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateAuthorizationResponse extends AuthorizeResponse {
    private int amount;

    private String payer_id;

    private String status_code;

    private String extra_data;
}
