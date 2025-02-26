package com.redpay.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.enums.AuthorizationMode;
import com.redpay.models.RedPayConfig;
import com.redpay.models.SignedAuthorizationAccount;
import com.redpay.provider.RedPayConfigProvider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud de devolución (chargeback) en el sistema.
 * <p>
 * Esta clase encapsula la información necesaria para procesar una devolución,
 * incluyendo el identificador del comercio, el UUID de la autorización y el
 * monto. Además, genera dinámicamente las cuentas de autorización firmadas para
 * los modos CHARGEBACK y CHARGEBACK_AUTOMATIC durante la serialización.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChargebackRequest {

    /**
     * Identificador único del comercio que genera la solicitud.
     */
    @JsonProperty("enroller_user_id")
    private String user_id;

    /**
     * UUID de la autorización asociada a la devolución.
     */
    @JsonProperty
    private String authorization_uuid;

    /**
     * Monto asociado a la devolución.
     */
    @JsonProperty
    private int amount;

    /**
     * Cuenta de autorización firmada con el modo CHARGEBACK.
     * <p>
     * Este método se invoca durante la serialización para generar dinámicamente
     * el objeto de autorización, utilizando la configuración de secretos. Si no
     * se encuentra el secreto para chargeback, se retorna null.
     * </p>
     *
     * @return Una instancia de {@link SignedAuthorizationAccount} configurada
     * para CHARGEBACK, o null si no está configurado.
     * @throws Exception Si ocurre un error al generar la cuenta de
     * autorización.
     */
    @JsonProperty(value = "filler", access = JsonProperty.Access.READ_ONLY)
    public SignedAuthorizationAccount getFiller() throws Exception {
        RedPayConfig config = RedPayConfigProvider.getInstance().getConfig();
        if (config.getSecrets() == null || config.getSecrets().getChargeback() == null) {
            return null;
        }
        SignedAuthorizationAccount filler = new SignedAuthorizationAccount();
        filler.setAuthorization(AuthorizationMode.Chargeback);
        return filler;
    }

    /**
     * Cuenta de autorización firmada con el modo CHARGEBACK_AUTOMATIC.
     * <p>
     * Este método se invoca durante la serialización para generar dinámicamente
     * el objeto de autorización para devoluciones automáticas, utilizando la
     * configuración de secretos. Si no se encuentra el secreto para
     * chargeback_automatic, se retorna null.
     * </p>
     *
     * @return Una instancia de {@link SignedAuthorizationAccount} configurada
     * para CHARGEBACK_AUTOMATIC, o null si no está configurado.
     * @throws Exception Si ocurre un error al generar la cuenta de
     * autorización.
     */
    @JsonProperty(value = "debit_filler", access = JsonProperty.Access.READ_ONLY)
    public SignedAuthorizationAccount getDebitFiller() throws Exception {
        RedPayConfig config = RedPayConfigProvider.getInstance().getConfig();
        if (config.getSecrets() == null || config.getSecrets().getChargeback_automatic() == null) {
            return null;
        }
        SignedAuthorizationAccount filler = new SignedAuthorizationAccount();
        filler.setAuthorization(AuthorizationMode.Chargeback_Automatic);
        return filler;
    }
}
