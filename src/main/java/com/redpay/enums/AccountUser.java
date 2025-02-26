package com.redpay.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * Enum que representa los tipos de cuenta que un usuario puede tener.
 *
 * <p>
 * - CUENTA_CORRIENTE: Representa una cuenta corriente con el código "001".<br>
 * - CUENTA_VISTA: Representa una cuenta vista con el código "002".
 * </p>
 */
@Getter
public enum AccountUser {
    
    CUENTA_CORRIENTE("001"),
    CUENTA_VISTA("002");

    private final String code;

    /**
     * Constructor que asigna el código correspondiente a cada tipo de cuenta.
     *
     * @param code Código asignado al tipo de cuenta.
     */
    AccountUser(String code) {
        this.code = code;
    }

    /**
     * Obtiene el código de la cuenta.
     * <p>
     * Este método se utiliza para la serialización a JSON, permitiendo que el valor
     * del enum se represente por su código.
     * </p>
     *
     * @return El código asociado a la cuenta.
     */
    @JsonValue
    public String getCode() {
        return code;
    }

    /**
     * Convierte un código en el enum {@code AccountUser}.
     * <p>
     * Este método se utiliza para la deserialización desde JSON, permitiendo convertir
     * un String que representa el código en la instancia correspondiente del enum.
     * </p>
     *
     * @param code El código de la cuenta a convertir.
     * @return La instancia de {@code AccountUser} que corresponde al código.
     * @throws IllegalArgumentException Si el código no coincide con ningún tipo de cuenta.
     */
    @JsonCreator
    public static AccountUser fromCode(String code) {
        for (AccountUser value : AccountUser.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código de la cuenta no reconocido: " + code);
    }

}
