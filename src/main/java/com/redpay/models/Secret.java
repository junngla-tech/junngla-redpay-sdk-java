package com.redpay.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Secret  {
    @NonNull
    private String integrity;

    private String authorize;
    private String chargeback;
    private String chargeback_automatic;
}