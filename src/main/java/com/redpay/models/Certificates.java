package com.redpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa los certificados mTLS necesarios para la comunicación con RedPay.
 * <p>
 * Contiene la ruta del certificado, la ruta de la clave privada asociada y una bandera que indica
 * si se debe verificar la conexión SSL.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class Certificates {

    /**
     * Ruta del archivo del certificado.
     */
    String cert_path;

    /**
     * Ruta del archivo de la clave privada asociada al certificado.
     */
    String key_path;

    /**
     * Indica si se debe verificar la conexión SSL.
     * <p>
     * El valor por defecto es {@code true}.
     * </p>
     */
    Boolean verify_SLL = true;

}
