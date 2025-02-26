package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta generada al crear, actualizar u obtener un usuario.
 * <p>
 * Esta clase encapsula la firma de integridad, los detalles del usuario y el UUID de la operación asociada.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenerateUserResponse {

    /**
     * Firma de integridad de la respuesta.
     */
    @JsonProperty("signature")
    private String signature;

    /**
     * Detalles del usuario.
     */
    @JsonProperty("user")
    private UserResponse user;

    /**
     * UUID de la operación asociada.
     */
    @JsonProperty("operation_uuid")
    private String operation_uuid;
}
