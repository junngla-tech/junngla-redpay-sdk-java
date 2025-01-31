package com.redpay.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class Settlement  {
    private SettlementSchedule schedule;
}