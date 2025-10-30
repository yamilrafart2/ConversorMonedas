package com.conversor.calculos;

import com.conversor.modelos.Conversion;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorDeMonedas {

    private final String _API_KEY = "9b585c4925d1dbd5d00cae8e";

    public Conversion convierteMonedas(String monedaOrigen, String monedaDestino) {

        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + _API_KEY + "/pair/" + monedaOrigen + "/" + monedaDestino);

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
            return new Gson().fromJson(response.body(), Conversion.class);
        } catch (Exception e) {
            throw new RuntimeException("No encontré esa moneda. " + e.getMessage());
        }

    }

}
