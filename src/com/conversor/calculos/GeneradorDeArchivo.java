package com.conversor.calculos;

import com.conversor.modelos.RegistroConversion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneradorDeArchivo {

    private final String _NOMBRE_ARCHIVO = "historialDeConversiones.json";
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     *  Guarda la lisa completa del historial de conversiones en el archivo JSON.
     *  Sobreescribe el archivo anterior.
     */
    public void guardarHistorial(List<RegistroConversion> historial) throws IOException {
        try (FileWriter escritura = new FileWriter(_NOMBRE_ARCHIVO)) {
            gson.toJson(historial, escritura);
        }
    }

    /**
     *  Carga la lista de historial desde el archivo JSON.
     *  Si el archivo no existe, devuelve una lista vacía.
     */
    public List<RegistroConversion> cargarHistorial() {
        // DEFINE EL TIPO DE DATO QUE SE ESPERA LEER (UNA LISTA DE RegistroConversion)
        Type tipoLista = new TypeToken<ArrayList<RegistroConversion>>(){}.getType();

        try (FileReader lector = new FileReader(_NOMBRE_ARCHIVO)) {
            List<RegistroConversion> historial = gson.fromJson(lector, tipoLista);

            // SI EL ARCHIVO ESTÁ VACÍO O MAL FORMADO, GSON PUEDE DEVOLVER NULL
            return (historial != null) ? historial : new ArrayList<>();
        } catch (IOException e) {
            // SI EL ARCHIVO NO EXISTE, ES LA PRIMERA VEZ QUE SE CORRE
            // DEVUELVE UNA LISTA VACÍA
            return new ArrayList<>();
        }
    }

    public String get_NOMBRE_ARCHIVO() {
        return _NOMBRE_ARCHIVO;
    }
}
