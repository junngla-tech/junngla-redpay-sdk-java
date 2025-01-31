package com.redpay.provider;

import com.redpay.models.RedPayConfig;
import lombok.Getter;

import java.util.Set;

/**
 * Proveedor de configuración para RedPay.
 * Gestiona una única instancia de configuración global de RedPay.
 */
@Getter
public class RedPayConfigProvider {

    private static RedPayConfigProvider instance;
    private RedPayConfig config;

    // Constructor privado para evitar nuevas instancias
    private RedPayConfigProvider() {
    }

    /**
     * Obtiene la instancia única del proveedor.
     *
     * @return La instancia de RedPayConfigProvider.
     */
    public static synchronized RedPayConfigProvider getInstance() {
        if (instance == null) {
            instance = new RedPayConfigProvider();
        }
        return instance;
    }

    /**
     * Establece la configuración de RedPay. Solo puede configurarse una vez.
     *
     * @param config La configuración a establecer.
     * @throws IllegalStateException    Si la configuración ya fue establecida.
     * @throws IllegalArgumentException Si la configuración está incompleta.
     */
    public synchronized void setConfig(RedPayConfig config) {
        if (this.config != null) {
            throw new IllegalStateException(
                    "La configuración de RedPay ya está establecida y no puede ser modificada."
            );
        }

        // Validar que todos los campos obligatorios estén presentes
        validateConfig(config);

        // Asignar la configuración
        this.config = config;
    }

    /**
     * Válida que la configuración tenga todos los campos obligatorios.
     *
     * @param config Configuración a validar.
     * @throws IllegalArgumentException Sí falta algún campo obligatorio.
     */
    private void validateConfig(RedPayConfig config) {

        // Verificar que los campos obligatorios no sean nulos o vacíos
        if (config.getSecrets() == null) {
            throw new IllegalArgumentException("El campo 'secrets' es obligatorio y no puede ser nulo o vacío.");
        }
        if (config.getEnvironment() == null) {
            throw new IllegalArgumentException("El campo 'environment' es obligatorio y no puede ser nulo o vacío.");
        }
        if (config.getCertificate() == null) {
            throw new IllegalArgumentException("El campo 'certificate' es obligatorio y no puede ser nulo o vacío.");
        }
        if (config.getType() == null) {
            throw new IllegalArgumentException("El campo 'type' es obligatorio y no puede ser nulo o vacío.");
        }
    }
}

