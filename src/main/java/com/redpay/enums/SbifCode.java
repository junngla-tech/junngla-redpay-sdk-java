package com.redpay.enums;

/**
 * Representa los códigos SBIF (Superintendencia de Bancos e Instituciones Financieras)
 * para identificar instituciones financieras en Chile.
 */
public enum SbifCode {
    BANCO_DE_CHILE("001"), // Banco de Chile
    BANCO_INTERNACIONAL("009"), // Banco Internacional
    BANCO_ESTADO("012"), // Banco del Estado de Chile
    SCOTIABANK("014"), // Scotiabank Chile
    BCI("016"), // Banco de Crédito e Inversiones (BCI)
    CORPBANCA("027"), // Corpbanca
    BANCO_BICE("028"), // Banco BICE
    SANTANDER("037"), // Banco Santander Chile
    ITAU("039"), // Banco Itaú Chile
    SECURITY("049"), // Banco Security
    FALABELLA("051"), // Banco Falabella
    RIPLEY("053"), // Banco Ripley
    CONSORCIO("055"), // Banco Consorcio
    BBVA("504"), // BBVA
    COOPEUCH("672"), // Coopeuch
    CMR_FALABELLA("693"), // CMR Falabella
    PREPAGO_LOS_HEROES("729"), // Prepago Los Héroes
    PREPAGO_TENPO("730"); // Prepago Tenpo

    private final String code;

    SbifCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
