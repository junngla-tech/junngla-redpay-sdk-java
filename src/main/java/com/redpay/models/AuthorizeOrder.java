package com.redpay.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa una orden de autorización en el sistema RedPay.
 * <p>
 * Esta clase contiene la información necesaria para identificar y procesar
 * una autorización, incluyendo el identificador de la autorización, el token asociado,
 * el usuario y el estado de confirmación.
 * </p>
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class AuthorizeOrder {

  /**
   * Identificador único de la autorización.
   */
  @NonNull 
  @JsonProperty
  String authorization_uuid;

  /**
   * Identificador único del token asociado a la orden.
   */
  @NonNull
  @JsonProperty
  String token_uuid;

  /**
   * Identificador del usuario asociado a la orden.
   */
  @NonNull
  @JsonProperty
  String user_id;
  
  /**
   * Indica si la orden de autorización ha sido confirmada.
   * Por defecto es false.
   */
  @JsonProperty
  Boolean is_confirmed = false;

  /**
   * Código de estado de respuesta tras el procesamiento de la autorización.
   */
  @JsonProperty
  String status_code;
}
