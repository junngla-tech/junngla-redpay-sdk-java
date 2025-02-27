package com.redpay.models;

import com.redpay.enums.Enroller;
import com.redpay.enums.RedPayEnvironment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que encapsula la configuración para la integración con RedPay.
 * <p>
 * Esta configuración incluye los secretos de autenticación, el entorno de operación,
 * el certificado para mTLS, el tipo de enrolador y la configuración de cuentas asociada.
 * </p>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RedPayConfig {

    /**
     * Sección de secretos o credenciales necesarios para generar la firma de las solicitudes y/o fillers.
     */
    Secrets secrets;
    
    /**
     * Entorno de operación de RedPay (por ejemplo, Producción o Integración).
     */
    RedPayEnvironment environment;
    
    /**
     * Certificado utilizado para la autenticación mTLS.
     */
    Certificates certificate;
    
    /**
     * Tipo de enrolador configurado.
     */
    Enroller type;
    
    /**
     * Configuración de las cuentas asociadas a la integración.
     */
    @NonNull
    ConfigurationAccounts accounts;
}
