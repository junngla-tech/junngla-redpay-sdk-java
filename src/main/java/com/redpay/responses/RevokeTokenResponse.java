package com.redpay.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la respuesta de revocación de un token.
 * <p>
 * Esta clase encapsula los detalles asociados a la revocación, incluyendo el UUID de la operación,
 * el monto, la glosa, los detalles, la fecha de revocación, la firma y una descripción adicional.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RevokeTokenResponse {
    
    /**
     * UUID de la operación para revocar el token.
     */
    @NonNull
    private String operation_uuid;
    
    /**
     * Monto asociado a la revocación del token.
     */
    private int amount;
    
    /**
     * Detalle de la revocación.
     */
    @NonNull
    private String detail;
    
    /**
     * Fecha y hora en que se realizó la revocación.
     */
    @NonNull
    private Date revoked_at;
    
    /**
     * Firma que valida la integridad de la respuesta.
     */
    @NonNull
    private String signature;
    
    /**
     * Descripción asociada al token revocado.
     */
    @NonNull
    private String description;
}
