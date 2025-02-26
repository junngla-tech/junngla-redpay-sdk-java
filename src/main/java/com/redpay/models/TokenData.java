package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que encapsula los datos asociados a un token T1.
 * <p>
 * Se utiliza para almacenar información adicional, como el monto.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenData {

    /**
     * Monto asociado al token.
     * <p>
     * Este campo se incluirá en la serialización JSON únicamente si su valor difiere del valor por defecto.
     * </p>
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int amount;
}
