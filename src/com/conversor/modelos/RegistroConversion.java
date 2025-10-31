package com.conversor.modelos;

/**
 *  Se utiliza el record para almacenar la información a guardar en el historial en el JSON.
 */
public record RegistroConversion(String monedaOrigen,
                                 String monedaDestino,
                                 double montoConvertir,
                                 double montoRecibido) {
}
