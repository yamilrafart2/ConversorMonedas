# 💰 Conversor de Moneda - Challenge ONE (G6)

## 📜 Descripción

Se trata de una aplicación de consola en Java que permite a los usuarios convertir valores entre diferentes divisas, utilizando una API de tasas de cambio en tiempo real.

## ✨ Características

* **Interfaz de Consola Interactiva:** Un menú simple y fácil de usar en la terminal.
* **Conversiones Predefinidas:** Incluye 6 pares de conversión populares (USD, ARS, BRL, COP).
* **Eficiencia de API (Caché):** Realiza una sola llamada a la API al inicio para obtener todas las tasas, haciendo las conversiones instantáneas.
* **Cálculo de Conversión Cruzada:** Capaz de calcular la conversión entre dos monedas (ej. ARS -> BRL) usando el Dólar (USD) como moneda base, gracias al mapa de tasas.
* **Manejo de Errores:** Incluye validación para entradas numéricas y maneja errores si la API no puede ser consultada al inicio.

## 🚀 Demostración de Uso

Así es como se ve la aplicación en funcionamiento:

```bash
Tasas de cambio cargadas exitosamente.
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
1
Ingrese el valor que desear convertir: 
150
*******************************************************
El valor 150.0 [USD] corresponde al valor final de =>> 138542.87 [ARS]
*******************************************************

*******************************************************
Sea bienvenid@ al Conversor de Moneda =]
...
7) Salir
Elija una opción válida:
*******************************************************
2
Ingrese el valor que desear convertir: 
100000
*******************************************************
El valor 100000.0 [ARS] corresponde al valor final de =>> 108.27 [USD]
*******************************************************

*******************************************************
...
7) Salir
Elija una opción válida:
*******************************************************
7
Finalizando la aplicación... ¡Gracias por usar el Conversor de Moneda!
```

## 🛠️ Tecnologías Utilizadas

* **Java (JDK 17+)**
* **Java `HttpClient`:** Para realizar peticiones HTTP modernas (Java 11+).
* **Gson:** Biblioteca de Google para analizar (parsear) las respuestas JSON de la API.
* **ExchangeRate-API:** El servicio web externo que provee las tasas de cambio en tiempo real.

## ⚙️ Configuración y Ejecución

1.  Clona este repositorio en tu máquina local.
2.  Abre el proyecto en tu IDE de Java preferido (IntelliJ, Eclipse, VSCode, etc.).
3.  **Importante:** Necesitas una clave (API Key) de [ExchangeRate-API](https://www.exchangerate-api.com/).
4.  Ve al archivo `src/com/conversormonedas/calculos/ConversorMonedas.java`.
5.  Reemplaza el valor de la variable `_API_KEY` con tu propia clave:
    ```java
    private final String _API_KEY = "TU_API_KEY_VA_AQUÍ";
    ```
6.  Asegúrate de tener la biblioteca **Gson** añadida a las dependencias de tu proyecto (ya sea manualmente como .jar o vía Maven/Gradle).
7.  Ejecuta el método `main` en la clase `src/com/conversormonedas/principal/App.java`.

## 👨‍💻 Autor

* **Yamil Aruto Rafart**