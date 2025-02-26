package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redpay.enums.AccountAuthorization;
import com.redpay.enums.SbifCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa una cuenta en el sistema.
 * <p>
 * Contiene información básica de la cuenta, incluyendo:
 * - Identificador único.
 * - Número de cuenta.
 * - Código SBIF que identifica la institución financiera.
 * - Tipo de autorización asociado a la cuenta.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class AccountEnrollerConfig {

    /**
     * Identificador único de la cuenta.
     */
    @JsonIgnore
    String id;
    
    /**
     * Número de la cuenta.
     */
    @JsonSerialize(using = IntToStringSerializer.class)
    @JsonDeserialize(using = StringToIntDeserializer.class)
    int number;
    
    /**
     * Código SBIF que identifica la institución financiera asociada.
     */
    @JsonProperty("sbif_code")
    SbifCode bank;
    
    /**
     * Tipo de autorización de la cuenta.
     */
    AccountAuthorization type;
}
