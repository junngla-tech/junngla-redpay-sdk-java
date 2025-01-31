package com.redpay.models;

import com.redpay.enums.Enroller;
import com.redpay.enums.RedPayEnvironment;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RedPayConfig {

    Secret secrets;
    RedPayEnvironment environment;
    Certificate certificate;
    Enroller type;

    @NonNull
    ConfigurationAccounts accounts;

}
