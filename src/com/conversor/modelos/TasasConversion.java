package com.conversor.modelos;

import java.util.Map;

/*
    Este record modela la respuesta del endpoint "/latest"
    que incluye un mapa (diccionario) de todas las tasas.
 */
public record TasasConversion(String result,
                              String base_code,
                              Map<String, Double> conversion_rates) {
}
