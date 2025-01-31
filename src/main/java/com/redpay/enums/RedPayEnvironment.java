package com.redpay.enums;

/**
 * Enum que define los entornos disponibles para el servicio RedPay.
 * Cada valor es un entorno de ejecución del servicio RedPay.
 * <p>
 * Production: Entorno de producción, se utiliza para operaciones reales en un ambiente productivo. <br>
 * Integration: Entorno de integración, se utiliza para pruebas y desarrollo.
 * </p>
 */
public enum RedPayEnvironment {
    Production("production"),
    Integration("integration");

    private final String environment;

    RedPayEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }
}
