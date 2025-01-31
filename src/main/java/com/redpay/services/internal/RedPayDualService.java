package com.redpay.services.internal;

import com.redpay.models.RedPayBase;

public class RedPayDualService extends RedPayBase {

    private RedPayEPService redPayEPService;
    private RedPayERService redPayERService;

    public RedPayDualService() {
        super();
        initializeServices();
    }

    /**
     * Inicializa las instancias de los servicios `RedPayERService` y `RedPayEPService`.
     */
    private void initializeServices() {
        this.redPayERService = new RedPayERService();
        this.redPayEPService = new RedPayEPService();
    }

    // TODO: Finalizar implementación de los métodos de un ER y EP

}
