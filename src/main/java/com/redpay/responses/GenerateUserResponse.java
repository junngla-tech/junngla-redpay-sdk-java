package com.redpay.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class GenerateUserResponse {
    private User user;
    private String operation_id;
    private String signature;
}

