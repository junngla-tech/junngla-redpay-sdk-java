# RedPay SDK Java

RedPay SDK Java es una biblioteca diseñada para facilitar la integración con los servicios de RedPay en aplicaciones Java. Proporciona herramientas completas para la gestión de usuarios, generación y validación de tokens, manejo de autorizaciones y control de integridad en todas las interacciones con los servicios de RedPay.

> **Estado del SDK:** Versión BETA  
> Durante esta fase, estamos trabajando continuamente para mejorar la funcionalidad y confiabilidad del SDK. Valoramos tus comentarios y sugerencias. Si encuentras algún problema o deseas compartir ideas, contáctanos a: soporteqri@junngla.com

---

# Tabla de contenidos

- [Instalación](#instalación)
- [RedPayClient: Gestión de peticiones HTTP](#redpayclient-gestión-de-peticiones-http)
- [Enrolador recaudador (Collector)](#enrolador-recaudador-collector)
  - [Configuración inicial](#configuración-inicial)
  - [RedPayService](#redpayservice)
    - [Métodos generales](#métodos-generales)
    - [Métodos específicos](#métodos-específicos)
    - [Gestión de tokens](#gestión-de-tokens)
        - [Tipos](#tipos)
        - [Generación](#generación)
        - [Revocación](#revocación)
        - [Validación (opcional)](#validación-opcional)
    - [Validación de autorización](#validación-de-autorización)
    - [Detalles de la devolución](#detalles-de-la-devolución)
- [Enrolador pagador (Billetera digital)](#enrolador-pagador-billetera-digital)
    - [Configuración inicial](#configuración-inicial-1)
    - [RedPayService](#redpayservice-1)
        - [Métodos generales](#métodos-generales-1)
        - [Métodos específicos](#métodos-específicos-1)
        - [Gestión de usuarios](#gestión-de-usuarios)
        - [Validación de token](#validación-de-token)
            - [Tipos de tokens](#tipos-de-tokens)
        - [Autorización de transacciones](#autorización-de-transacciones)
        - [Validación de autorización (opcional)](#validación-de-autorización-opcional)
- [Enrolador dual (Recaudador y Pagador)](#enrolador-dual-recaudador-y-pagador)
    - [Requisitos para implementar un Enrolador Dual](#requisitos-para-implementar-un-enrolador-dual)
- [Control de integridad](#control-de-integridad)
    - [Servicio de integridad](#servicio-de-integridad)
    - [Métodos disponibles](#métodos-disponibles)
- [Colaboración](#colaboración)
    - [Reporte de problemas de seguridad](#reporte-de-problemas-de-seguridad)
- [Documentación](#documentación)
- [API](#api)

---

# Instalación

Para incluir el SDK en tu proyecto Java, agrega la dependencia en tu archivo de configuración (por ejemplo, Maven o Gradle).

**Ejemplo con Maven:**

```xml
<dependency>
    <groupId>com.redpay</groupId>
    <artifactId>redpay-sdk-java</artifactId>
    <version>0.1.0-beta</version>
</dependency>
```

---

# RedPayClient: Gestión de peticiones HTTP

El RedPayClient es la clase central que se encarga de realizar peticiones HTTP a los servicios de RedPay. Entre sus características se destacan:

- Firma Automática: Todas las peticiones se firman utilizando HMAC SHA256 con el secreto de integridad configurado.
- Validación de Respuestas: Se verifica la firma en las respuestas para asegurar la integridad de los datos.

---

# Enrolador recaudador (Collector)

## Configuración inicial

La configuración global es única y se realiza una sola vez. Debes definir certificados mTLS, secretos, cuentas y el entorno (producción o integración).

**Ejemplo de configuración**

```java
Certificate certificates = new Certificates("private.key", "certificate.crt");

Secrets secrets = new Secrets();
secrets.setIntegrity("integrity-secret");
secrets.setCharbeback("chargeback-secret"); // Opcional - Solo si se requiere realizar devoluciones
secrets.setChargebackAutomatic("chargeback-automatic-secret"); // Opcional - Solo si se requiere realizar devoluciones automáticas

AccountEnrollerConfig chargeback = new AccountEnrollerConfig();
chargeback.setId("id_reversa");
chargeback.setNumber(12345678);
chargeback.setType(AccountAuthorization.CORRIENTE);
chargeback.setBank(SbifCode.BANCO_BICE);


AccountEnrollerConfig chargeback_automatic = new AccountEnrollerConfig();
chargeback_automatic.setId("rev_auto_reversa");
chargeback_automatic.setNumber(12345678);
chargeback_automatic.setType(AccountAuthorization.CORRIENTE);
chargeback_automatic.setBank(SbifCode.BANCO_BICE);

ConfigurationAccounts accounts = new ConfigurationAccounts();
accounts.setChargeback(chargeback);
accounts.setChargeback_automatic(chargeback_automatic);

RedPayConfig config = new RedPayConfig();
config.setCertificate(certificates);
config.setSecrets(secrets);
config.setEnvironment(RedPayEnvironment.Integration);
config.setType(Enroller.COLLECTOR);
config.setAccounts(accounts);
```

---

## RedPayService

Este servicio ofrece las siguientes funcionalidades para las integraciones de tipo **Enrolador Recaudador**:

### Métodos generales

- `createUser`: Crear un usuario recolector.
- `updateUser` / `updateUserPartial`: Actualizar la información del usuario.
- `getUser` / `getUserOrFail`: Obtener datos del usuario.

### Métodos específicos:

- `generateToken`: Genera un token para operaciones de compra, suscripción, entre otras.
- `validateToken`: Validar los detalles de un token (opcional).
- `revokeToken`: Revocar un token existente.
- `generateChargeback`: Realizar un contra cargo (devolución).
- `validateAuthorization`: Valida el estado final de una autorización.

**Ejemplo de implementación: Generación de usuario (comercio)**

```java
// Cuenta del usuario (comercio)
UserAccount userAccount = new UserAccount();
userAccount.setNumber(22222222);
userAccount.setType(AccountUser.CUENTA_CORRIENTE);
userAccount.setBank(SbifCode.BANCO_BICE);
userAccount.setTax_id("59873050-4");  // El RUT de la cuenta debe ser el mismo que el RUT del comercio

Geo geo = new Geo();
geo.setLat(-33.4489);
geo.setLng(-70.6693);

// Configuración de retiro del Portal de Cartolas
Withdrawal withdrawal = new Withdrawal();
withdrawal.setMode(WithdrawalMode.DAILY);

UserCollectorRequest userCollectorRequest = new UserCollectorRequest();
userCollectorRequest.setUser_id("demo");
userCollectorRequest.setEmail("example@example.com");
userCollectorRequest.setName("Comercio de prueba");
userCollectorRequest.setAccount(userAccount);
userCollectorRequest.setGeo(geo);
userCollectorRequest.setTax_address("Calle de fantasia 123");
userCollectorRequest.setTax_id("59873050-4");
userCollectorRequest.setGloss("Comercio de prueba");
userCollectorRequest.setWithdrawal(withdrawal);

RedPayService redpayService = new RedPayService();

try {
    GenerateUserResponse userCreated = redpayService.createUser(userCollectorRequest);
} catch (Exception e) {
    System.out.println("Error al crear el usuario: " + e.getMessage());
}
```

#### Withdrawal

El objeto `Withdrawal` se utiliza para definir el modo de retiro de fondos de un usuario recolector que utiliza el Portal de Cartolas. Los modos disponibles son:

- `WithdrawalMode.MONTHLY`: Retiro mensual.
- `WithdrawalMode.WEEKLY`: Retiro quincenal.
- `WithdrawalMode.DAILY`: Retiro diario.
- `WithdrawalMode.MANUAL`: Retiro manual (personalizado).

Para el modo `MANUAL`, se debe definir el campo `settlement` con la frecuencia de retiro deseada.

### Gestión de tokens

Los tokens son componentes esenciales para las operaciones en RedPay. La librería permite manejar diversos tipos de tokens (T0, T1, T2, T3, T4), cada uno con características específicas.

#### Tipos

- T0: Token de transacción.
- T1: Token de suscripción.
- T2: Token de cobro de suscripción.
- T3: Token de envío de dinero.
- T4: Token de transacción con un alias.

#### Generación

**Ejemplo de implementación: Generación de token**

```java
TokenT0Request tokenRequest = new TokenT0Request();
tokenRequest.setUser_id("demo");
tokenRequest.setDetail("Detalle de token de prueba");
tokenRequest.setData(new TokenData(3000));
tokenRequest.setReusability(1); // Opcional - Número de usos permitidos, por defecto es 1
tokenRequest.setLifetime(300); // Opcional - Tiempo de vida del token en segundos, por defecto es 300
tokenRequest.setExtra_data("Datos extra de prueba");

RedPayService redpayService = new RedPayService();

try {
    GenerateTokenResponse tokenCreated = redpayService.generateToken(tokenRequest);
} catch (Exception e) {
    System.out.println("Error al generar el token: " + e.getMessage());
}
```

#### Revocación

**Ejemplo de implementación: Revocación de token**

```java
RevokeTokenRequest revokeTokenRequest = new RevokeTokenRequest();
revokeTokenRequest.setToken_uuid("token-uuid");
revokeTokenRequest.setUser_id("demo");

RedPayService redpayService = new RedPayService();

try {
    RevokeTokenResponse tokenRevoked = redpayService.revokeToken(revokeTokenRequest);
} catch (Exception e) {
    System.out.println("Error al revocar el token: " + e.getMessage());
}
```

#### Validación (opcional)
**Ejemplo de implementación: Validación de token (opcional)**

```java
ValidateTokenRequest validateTokenRequest = new ValidateTokenRequest();
validateTokenRequest.setToken_uuid("token-uuid");
validateTokenRequest.setUser_id("demo");
validateTokenRequest.setUser_type(UserType.COLLECTOR);

RedPayService redpayService = new RedPayService();

try {
    ValidateTokenResponse tokenValidated = redpayService.validateToken(validateTokenRequest);
} catch (Exception e) {
    System.out.println("Error al validar el token: " + e.getMessage());
}
```

### Validación de autorización

El método `validateAuthorization` permite validar el estado final de una autorización de transacción. Dependiendo de la propiedad `status_code` obtenida en la respuesta, se puede determinar si la transacción fue exitosa, fallida o se encuentra en proceso.

**Ejemplo de implementación: Validación de autorización**

```java
ValidateAuthorizationCollectorRequest validateAuthorizationCollectorRequest = new ValidateAuthorizationCollectorRequest();
validateAuthorizationCollectorRequest.setAuthorization_uuid("authorization-uuid");
validateAuthorizationCollectorRequest.setUser_id("demo");

RedPayService redpayService = new RedPayService();

try {
    ValidateAuthorizationResponse authorizationValidated = redpayService.validateAuthorization(validateAuthorizationCollectorRequest);
} catch (Exception e) {
    System.out.println("Error al validar la autorización: " + e.getMessage());
}
```

### Detalles de la devolución

Para realizar una devolución, se debe definir previamente la cuenta `chargeback` en la configuración inicial de la librería.

Adicionalmente, si desea operar con el modelo devolución automática, se debe definir el `secrets.chargeback_automatic` y la cuenta `account.chargeback_automatic` en la configuración inicial de la librería.

**Ejemplo de implementación: Devolución (opcional)**

```java
ChargebackRequest chargebackRequest = new ChargebackRequest();
chargebackRequest.setAmount(12345); // Monto que se desea devolver, puede ser parcial o total
chargebackRequest.setUser_id("demo");
chargebackRequest.setAuthorization_uuid("authorization-uuid");

RedPayService redpayService = new RedPayService();

try {
    ChargebackResponse chargebackResponse = redpayService.generateChargeback(chargebackRequest);
} catch (Exception e) {
    System.out.println("Error al realizar la devolución: " + e.getMessage());
}
```

# Enrolador pagador (Billetera digital)

## Configuración inicial

a configuración inicial de la librería es global y debe realizarse una única vez. Define los certificados, secretos y parámetros del entorno (producción o integración) necesarios para operar como pagador (billetera digital).

**Ejemplo de implementación: Configuración inicial**

```java
Certificate certificates = new Certificates("private.key", "certificate.crt");

Secrets secrets = new Secrets();
secrets.setIntegrity("integrity-secret");
secrets.setAuthorize("authorize-secret");

AccountEnrollerConfig authorize = new AccountEnrollerConfig();
authorize.setId("id_authorize");
authorize.setNumber(12345678);
authorize.setType(AccountAuthorization.CORRIENTE);
authorize.setBank(SbifCode.BANCO_BICE);

ConfigurationAccounts accounts = new ConfigurationAccounts();
accounts.setAuthorize(authorize);

RedPayConfig config = new RedPayConfig();
config.setCertificate(certificates);
config.setSecrets(secrets);
config.setEnvironment(RedPayEnvironment.Integration);
config.setType(Enroller.PAYER);
config.setAccounts(accounts);
```

## RedPayService

Este servicio ofrece las siguientes funcionalidades para las integraciones de tipo **Enrolador Pagador**:

### Métodos generales:

- `createUser`: Crear un usuario recolector.
- `updateUser` / `updateUserPartial`: Actualizar la información del usuario.
- `getUser` / `getUserOrFail`: Obtener datos del usuario.

### Métodos específicos:

- `validateToken`: Obtener detalles de un token.
- `authorizeToken`: Autorizar una transacción.
- `validateAuthorization`: Validar autorización de una trasacción.

### Gestión de usuarios

**Ejemplo de implementación: Generación de usuario (pagador)**

```java
// Cuenta de la institución financiera de origen (IFO) del enrolador pagador
UserAccount userAccount = new UserAccount();
userAccount.setNumber(22222222);
userAccount.setType(AccountUser.CUENTA_CORRIENTE);
userAccount.setBank(SbifCode.BANCO_BICE);
userAccount.setTax_id("59873050-4");

Geo geo = new Geo(); // Opcional
geo.setLat(-33.4489);
geo.setLng(-70.6693);

UserPayerRequest userPayerRequest = new UserPayerRequest();
userPayerRequest.setUser_id("demo");
userPayerRequest.setGeo(geo);
userPayerRequest.setTax_id("18204308-7");
userPayerRequest.setAccount(userAccount);
userPayerRequest.setName("Usuario pagador de prueba");
userPayerRequest.setEmail("example@example.com");

RedPayService redpayService = new RedPayService();

try {
    GenerateUserResponse userCreated = redpayService.createUser(userPayerRequest);
} catch (Exception e) {
    System.out.println("Error al crear el usuario: " + e.getMessage());
}
```

### Validación de token

El método `validateToken` permite obtener detalles de un token. Se utiliza para verificar la información de un token antes de realizar una operación de autorización.

```java
ValidateTokenRequest validateTokenRequest = new ValidateTokenRequest();
validateTokenRequest.setToken_uuid("token-uuid");
validateTokenRequest.setUser_id("demo");
validateTokenRequest.setUser_type(UserType.PAYER);

RedPayService redpayService = new RedPayService();

try {
    ValidateTokenResponse tokenValidated = redpayService.validateToken(validateTokenRequest);
} catch (Exception e) {
    System.out.println("Error al validar el token: " + e.getMessage());
}
```

#### Tipos de tokens

- T0: Token de transacción.
- T1: Token de suscripción.
- T2: Token de cobro de suscripción.
- T3: Token de envío de dinero.
- T4: Token de transacción con un alias.
- T6: Token de portal de recarga de devolución.

### Autorización de transacciones

El método `authorizeToken` permite autorizar una transacción utilizando un token previamente validado.

**Ejemplo de implementación: Autorización de transacciones**

```java
AuthorizeRequest authorizeRequest = new AuthorizeRequest();
authorizeRequest.setToken_uuid("token-uuid");
authorizeRequest.setUser_id("demo");
authorizeRequest.setAmount(12345); // Monto de la transacción del token a autorizar
authorizeRequest.setToken_type(TokenTypes.T0); // Tipo de token a autorizar
authorizeRequest.setValidation_uuid("validation-uuid"); // UUID de validación del token (operation_uuid)

RedPayService redpayService = new RedPayService();

try {
    AuthorizeResponse authorizeResponse = redpayService.authorizeToken(authorizeRequest);
} catch (Exception e) {
    System.out.println("Error al autorizar el token: " + e.getMessage());
}
```

### Validación de autorización (opcional)

El método `validateAuthorization` permite validar el estado final de una autorización de transacción. Este método es opcional y se puede utilizar cuando se recibe una autorización fallida (por ejemplo, cuando tienes como respuesta un `TIMEOUT` en la autorización).

Para utilizar este método, debe definir uno o ambos de los siguientes campos: `authorization_uuid` o `validation_uuid`.

**Ejemplo de implementación: Validación de autorización**

```java
ValidateAuthorizationPayerRequest validateAuthorizationPayerRequest = new ValidateAuthorizationPayerRequest();
validateAuthorizationPayerRequest.setAuthorization_uuid("authorization-uuid");
validateAuthorizationPayerRequest.setValidation_uuid("validation-uuid");
validateAuthorizationPayerRequest.setUser_id("demo");

RedPayService redpayService = new RedPayService();

try {
    ValidateAuthorizationResponse authorizationValidated = redpayService.validateAuthorization(validateAuthorizationPayerRequest);
} catch (Exception e) {
    System.out.println("Error al validar la autorización: " + e.getMessage());
}
```

# Enrolador dual (Recaudador y Pagador)

El **Enrolador Dual** combina las funcionalidades del Enrolador Recaudador y el Enrolador Pagador, permitiendo gestionar tanto la recolección como el pago de fondos en una misma integración.

## Requisitos para implementar un Enrolador Dual

Un Enrolador Dual debe implementar las capacidades de ambos roles:

1. **Funcionalidades de enrolador recaudador:**

- Gestión de usuarios recolectores, incluyendo creación, actualización y verificación.
- Generación, validación (opcional) y revocación de tokens asociados a la recolección de fondos
- Manejo de devoluciones mediante el método `generateChargeback` (opcional).

2. **Funcionalidades de enrolador pagador:**

- Gestión de usuarios pagadores, incluyendo creación, actualización y verificación.
- Validación y autorización de tokens para el pago de transacciones
- Manejo de devoluciones

# Control de integridad

Además de las funcionalidades de los servicios de RedPay, la librería proporciona un servicio para la generación y validación de firmas en los objetos de las transacciones.

## Servicio de integridad

El `RedPayIntegrityService` incluye los siguientes métodos principales:

## Métodos disponibles:

1. `generateSignature(Object payload, String secretKey)`: Genera una firma digital única para un objeto utilizando HMAC SHA256.

**Ejemplo**

```java
RedPayIntegrityService integrityService = new RedPayIntegrityService();
String signature = integrityService.generateSignature(payload, secretKey);
```

2. `getSignedObject(Map<String, Object> payload, String secretKey)`: Genera un objeto payload complementado con su firma.

**Ejemplo**

```java
RedPayIntegrityService integrityService = new RedPayIntegrityService();
Map<String, Object> signedPayload = integrityService.getSignedObject(payload, secretKey);
```

# Colaboración
¡Gracias por tu interés en contribuir al desarrollo de RedPay SDK JAVA! Valoramos enormemente todas las aportaciones constructivas que puedan ayudarnos a mejorar esta herramienta. Hay muchas formas en las que puedes colaborar, como:

- **Reportar errores**: 
Si encuentras un problema o algo no funciona como esperabas, no dudes en reportarlo.
- **Aportar código**: 
Ya sea para corregir errores, implementar nuevas funcionalidades o mejorar las existentes.
- **Mejorar la documentación**: 
Correcciones, aclaraciones o nuevas secciones siempre son bienvenidas.
- **Crear pruebas adicionales**: Ayúdanos a mejorar la cobertura y confiabilidad de nuestras pruebas.
- **Revisar y triage**: Analiza solicitudes de cambios y problemas abiertos para priorizar su atención.

## Reporte de problemas de seguridad
Si descubres una vulnerabilidad de seguridad en RedPay SDK JAVA, por favor comunícate con `soporteqri@junngla.com` para conocer los pasos a seguir y cómo informarnos de manera responsable.


# Documentación

Visita nuestra documentación oficial: https://developers.redpay.cl/site/documentation/context

# API

Visita nuestra API oficial: https://developers.redpay.cl/site/reference-api/redpay/api-qri-v2