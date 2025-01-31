package com.redpay.requests;

import com.redpay.enums.UserType;
import com.redpay.models.Geo;
import com.redpay.models.UserBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPayerRequest extends UserBase {
    Geo geo;

    @Override
    public String getUser_type() {
        return UserType.COLLECTOR.name();
    }
}
