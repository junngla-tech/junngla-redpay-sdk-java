package com.redpay.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de operaciones de autorización.
 * <p>
 * Esta clase encapsula los UUIDs generados durante el proceso de autorización:
 * - generation_uuid: UUID operacional de la generación del token autorizado.
 * - verification_uuid: UUID operacional de la verificación de la autorización.
 * - authorization_uuid: UUID asignado a la autorización.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OperationsAuthorizeResponse {

    /**
     * UUID operacional de la generación del token autorizado.
     */
    @JsonProperty
    private String generation_uuid;

    /**
     * UUID operacional de la verificación de la autorización.
     */
    @JsonProperty
    private String verification_uuid;

    /**
     * UUID asignado a la autorización.
     */
    @JsonProperty
    private String authorization_uuid;
}
