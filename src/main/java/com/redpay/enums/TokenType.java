package com.redpay.enums;

/**
 * Enum que define los tipos de tokens disponibles en el sistema.
 * <p>
 * <ul>
 *     <li>T0: Token de transacción</li>
 *     <li>T1: Token de suscripción</li>
 *     <li>T2: Token de cobro de suscripción</li>
 *     <li>T3: Token de envío de dinero (AliasSend)</li>
 *     <li>T4: Token de transacción con un alias (AliasPay)</li>
 *     <li>T6: Token del portal de recarga devoluciones</li>
 *  </ul>
 * <p>
 */
public enum TokenType {

    T0,
    T1,
    T2,
    T3,
    T4,
    T6
}

