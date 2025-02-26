package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.redpay.enums.TokenType;
import com.redpay.models.TokenData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta generada al crear un token.
 * <p>
 * Contiene información detallada del token, como su UUID, número, URL, y otros atributos relevantes.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TokenResponse {
    
    /**
     * Detalle del token.
     */
    @NonNull
    private String detail;
    
    /**
     * UUID del token.
     * Se mapea en JSON con la clave "token_uuid".
     */
    @NonNull
    @JsonProperty("token_uuid")
    private String uuid;
    
    /**
     * Tiempo de vida del token.
     */
    private int lifetime;
    
    /**
     * Información adicional asociada al token.
     */
    private String extra_data;
    
    /**
     * Número de reusabilidad del token.
     */
    private int reusability;
    
    /**
     * Identificador del usuario (comercio) enrolador.
     * Se mapea en JSON con la clave "enroller_user_id".
     */
    @JsonProperty("enroller_user_id")
    private String user_id;
    
    /**
     * Número asociado al token.
     * Se mapea en JSON con la clave "token_number".
     */
    @NonNull
    @JsonProperty("token_number")
    private String number;
    
    /**
     * URL asociada al token.
     * Se mapea en JSON con la clave "token_url".
     */
    @NonNull
    @JsonProperty("token_url")
    private String url;
    
    /**
     * Tipo de token.
     */
    @NonNull
    private TokenType token_type;
    
    /**
     * Datos adicionales asociados al token.
     */
    private TokenData data;
}
