package com.redpay.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Serializador personalizado que convierte un objeto {@code Integer} a su representación en {@code String} en JSON.
 * <p>
 * Este serializador asegura que los valores de tipo {@code Integer} se serialicen como cadenas en la salida JSON.
 * </p>
 */
public class IntToStringSerializer extends StdSerializer<Integer> {

    /**
     * Constructor por defecto que especifica el tipo de dato manejado ({@code Integer}).
     */
    public IntToStringSerializer() {
        super(Integer.class);
    }

    /**
     * Serializa el valor {@code Integer} proporcionado, convirtiéndolo a {@code String}.
     *
     * @param value    El valor {@code Integer} a serializar.
     * @param gen      El {@link JsonGenerator} usado para escribir el contenido JSON.
     * @param provider El {@link SerializerProvider} que puede proporcionar otros serializadores necesarios.
     * @throws IOException Si ocurre algún error de entrada/salida durante la serialización.
     */
    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toString());
    }
}
