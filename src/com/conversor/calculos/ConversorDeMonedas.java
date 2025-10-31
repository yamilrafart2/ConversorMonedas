package com.conversor.calculos;

import com.conversor.modelos.TasasConversion;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorDeMonedas {

    private final String _API_KEY = "9b585c4925d1dbd5d00cae8e";

    /**
     * Este método obtiene TODAS las tasas de conversión
     * usando USD como moneda base (endpoint /latest/USD).
     */
    public TasasConversion obtenerTodasLasTasas() {

        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + _API_KEY + "/latest/USD");

        // CREANDO CLIENTE HTTP
        HttpClient client = HttpClient.newHttpClient();

        // CREANDO SOLICITUD
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            // ENVIANDO SOLICITUD Y RECIBIENDO RESPUESTAS
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson(response.body(), TasasConversion.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar las tasas de cambio. " + e.getMessage());
        }
    }

}
