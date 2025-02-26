package com.redpay.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * Representa los códigos SBIF (Superintendencia de Bancos e Instituciones Financieras)
 * para identificar instituciones financieras en Chile.
 */
@Getter
public enum SbifCode {
    BANCO_DE_CHILE("001"),
    BANCO_INTERNACIONAL("009"),
    BANCO_ESTADO("012"),
    SCOTIABANK("014"),
    BCI("016"),
    CORPBANCA("027"),
    BANCO_BICE("028"),
    SANTANDER("037"),
    ITAU("039"),
    SECURITY("049"),
    FALABELLA("051"),
    RIPLEY("053"),
    CONSORCIO("055"),
    BBVA("504"),
    COOPEUCH("672"),
    CMR_FALABELLA("693"),
    PREPAGO_LOS_HEROES("729"),
    PREPAGO_TENPO("730");

    /**
     * Código SBIF asociado a la institución financiera.
     */
    private final String code;

    /**
     * Constructor que asigna el código SBIF correspondiente a cada institución financiera.
     *
     * @param code Código SBIF asignado a la institución financiera.
     */
    SbifCode(String code) {
        this.code = code;
    }

    /**
     * Retorna el código SBIF de la institución financiera.
     * <p>
     * Este método se utiliza durante la serialización a JSON.
     * </p>
     *
     * @return El código SBIF asociado a la institución financiera.
     */
    @JsonValue
    public String getCode() {
        return code;
    }

    /**
     * Crea una instancia de {@code SbifCode} a partir del código SBIF proporcionado.
     * <p>
     * Este método se utiliza para la deserialización desde JSON.
     * </p>
     *
     * @param code El código SBIF a convertir.
     * @return La instancia de {@code SbifCode} que coincide con el código proporcionado.
     * @throws IllegalArgumentException Si el código no coincide con ninguna institución financiera.
     */
    @JsonCreator
    public static SbifCode fromCode(String code) {
        for (SbifCode value : SbifCode.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código de banco no reconocido: " + code);
    }

}
