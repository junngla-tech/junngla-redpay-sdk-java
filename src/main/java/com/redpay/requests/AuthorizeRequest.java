package com.redpay.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.redpay.enums.AuthorizationMode;
import com.redpay.enums.TokenType;
import com.redpay.models.AuthorizationData;
import com.redpay.models.SignedAuthorizationAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una solicitud de autorización para un token.
 * <p>
 * Esta clase encapsula los datos necesarios para autorizar un token, incluyendo
 * el identificador del usuario enrolador, el UUID del token y otros atributos
 * relevantes. Los datos de la operación se generan dinámicamente en el método
 * {@code getData()}.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizeRequest {

    /**
     * Identificador del usuario enrolador.
     */
    @JsonProperty("enroller_user_id")
    private String user_id;

    /**
     * UUID del token.
     */
    @JsonProperty
    private String token_uuid;

    /**
     * UUID de la validación (operation_uuid de la validación) del token.
     */
    @JsonProperty("parent_uuid")
    private String validation_uuid;

    /**
     * Tipo de token.
     */
    @JsonProperty
    private TokenType token_type;

    /**
     * Monto asociado a la operación.
     * <p>
     * Este campo se ignora en la serialización JSON.
     * </p>
     */
    @JsonIgnore
    private int amount;

    /**
     * Genera dinámicamente los datos de la operación de autorización.
     * <p>
     * Se instancia un objeto {@link SignedAuthorizationAccount} configurado en
     * modo {@link AuthorizationMode#Authorize}. Dependiendo del tipo de token,
     * se asigna el monto a {@code amount} o a {@code max_amount} en el objeto
     * {@link AuthorizationData}.
     * </p>
     *
     * @return Un objeto {@link AuthorizationData} con la información de la
     * operación.
     * @throws Exception Si ocurre un error al configurar la autorización.
     */
    @JsonProperty(value = "data", access = Access.READ_ONLY)
    public AuthorizationData getData() throws Exception {
        SignedAuthorizationAccount filler = new SignedAuthorizationAccount();
        filler.setAuthorization(AuthorizationMode.Authorize);

        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setSignedAuthorizationAccount(filler);
        if (token_type != null && token_type != TokenType.T1) {
            authorizationData.setAmount(this.amount);
        } else {
            authorizationData.setMax_amount(this.amount);
        }
        return authorizationData;
    }
}
