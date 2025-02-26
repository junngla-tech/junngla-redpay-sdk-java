package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redpay.enums.AccountUser;
import com.redpay.enums.SbifCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una cuenta de usuario en el sistema.
 * <p>
 * Incluye el número de cuenta, el banco asociado, el tipo de cuenta y el identificador tributario.
 * El número de cuenta se serializa a JSON como cadena y se deserializa como entero.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAccount {

    /**
     * Número de la cuenta.
     * <p>
     * Se serializa a JSON como String usando {@link com.redpay.models.IntToStringSerializer} y
     * se deserializa a entero usando {@link com.redpay.models.StringToIntDeserializer}.
     * </p>
     */
    @JsonProperty("id")
    @JsonSerialize(using = IntToStringSerializer.class)
    @JsonDeserialize(using = StringToIntDeserializer.class)
    private int number;

    /**
     * Código del banco asociado a la cuenta.
     * <p>
     * Se mapea en JSON con la clave "owner_id" y se representa mediante la enumeración {@link SbifCode}.
     * </p>
     */
    @JsonProperty("owner_id")
    private SbifCode bank;

    /**
     * Tipo de cuenta, representado por la enumeración {@link AccountUser}.
     */
    private AccountUser type;

    /**
     * Identificador tributario asociado a la cuenta.
     */
    private String tax_id;
}
