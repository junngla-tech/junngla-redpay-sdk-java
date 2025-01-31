package com.redpay.models;

import com.redpay.enums.ScheduleMode;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class SettlementSchedule  {
    private ScheduleMode mode;
    private List<Integer> value;
}
