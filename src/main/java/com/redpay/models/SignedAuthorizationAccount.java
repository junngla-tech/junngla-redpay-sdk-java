package com.redpay.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redpay.enums.AuthorizationMode;
import com.redpay.provider.RedPayConfigProvider;
import com.redpay.services.RedPayIntegrityService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una cuenta de autorización firmada que contiene la información necesaria
 * para realizar operaciones de autorización según un modo específico.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"integrityService", "config"})
public class SignedAuthorizationAccount {
    @JsonIgnore
    private final RedPayIntegrityService integrityService;
    
    @JsonIgnore
    private final RedPayConfig config;
    
    private String id;
    private AccountEnrollerConfig account;
    private long timestamp;
    private String signature;

    /**
     * Constructor por defecto que inicializa la configuración y el servicio de integridad.
     */
    public SignedAuthorizationAccount() {
        this.config = RedPayConfigProvider.getInstance().getConfig();
        this.integrityService = new RedPayIntegrityService();
    }

    /**
     * Configura el SignedAuthorizationAccount de acuerdo al modo de autorización.
     * <p>
     * Este método asigna el identificador, la cuenta, la marca de tiempo, y genera la firma
     * basada en los datos de la cuenta y el secreto correspondiente.
     * </p>
     *
     * @param mode El modo de autorización a utilizar.
     * @throws Exception Si ocurre un error durante la generación de la firma.
     */
    public void setAuthorization(AuthorizationMode mode) throws Exception {
        RedPayConfig config = RedPayConfigProvider.getInstance().getConfig();

        // Obtiene la cuenta y el identificador correspondiente al modo de autorización.
        AuthorizationAccountWithId accountWithId = getAccountForMode(config, mode);
        this.id = accountWithId.getId();
        this.account = accountWithId.getAccount();
        this.timestamp = System.currentTimeMillis();

        String secret = getSecretForMode(config, mode);

        // Prepara los datos necesarios para generar la firma.
        Map<String, Object> dataForSignature = new HashMap<>();
        dataForSignature.put("id", this.id);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> accountMap = mapper.convertValue(this.account, Map.class);
        dataForSignature.put("account", accountMap);
        dataForSignature.put("timestamp", this.timestamp);

        // Genera la firma utilizando el servicio de integridad.
        this.signature = integrityService.generateSignature(dataForSignature, secret);
    }

    /**
     * Obtiene el secreto correspondiente al modo de autorización.
     *
     * @param config La configuración actual de RedPay.
     * @param mode   El modo de autorización.
     * @return El secreto asociado al modo especificado.
     */
    private String getSecretForMode(RedPayConfig config, AuthorizationMode mode) {
        return switch (mode) {
            case Authorize -> ensureSecret(config.getSecrets().getAuthorize());
            case Chargeback -> ensureSecret(config.getSecrets().getChargeback());
            case Chargeback_Automatic -> ensureSecret(config.getSecrets().getChargeback_automatic());
            default -> throw new IllegalArgumentException("Authorization mode \"" + mode + "\" is not supported.");
        };
    }

    /**
     * Obtiene la cuenta asociada al modo de autorización.
     *
     * @param config La configuración actual de RedPay.
     * @param mode   El modo de autorización.
     * @return Un objeto {@code AuthorizationAccountWithId} que contiene la cuenta y su identificador.
     */
    private AuthorizationAccountWithId getAccountForMode(RedPayConfig config, AuthorizationMode mode) {
        return switch (mode) {
            case Authorize -> ensureAccount(config.getAccounts().getAuthorize());
            case Chargeback -> ensureAccount(config.getAccounts().getChargeback());
            case Chargeback_Automatic -> ensureAccount(config.getAccounts().getChargeback_automatic());
            default -> throw new IllegalArgumentException("Authorization mode \"" + mode + "\" is not supported.");
        };
    }

    /**
     * Valida que la cuenta no sea nula y construye un objeto {@code AuthorizationAccountWithId}.
     *
     * @param account La configuración de la cuenta del enrolador.
     * @return Un objeto {@code AuthorizationAccountWithId} construido a partir de la cuenta.
     */
    private AuthorizationAccountWithId ensureAccount(AccountEnrollerConfig account) {
        if (account == null) {
            throw new IllegalArgumentException("Account is undefined for the given mode.");
        }
        return new AuthorizationAccountWithId(account.getId(), account);
    }

    /**
     * Asegura que el secreto no sea nulo o vacío.
     *
     * @param secret El secreto a validar.
     * @return El secreto si es válido.
     */
    private String ensureSecret(String secret) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("Secret is undefined for the given mode.");
        }
        return secret;
    }
}
