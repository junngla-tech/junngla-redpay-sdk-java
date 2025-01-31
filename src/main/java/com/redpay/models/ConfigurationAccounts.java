package com.redpay.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class ConfigurationAccounts {
    Account authorize;
    Account chargeback;
    Account chargeback_automatic;
}
