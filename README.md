# 💰 Conversor de Moneda

## 📜 Descripción

Se trata de una aplicación de consola en Java que permite a los usuarios convertir valores entre diferentes divisas, utilizando una API de tasas de cambio en tiempo real.

## ✨ Características

* **Interfaz de Consola Interactiva:** Un menú simple y fácil de usar en la terminal.
* **Conversiones Predefinidas:** Incluye 6 pares de conversión populares (USD, ARS, BRL, COP).
* **Conversión Personalizada:** Permite al usuario convertir entre *cualquier* par de monedas disponibles en la API (ej. "EUR" a "JPY").
* **Eficiencia de API (Caché):** Realiza una sola llamada a la API al inicio para obtener todas las tasas, haciendo las conversiones instantáneas.
* **Historial Persistente:** Guarda todas las conversiones en un archivo `historialDeConversiones.json` para que el historial persista entre sesiones.
* **Marca de Tiempo (Timestamp):** Cada registro en el historial incluye la fecha y hora exactas de la conversión.
* **Cálculo de Conversión Cruzada:** Capaz de calcular la conversión entre dos monedas (ej. ARS -> BRL) usando el Dólar (USD) como moneda base.
* **Manejo de Errores:** Incluye validación para entradas numéricas y maneja errores si la API no puede ser consultada al inicio.

## 🚀 Demostración de Uso

Así es como se ve la aplicación en funcionamiento con las nuevas características:

```bash
Tasas de cambio cargadas exitosamente.
Historial cargado exitosamente.
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
7
Ingrese el código de la moneda de ORIGEN (ej: EUR, JPY, USD):
EUR
Ingrese el código de la moneda de DESTINO (ej: GBP, CHF, ARS):
JPY
Ingrese el valor que desear convertir: 
100
El valor 100.0 [EUR] corresponde al valor final de =>>> 17058.42 [JPY]

*******************************************************
Sea bienvenid@ al Conversor de Moneda =]
...
9) Salir
Elija una opción válida:
*******************************************************
8

**************** Historial de Conversiones ****************
[ 31-10-2025 16:30:15 ] Convirtió: 150.0 [USD] -> 138542.87 [ARS]
[ 31-10-2025 16:30:45 ] Convirtió: 100000.0 [ARS] -> 108.27 [USD]
[ 31-10-2025 16:41:10 ] Convirtió: 100.0 [EUR] -> 17058.42 [JPY]
*******************************************************

*******************************************************
...
9) Salir
Elija una opción válida:
*******************************************************
9
Historial guardado exitosamente en historialDeConversiones.json
Finalizando la aplicación... ¡Gracias por usar el Conversor de Moneda!
```

## 🛠️ Tecnologías Utilizadas

* **Java (JDK 17+)**
* **Java `HttpClient`:** Para realizar peticiones HTTP modernas (Java 11+).
* **Java `java.time`:** Para la generación de marcas de tiempo (timestamps).
* **Gson:** Biblioteca de Google para analizar (parsear) las respuestas y archivos JSON.
* **ExchangeRate-API:** El servicio web externo que provee las tasas de cambio en tiempo real.

## ⚙️ Configuración y Ejecución

1.  Clona este repositorio en tu máquina local.
2.  Abre el proyecto en tu IDE de Java preferido (IntelliJ, Eclipse, VSCode, etc.).
3.  **Importante:** Necesitas una clave (API Key) de [ExchangeRate-API](https://www.exchangerate-api.com/).
4.  Ve al archivo `src/com/conversor/calculos/ConversorDeMonedas.java`.
5.  Reemplaza el valor de la variable `_API_KEY` con tu propia clave:
    ```java
    private final String _API_KEY = "TU_API_KEY_VA_AQUÍ";
    ```
6.  Asegúrate de tener la biblioteca **Gson** añadida a las dependencias de tu proyecto (ya sea manualmente como .jar o vía Maven/Gradle).
7.  Ejecuta el método `main` en la clase `src/com/conversor/principal/App.java`.

## 👨‍💻 Autor

* **Yamil Aruto Rafart**