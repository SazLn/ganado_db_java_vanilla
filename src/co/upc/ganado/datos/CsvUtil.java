package co.upc.ganado.datos;

import java.io.BufferedReader;   // Lee texto de un archivo de forma eficiente (buffer en memoria). Un buffer es un bloque de memoria temporal que se utiliza como intermediario entre un programa y un origen o destino de datos (como un archivo o la red).
import java.io.BufferedWriter;   // Escribe texto a un archivo de forma eficiente
import java.io.File;             // Representa rutas de archivos/directorios; usado para crear directorio data/
import java.io.FileReader;       // Conecta con el archivo físico para lectura de caracteres
import java.io.FileWriter;       // Conecta con el archivo físico para escritura de caracteres
import java.io.IOException;      // Excepción principal de operaciones de entrada/salida
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;      // Implementación de Lista para ir agregando las filas parseadas
import java.util.List;           // Interface para retornar la lista de líneas del CSV

/**
 * Clase utilitaria (sin estado) para operaciones genéricas de lectura y
 * escritura de archivos CSV. Todos sus métodos son estáticos para ser usados
 * directamente por las clases XxxData sin necesidad de instanciarla.
 *
 * Es utilitaria porque: - No tiene atributos de instancia (sin estado). - Solo
 * expone métodos estáticos de propósito general. - Sigue el patrón de clase
 * helper (como Math, Arrays, Collections).
 *
 * @author Santiago Rafael Zuleta Neira
 * @version 1.0
 * @since 2026-05-16
 */
public class CsvUtil {

    //MÉTODOS
    /**
     * Lee todo un archivo CSV y devuelve todas sus líneas parseadas. Abre el
     * archivo, lo lee línea por línea con un BufferedReader, llama a
     * parsearLinea() por cada línea, agrega cada String[] a una List<String[]>,
     * y la retorna. Si el archivo no existe, retorna lista vacía.
     *
     * @param rutaArchivo Ruta del archivo CSV a leer
     * @return Lista de arreglos, cada arreglo representa una línea del CSV
     */
    public static List<String[]> leerCsv(String rutaArchivo) {
        //Lista de String[], lista de arreglos.
        List<String[]> lineasParseadas = new ArrayList<>();

        String linea; //Se asinga una línea del archivo a esta variable para posteriormente ser parseada.

        //try-with-resources para que se cierre el BufferedReader automáticamente al finalizar el bloque try-catch.
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo, StandardCharsets.UTF_8))) {
            //Se establece el flujo de entrada del archivo con 'FileReader' anidado en un 'BufferedReader' establece el formato UTF-8.

            //Leer línea por línea.
            while ((linea = lector.readLine()) != null) {
                //Añade la línea parseada a la lista.
                lineasParseadas.add(parsearLinea(linea));
            }

            //Comentario temporal: Testear qué pasa cuando el archivo no existe.
        } catch (IOException e) {
            System.out.println("[CsvUtil] Error al leer archivo: " + e.getMessage());
        }

        return lineasParseadas;
    }

    /**
     * Escribe una lista completa de arreglos a un archivo CSV. Cada String[]
     * se convierte en una línea del archivo. Si el archivo ya existe, lo
     * sobrescribe.
     *
     * @param rutaArchivo Ruta del archivo CSV donde escribir
     * @param lineas Lista de arreglos donde cada arreglo es una línea del CSV
     */
    public static void escribirCsv(String rutaArchivo, List<String[]> lineas) {

        //Validar que el directorio 'data/' exista.
        if (!new File("data").exists()) {
            System.out.println("El directorio no existe.");

            new File("data").mkdirs(); //'true' si crea el directorio, 'false' si no.
            System.out.println("Directorio creado exitosamente.");
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo, StandardCharsets.UTF_8))) {
            //Recorrer lista de líneas parseadas.
            for (String[] linea : lineas) {
                
                //Escribir la línea parseada al archivo.
                escritor.write(formatearLinea(linea));
                escritor.newLine(); //Salto de línea luego de cada fila.
            }
        } catch (IOException e) {
            System.out.println("[CsvUtil] Error al escribir archivo: " + e.getMessage());
        }
    }

    /**
     * Convierte una línea de texto en un arreglo de varios datos de tipo
     * String. Por ejemplo, toma una línea de texto como "1~Finca La
     * Pradera~Valledupar" y la parte en partes individuales → ["1", "Finca La
     * Pradera", "Valledupar"]. Es decir, convertir una cadena CSV en un arreglo
     * de campos.
     *
     * @param lineaCsv
     * @return Linea CSV parseada como arreglo de campos.
     */
    public static String[] parsearLinea(String lineaCsv) {
        //Validar que la líne de texto no esté vacía
        if (lineaCsv == null || lineaCsv.isEmpty()) {
            return new String[0]; //Si está vacía devuelve un arreglo vacío.
        }
        //Cada que encuentre una "~" parte el String como se explicó líneas arriba.
        return lineaCsv.split("~", -1); //-1 garantiza que se incluyan las cadenas vacía.
    }

    /**
     * Inverso de parsearLinea. Toma un arreglo de campos y los une en una línea
     * CSV. Ej: ["1", "Finca La Pradera", "Valledupar"] → "1~Finca La
     * Pradera~Valledupar".
     *
     * @param campos Arreglo con los campos a unir.
     * @return Línea CSV formateada como String.
     */
    public static String formatearLinea(String[] campos) {
        if (campos == null || campos.length == 0) {
            return "";
        }

        /* Parámetros de 'String.join()': (delimitador, elementos)
            -El delimitador es un String que se pone entre cada elemento o campo, en este caso una virgulilla "~".
            -los elemntos son la colección o arreglo de campos, en este caso el String[] campos.
         */
        return String.join("~", campos); //'String.join()' une todos los campos
    }

}
