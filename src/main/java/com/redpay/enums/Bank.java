package com.redpay.enums;

/**
 * Enum que representa los códigos de los principales bancos y emisores de Chile.
 * Cada valor es un código único que identifica a una institución financiera.
 */
public enum Bank {

    BANCO_DE_CHILE("001"),
    BANCO_INTERNACIONAL("009"),
    BANCO_DEL_ESTADO_DE_CHILE("012"),
    SCOTIABANK_CHILE("014"),
    BANCO_DE_CREDITO_E_INVERSIONES("016"),
    CORPBANCA("027"),
    BANCO_BICE("028"),
    BANCO_SANTANDER_CHILE("037"),
    BANCO_ITAU_CHILE("039"),
    BANCO_SECURITY("049"),
    BANCO_FALABELLA("051"),
    BANCO_RIPLEY("053"),
    BANCO_CONSORCIO("055"),
    BBVA("504"),
    COOPEUCH("672"),
    CMR_FALABELLA("693"),
    PREPAGO_LOS_HEROES("729"),
    PREPAGO_TENPO("730");

    private final String code;

    Bank(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
