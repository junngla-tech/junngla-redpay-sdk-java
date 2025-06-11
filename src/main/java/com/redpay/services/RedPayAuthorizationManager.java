package com.redpay.services;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redpay.config.ConstantsRedPay;
import com.redpay.config.CustomThreadFactory;
import com.redpay.exceptions.ApiError;
import com.redpay.exceptions.ImplementationError;
import com.redpay.exceptions.InvalidSignatureError;
import com.redpay.exceptions.OrderIsRevokedError;
import com.redpay.exceptions.OrderReuseLimitError;
import com.redpay.models.AuthorizeOrder;
import com.redpay.models.Order;
import com.redpay.models.RedPayConfig;
import com.redpay.models.WebhookPreAuthorization;
import com.redpay.provider.RedPayConfigProvider;
import com.redpay.requests.ValidateAuthorizationCollectorRequest;
import com.redpay.responses.ValidateAuthorizationResponse;

/**
 * Clase abstracta que gestiona el proceso de autorización.
 * <p>
 * Valida la firma del webhook, recupera la orden asociada al token, verifica el
 * estado de la orden (revocada o límite de reutilización) y procesa los eventos
 * de autorización. La clase también programa el procesamiento periódico de
 * órdenes de autorización pendientes.
 * </p>
 */
public abstract class RedPayAuthorizationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedPayAuthorizationManager.class);

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new CustomThreadFactory("RedPayAuthScheduler"));

    private ScheduledFuture<?> intervalFuture;
    private volatile boolean isProcessing = false;
    protected final RedPayService redPayService;
    /**
     * Configuración de RedPay obtenida a través de un proveedor de
     * configuración.
     */
    private final RedPayConfig config;

    /**
     * Servicio para la generación y validación de firmas de integridad.
     */
    private final RedPayIntegrityService integrityService;

    public RedPayAuthorizationManager() {
        this.config = RedPayConfigProvider.getInstance().getConfig();
        this.redPayService = new RedPayService();
        this.integrityService = new RedPayIntegrityService();
    }

    /**
     * Procesa un webhook de pre-autorización siguiendo un flujo predefinido:
     * <ol>
     * <li>Valida la firma del webhook.</li>
     * <li>Recupera la orden asociada al token_uuid del webhook.</li>
     * <li>Verifica si el código de estado del webhook es válido.</li>
     * <li>Si es válido, comprueba que la orden no esté revocada, valida su
     * límite de reutilización y activa el evento de pre-autorización.</li>
     * <li>De lo contrario, activa un evento informativo.</li>
     * </ol>
     *
     * @param webhook La carga útil del webhook de pre-autorización.
     * @throws Exception Si falla alguna validación o procesamiento.
     */
    public void processWebhookPreAuthorize(WebhookPreAuthorization webhook) throws Exception {
        validateSignature(webhook);
        String tokenUuid = webhook.getToken_uuid();
        Order order = getOrder(tokenUuid);
        if (checkStatusCodeFromWebhook(webhook)) {
            checkIfOrderIsRevoked(order);
            validateOrderReuse(order);
            onPreAuthorizeEvent(webhook, order);
        } else {
            onInfoEvent(webhook);
        }
    }

    /**
     * Valida la firma del webhook para asegurar la integridad del mensaje.
     *
     * @param webhook La carga útil del webhook.
     * @throws Exception Si la firma es inválida.
     */
    private void validateSignature(WebhookPreAuthorization webhook) throws Exception {
        String secretKey = config.getSecrets().getIntegrity();

        String signatureValid = this.integrityService.generateSignature(webhook, secretKey);

        if (!signatureValid.equals(webhook.getSignature())) {
            LOGGER.error("Firma inválida: {}", webhook.getSignature());
            throw new InvalidSignatureError();
        }
    }

    /**
     * Verifica si el código de estado del webhook indica un evento válido.
     *
     * @param webhook La carga útil del webhook.
     * @return true si el código de estado es igual a la constante
     * STATUS_CODE_OK, false en caso contrario.
     */
    private boolean checkStatusCodeFromWebhook(WebhookPreAuthorization webhook) {
        return webhook.getStatus_code().equals(ConstantsRedPay.STATUS_CODE_OK);
    }

    /**
     * Verifica si la orden dada ha sido revocada.
     *
     * @param order La orden a verificar.
     * @throws OrderIsRevokedError Si la orden ha sido revocada.
     */
    private void checkIfOrderIsRevoked(Order order) throws Exception {
        if (order.getRevoket_at() != null) {
            LOGGER.error("La orden ha sido revocada: {}", order.getRevoket_at());
            throw new OrderIsRevokedError();
        }
    }

    /**
     * Inicia el procesamiento periódico de órdenes de autorización pendientes.
     * <p>
     * El proceso se ejecuta cada 1000 milisegundos, recuperando órdenes
     * pendientes, procesando cada una y gestionando los errores según sea
     * necesario.
     * </p>
     */
    public void start() {
        if (intervalFuture != null && !intervalFuture.isCancelled()) {
            return;
        }

        intervalFuture = scheduler.scheduleAtFixedRate(() -> {

            if (isProcessing) {
                return;
            }

            isProcessing = true;

            try {
                List<AuthorizeOrder> orders = pendingAuthorizeOrders();

                if (orders.isEmpty()) {
                    stop();
                    return;
                }

                processAuthorizeOrders(orders);

            } catch (Exception e) {

                LOGGER.error("Error durante el procesamiento de órdenes", e);
                stop();

            } finally {
                isProcessing = false;
            }

        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Detiene el procesamiento periódico de órdenes.
     */
    public void stop() {
        if (intervalFuture != null) {
            intervalFuture.cancel(true);
            intervalFuture = null;
            isProcessing = false;
        }
    }

    /**
     * Procesa múltiples órdenes de autorización.
     *
     * @param authorizeOrders La lista de órdenes a procesar.
     * @throws Exception Si el procesamiento de alguna orden falla.
     */
    private void processAuthorizeOrders(List<AuthorizeOrder> authorizeOrders) throws Exception {
        for (AuthorizeOrder order : authorizeOrders) {
            processSingleAuthorization(order);
        }
    }

    /**
     * Procesa una única orden de autorización.
     *
     * @param authorizeOrder La orden a procesar.
     * @throws Exception Si el procesamiento falla.
     */
    private void processSingleAuthorization(AuthorizeOrder authorizeOrder) throws Exception {
        ValidateAuthorizationResponse response;
        try {
            ValidateAuthorizationCollectorRequest request = new ValidateAuthorizationCollectorRequest();
            request.setAuthorization_uuid(authorizeOrder.getAuthorization_uuid());
            request.setUser_id(authorizeOrder.getUser_id());

            response = redPayService.validateAuthorization(request);
        } catch (Exception e) {

            ApiError apiError;
            if (e instanceof ApiError apiError1) {
                apiError = apiError1;
            } else {
                apiError = new ApiError(e.getMessage(), null, null, null);
            }

            handleAuthorizationError(authorizeOrder, apiError);
            return;
        }

        if (response == null) {
            return;
        }

        try {
            onSuccess(authorizeOrder, response.getStatus_code());
        } catch (Exception e) {
            LOGGER.error("Error al procesar la orden de autorización en su implementación", e);
            throw new ImplementationError(e);
        }
    }

    /**
     * Maneja los errores durante el procesamiento de una orden de autorización.
     *
     * @param authorizeOrder La orden que causó el error.
     * @param e El error encontrado (siempre un ApiError).
     * @throws Exception Si el error no puede ser resuelto.
     */
    private void handleAuthorizationError(AuthorizeOrder authorizeOrder, ApiError e) throws Exception {
        if (ConstantsRedPay.STATUS_CODE_RETRY.equals(e.getStatusCode())) {
            LOGGER.warn("Error al procesar la orden de autorización, reintento en 2 segundos: {}", e.getMessage());
            // Espera 2 segundos y reintenta el procesamiento de la orden.
            Thread.sleep(2000);
            processSingleAuthorization(authorizeOrder);
        } else {
            LOGGER.error("Error al procesar la orden de autorización: {}", e.getMessage());
            onError(authorizeOrder, e.getStatusCode());
        }
    }

    /**
     * Valida que la orden pueda ser reutilizada.
     *
     * @param order La orden a validar.
     * @throws OrderReuseLimitError Si la orden ha superado su límite de
     * reutilización.
     */
    private void validateOrderReuse(Order order) throws Exception {
        int count = countAuthorizationByOrder(order.getToken_uuid());
        if (count == -1) {
            return;
        }
        if (order.getReusability() <= count) {
            LOGGER.error("La orden ha superado su límite de reutilización: {}", order.getReusability());
            throw new OrderReuseLimitError();
        }
    }

    /**
     * Cuenta el número de autorizaciones asociadas a una orden dada.
     * <p>
     * La implementación por defecto retorna -1, lo que indica que la
     * funcionalidad no está implementada. Sobrescribe este método para
     * proporcionar un conteo personalizado.
     * </p>
     *
     * @param tokenUuid El UUID del token de la orden.
     * @return El número de autorizaciones o -1 si no está implementado.
     */
    protected int countAuthorizationByOrder(String tokenUuid) {
        return -1;
    }

    // MÉTODOS ABSTRACTOS: Las subclases deben implementar estos métodos
    /**
     * Recupera la orden asociada al UUID del token dado.
     *
     * @param tokenUuid El UUID del token.
     * @return La orden.
     * @throws Exception Si no se puede recuperar la orden.
     */
    public abstract Order getOrder(String tokenUuid) throws Exception;

    /**
     * Recupera las órdenes de autorización pendientes.
     *
     * @return Una lista de órdenes de autorización pendientes.
     * @throws Exception Si falla la recuperación.
     */
    public abstract List<AuthorizeOrder> pendingAuthorizeOrders() throws Exception;

    /**
     * Maneja el evento de pre-autorización.
     *
     * @param webhook La carga útil del webhook de pre-autorización.
     * @param order La orden asociada.
     * @throws Exception Si falla el manejo del evento.
     */
    public abstract void onPreAuthorizeEvent(WebhookPreAuthorization webhook, Order order) throws Exception;

    /**
     * Maneja eventos informativos del webhook.
     *
     * @param webhook La carga útil del webhook.
     * @throws Exception Si falla el manejo del evento.
     */
    public abstract void onInfoEvent(WebhookPreAuthorization webhook) throws Exception;

    /**
     * Se invoca cuando una orden de autorización se procesa exitosamente.
     *
     * @param authorizeOrder La orden.
     * @param statusCode El código de estado de la respuesta de autorización.
     * @throws Exception Si falla el procesamiento.
     */
    public abstract void onSuccess(AuthorizeOrder authorizeOrder, String statusCode) throws Exception;

    /**
     * Se invoca cuando ocurre un error al procesar una orden de autorización.
     *
     * @param authorizeOrder La orden.
     * @param statusCode El código de estado de la respuesta de error.
     * @throws Exception Si falla el manejo del error.
     */
    public abstract void onError(AuthorizeOrder authorizeOrder, String statusCode) throws Exception;
}
