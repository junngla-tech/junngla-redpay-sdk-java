package com.redpay.models;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializador personalizado que convierte un valor JSON de tipo String a un Integer.
 * <p>
 * Se utiliza cuando los números en el JSON están representados como cadenas y se
 * requiere convertirlos a enteros durante la deserialización.
 * </p>
 */
public class StringToIntDeserializer extends StdDeserializer<Integer> {

    /**
     * Constructor por defecto que indica que el tipo objetivo de la deserialización es Integer.
     */
    public StringToIntDeserializer() {
        super(Integer.class);
    }

    /**
     * Deserializa un valor JSON representado como String a un objeto Integer.
     *
     * @param p     El JsonParser que proporciona el contenido JSON.
     * @param ctxt  El contexto de deserialización.
     * @return      El valor convertido a Integer.
     * @throws IOException Si ocurre un error durante la lectura o conversión del valor.
     */
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        return Integer.valueOf(text);
    }
}
