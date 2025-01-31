package com.redpay.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @NonNull
    String token_uuid;

    @NonNull
    String user_id;

    @NonNull
    Integer amount;

    @NonNull
    Integer reusability = 1;

    Date revoket_at;
}
