package com.redpay.models;

import com.redpay.enums.ScheduleMode;
import com.redpay.enums.WithdrawalMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PredefinedSchedules {
    public static final Map<WithdrawalMode, Settlement> PREDEFINED = new HashMap<>();

    static {
        // DAILY
        Settlement daily = new Settlement();
        SettlementSchedule dailySchedule = new SettlementSchedule();
        dailySchedule.setMode(ScheduleMode.DAYS_OF_WEEK);
        dailySchedule.setValue(Arrays.asList(1, 2, 3, 4, 5));
        daily.setSchedule(dailySchedule);
        PREDEFINED.put(WithdrawalMode.DAILY, daily);

        // BIWEEKLY
        Settlement biweekly = new Settlement();
        SettlementSchedule biSchedule = new SettlementSchedule();
        biSchedule.setMode(ScheduleMode.DAYS_OF_MONTH);
        biSchedule.setValue(Arrays.asList(1, 15));
        biweekly.setSchedule(biSchedule);
        PREDEFINED.put(WithdrawalMode.BIWEEKLY, biweekly);

        // MONTHLY
        Settlement monthly = new Settlement();
        SettlementSchedule monthlySchedule = new SettlementSchedule();
        monthlySchedule.setMode(ScheduleMode.DAYS_OF_MONTH);
        monthlySchedule.setValue(Arrays.asList(1));
        monthly.setSchedule(monthlySchedule);
        PREDEFINED.put(WithdrawalMode.MONTHLY, monthly);

        // MANUAL
        PREDEFINED.put(WithdrawalMode.MANUAL, null);
    }
}