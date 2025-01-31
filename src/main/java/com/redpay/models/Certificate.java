package com.redpay.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class Certificate  {

    String cert_path;
    String key_path;
    Boolean verify_SLL = true;

}
