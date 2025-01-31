package com.redpay.requests;

import com.redpay.enums.UserType;
import com.redpay.enums.WithdrawalMode;
import com.redpay.models.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class UserCollectorRequest extends UserBase {
    private String tax_address;
    private String gloss;
    private Geo geo;
    private Withdrawal withdrawal;

    @Override
    public String getUser_type() {
        return UserType.COLLECTOR.name();
    }
}
