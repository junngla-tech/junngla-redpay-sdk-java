package com.redpay.models;

import com.redpay.enums.AccountUser;
import com.redpay.enums.SbifCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class UserAccount {

    private int number;
    private SbifCode bank;
    private AccountUser type;
    private String tax_id;
}
