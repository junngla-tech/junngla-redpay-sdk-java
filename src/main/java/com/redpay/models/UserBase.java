package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public abstract class UserBase  {
    private String user_id;
    private UserAccount account;
    private String email;
    private String name;
    private String tax_id;


    public abstract String getUser_type();
}
