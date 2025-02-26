package com.redpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa una cuenta de autorización junto con su identificador.
 * <p>
 * Esta clase encapsula un identificador único y la configuración de la cuenta del enrolador.
 * </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorizationAccountWithId {
    
    /**
     * Identificador único asociado a la autorización.
     */
    private String id;
    
    /**
     * Configuración de la cuenta del enrolador.
     */
    private AccountEnrollerConfig account;
}
