package com.redpay.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * Enum que define los entornos disponibles para el servicio RedPay.
 * Cada valor es un entorno de ejecución del servicio RedPay.
 * <p>
 * Production: Entorno de producción, se utiliza para operaciones reales en un ambiente productivo. <br>
 * Integration: Entorno de integración, se utiliza para pruebas y desarrollo.
 * </p>
 */
@Getter
public enum RedPayEnvironment {
    Production("production"),
    Integration("integration");

    private final String environment;

    RedPayEnvironment(String environment) {
        this.environment = environment;
    }

}
