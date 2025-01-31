package com.redpay.models;

import com.redpay.enums.WithdrawalMode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class Withdrawal {
    private WithdrawalMode mode;
    private Settlement settlement;
}
