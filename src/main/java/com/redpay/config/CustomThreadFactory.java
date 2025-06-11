package com.redpay.config;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomThreadFactory es una implementación personalizada de la interfaz {@link ThreadFactory}
 * que crea hilos con nombres prefijados y asigna un {@code UncaughtExceptionHandler} para capturar
 * y registrar cualquier excepción no manejada en dichos hilos.
 *
 * <p>
 * Cada hilo creado se nombra utilizando el prefijo especificado seguido de un contador único.
 * El UncaughtExceptionHandler registra cualquier error que ocurra en el hilo.
 * </p>
 *
 */
public class CustomThreadFactory implements ThreadFactory {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadFactory.class);
    
    /** Prefijo para nombrar los hilos creados. */
    private final String threadPrefix;
    
    /** Contador interno para garantizar nombres únicos en los hilos. */
    private int counter = 1;

    /**
     * Crea una nueva instancia de CustomThreadFactory con el prefijo especificado.
     *
     * @param threadPrefix El prefijo que se usará para nombrar los hilos.
     */
    public CustomThreadFactory(String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }

    /**
     * Crea un nuevo hilo para ejecutar la tarea especificada.
     * <p>
     * El hilo se nombra utilizando el prefijo definido y un contador único, y se le asigna un
     * UncaughtExceptionHandler que registra cualquier excepción no capturada.
     * </p>
     *
     * @param r La tarea {@code Runnable} que el hilo debe ejecutar.
     * @return Un nuevo hilo configurado para ejecutar la tarea.
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, threadPrefix + "-" + counter++);
        thread.setUncaughtExceptionHandler((t, e) -> {
            logger.error("Excepción no capturada en el hilo {}: {}", t.getName(), e.getMessage(), e);
        });
        return thread;
    }
}
