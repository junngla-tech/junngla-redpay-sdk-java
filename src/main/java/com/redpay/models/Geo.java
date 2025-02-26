package com.redpay.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa una ubicación geográfica mediante sus coordenadas.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@ToString
public class Geo {

    /**
     * Latitud de la ubicación.
     */
    private double lat;

    /**
     * Longitud de la ubicación.
     */
    private double lng;
}
