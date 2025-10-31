package com.conversor.principal;

import com.conversor.calculos.ConversorDeMonedas;
import com.conversor.calculos.GeneradorDeArchivo;
import com.conversor.modelos.RegistroConversion;
import com.conversor.modelos.TasasConversion;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner lectura = new Scanner(System.in);
        ConversorDeMonedas consulta = new ConversorDeMonedas();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        GeneradorDeArchivo generador = new GeneradorDeArchivo();
        List<RegistroConversion> historial;

        Map<String, Double> mapDeTasas;

        // --- CARGAR TODAS LAS TASAS ---
        try {
            // LLAMA A LA API UNA SOLA VEZ
            TasasConversion tasas = consulta.obtenerTodasLasTasas();
            mapDeTasas = tasas.conversion_rates();
            System.out.println("Tasas de cambio cargadas exitosamente.");
        } catch (RuntimeException e) {
            // SI LA API FALLA, LA APP FINALIZA
            System.out.println("Error. " + e.getMessage());
            System.out.println("Finalizando la aplicación...");
            lectura.close();
            return;
        }

        // --- CARGAR HISTORIAL DESDE JSON ---
        try {
            historial = generador.cargarHistorial();
            System.out.println("Historial cargado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al cargar el historial. " + e.getMessage());
            // EMPIEZA HISTORIAL VACÍO SI FALLA
            historial = new ArrayList<>();
        }

        int opcion = -1;
        while (opcion != 9) {
            exibirMenu();
            try {
                opcion = Integer.valueOf(lectura.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido (1-9).");
                continue; // VUELVE AL INICIO DEL LOOP
            }

            // --- DEFINIR MONEDAS SEGÚN OPCIÓN ---
            String monedaOrigen = "";
            String monedaDestino = "";
            // FLAG PARA CONTROLAS SI SE PIDE ALGÚN TIPO DE CONVERSIÓN
            boolean esConversionValida = false;

            if (opcion > 0 && opcion < 7) {
                // OPCIONES DEFINIDAS 1-6
                switch (opcion) {
                    case 1: monedaOrigen = "USD"; monedaDestino = "ARS"; break;
                    case 2: monedaOrigen = "ARS"; monedaDestino = "USD"; break;
                    case 3: monedaOrigen = "USD"; monedaDestino = "BRL"; break;
                    case 4: monedaOrigen = "BRL"; monedaDestino = "USD"; break;
                    case 5: monedaOrigen = "USD"; monedaDestino = "COP"; break;
                    case 6: monedaOrigen = "COP"; monedaDestino = "USD"; break;
                }
                esConversionValida = true;

            } else if (opcion == 7) {
                // CONVERSIÓN PERSONALIZADA
                System.out.println("Ingrese el código de la moneda de ORIGEN (ej: EUR, JPY, USD):");
                monedaOrigen = lectura.nextLine().toUpperCase();
                System.out.println("Ingrese el código de la moneda de DESTINO (ej: GBP, CHF, ARS):");
                monedaDestino = lectura.nextLine().toUpperCase();

                // COMPRUEBA QUE LAS MONEDAS EXISTAN EN EL MAP
                if (mapDeTasas.containsKey(monedaOrigen) && mapDeTasas.containsKey(monedaDestino)) {
                    esConversionValida = true;
                } else {
                    System.out.println("Error. Uno o ambos códigos de moneda no son válidos.");
                    System.out.println("Puede ver los códigos soportados en: https://www.exchangerate-api.com/docs/supported-currencies\n");
                }

            } else if (opcion == 8) {

                // --- MOSTRAR HISTORIAL ---
                System.out.println("\n**************** Historial de Conversiones ****************");
                if (historial.isEmpty()) {
                    System.out.println("Aún no se han realizado conversiones.\n");
                } else {
                    historial.forEach(registro -> System.out.println(
                            "[ " + registro.timestamp() + " ] " +
                            "Convirtió: " + registro.montoConvertir() + " [" + registro.monedaOrigen() + "]" +
                                    " -> " + String.format("%.2f", registro.montoRecibido()) + " [" + registro.monedaDestino() + "]"
                    ));
                    System.out.println("*******************************************************\n");
                }
            } else if (opcion != 9) {
                System.out.println("Opción no válida. Elija entre 1 y 8.");
            }

            // ESTE BLOQUE SE EJECUTA SI EL USUARIO ELIGIÓ OPCIÓN 1-6,
            // O SI FUE LA 7 Y PASÓ LA VALIDACIÓN DE LAS MONEDAS
            if (esConversionValida) {

                // --- PEDIR EL MONTO ---
                System.out.println("Ingrese el valor que desear convertir: ");
                double monto;
                try {
                    monto = Double.valueOf(lectura.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Monto no válido. Intente de nuevo.");
                    continue; // VUELVE AL INICIO DEL LOOP
                }

                // --- CALCULAR CONVERSIÓN LOCALMENTE ---
                // OBTIENE LAS TASAS DESDE EL MAP LOCAL
                double tasaOrigen = mapDeTasas.get(monedaOrigen);
                double tasaDestino = mapDeTasas.get(monedaDestino);
                // FÓRMULA: CONVERTIR EL MONTO A LA MONEDA BASE (USD) Y LUEGO A LA MONEDA DESTINO
                double resultado = (monto / tasaOrigen)  * tasaDestino;

                // --- MOSTRAR RESULTADO ---
                System.out.println("El valor " + monto +
                        " [" + monedaOrigen + "] corresponde al valor final de =>>> " +
                        String.format("%.2f", resultado) + " [" + monedaDestino + "]\n");

                // --- AÑADIR HISTORIAL (EN MEMORIA) ---
                String timestamp = LocalDateTime.now().format(formatter);
                RegistroConversion registro = new RegistroConversion(timestamp, monedaOrigen, monedaDestino, monto, resultado);
                historial.add(registro);

            }
        }

        // --- GUARDAR HISTORIAL EN JSON ---
        try {
            generador.guardarHistorial(historial);
            System.out.println("Historial guardado exitosamente en " + generador.get_NOMBRE_ARCHIVO());
        } catch (IOException e) {
            System.out.println("Error al guardar el historial. " + e.getMessage());
        }

        System.out.println("Finalizando la aplicación... ¡Gracias por usar el Conversor de Moneda!");
        lectura.close();

    }

    public static void exibirMenu() {
        System.out.println("""
                *******************************************************
                Sea bienvenid@ al Conversor de Moneda =]
                
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real brasileño =>> Dólar
                5) Dólar ==> Peso colombiano
                6) Peso colombiano ==> Dólar
                7) Convertir otra moneda (Personalizado)
                8) Ver historial de conversiones
                9) Salir
                Elija una opción válida:
                *******************************************************
                """);
    }

}
