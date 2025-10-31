package com.conversor.principal;

import com.conversor.calculos.ConversorDeMonedas;
import com.conversor.modelos.TasasConversion;

import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner lectura = new Scanner(System.in);
        ConversorDeMonedas consulta = new ConversorDeMonedas();

        Map<String, Double> mapDeTasas;

        try {
            // LLAMA A LA API UNA SOLA VEZ
            TasasConversion tasas = consulta.obtenerTodasLasTasas();
            mapDeTasas = tasas.conversion_rates();
            System.out.println("Tasas de cambio cargadas exitosamente.");
        } catch (RuntimeException e) {
            // SI LA API FALLA, LA APP FINALIZA
            System.out.println("Erroe. " + e.getMessage());
            System.out.println("Finalizando la aplicación...");
            lectura.close();
            return;
        }

        int opcion = -1;
        while (opcion != 7) {
            exibirMenu();
            try {
                opcion = Integer.valueOf(lectura.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido (1-7).");
                continue; // VUELVE AL INICIO DEL LOOP
            }

            if (opcion > 0 && opcion < 7) {

                // --- 1. PEDIR EL MONTO ---
                System.out.println("Ingrese el valor que desear convertir: ");
                double monto;
                try {
                    monto = Double.valueOf(lectura.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Monto no válido. Intente de nuevo.");
                    continue; // VUELVE AL INICIO DEL LOOP
                }

                // --- 2. DEFINIR MONEDAS SEGÚN OPCIÓN ---
                String monedaOrigen = "";
                String monedaDestino = "";

                switch (opcion) {
                    case 1:
                        monedaOrigen = "USD";
                        monedaDestino = "ARS";
                        break;
                    case 2:
                        monedaOrigen = "ARS";
                        monedaDestino = "USD";
                        break;
                    case 3:
                        monedaOrigen = "USD";
                        monedaDestino = "BRL";
                        break;
                    case 4:
                        monedaOrigen = "BRL";
                        monedaDestino = "USD";
                        break;
                    case 5:
                        monedaOrigen = "USD";
                        monedaDestino = "COP";
                        break;
                    case 6:
                        monedaOrigen = "COP";
                        monedaDestino = "USD";
                        break;
                }

                // --- 3. CALCULAR CONVERSIÓN LOCALMENTE ---

                // OBTIENE LAS TASAS DESDE EL MAP LOCAL
                double tasaOrigen = mapDeTasas.get(monedaOrigen);
                double tasaDestino = mapDeTasas.get(monedaDestino);

                // FÓRMULA: CONVERTIR EL MONTO A LA MONEDA BASE (USD) Y LUEGO A LA MONEDA DESTINO
                double resultado = (monto / tasaOrigen)  * tasaDestino;

                // --- 4. MOSTRAR RESULTADO
                System.out.println("El valor " + monto +
                        " [" + monedaOrigen + "] corresponde al valor final de =>>> " +
                        String.format("%.2f", resultado) + " [" + monedaDestino + "]\n");

            } else if (opcion != 7) {
                System.out.println("Opción no válida. Elija entre 1 y 7.");
            }
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
                7) Salir
                Elija una opción válida:
                *******************************************************
                """);
    }

}
