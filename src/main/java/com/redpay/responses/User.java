package com.redpay.responses;

import com.redpay.models.Settlement;
import com.redpay.models.UserAccount;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull
    private String enroller_user_id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String tax_id;

    @NonNull
    private String user_type;

    @NonNull
    private UserAccount account;

    String tax_address;
    String gloss;
    String geo;
    String required_activation;
    Settlement settlement;
}
