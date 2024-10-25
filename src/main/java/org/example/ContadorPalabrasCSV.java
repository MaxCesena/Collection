package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class ContadorPalabrasCSV {

    private final Map<String, Integer> frecuenciasPalabras = new HashMap<>();

    public ContadorPalabrasCSV() {
    }

    // Método para leer el contenido de un archivo y contar palabras
    public void procesarArchivo(String rutaArchivo) throws IOException {
        String contenidoArchivo = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        Pattern patronPalabra = Pattern.compile("\\b\\w+\\b");
        Matcher matcherPalabra = patronPalabra.matcher(contenidoArchivo.toLowerCase());

        while (matcherPalabra.find()) {
            String palabraEncontrada = matcherPalabra.group();
            frecuenciasPalabras.put(palabraEncontrada, frecuenciasPalabras.getOrDefault(palabraEncontrada, 0) + 1);
        }
    }

    // Método para obtener la cantidad de veces que aparece una palabra específica
    public int obtenerFrecuencia(String palabra) {
        return frecuenciasPalabras.getOrDefault(palabra.toLowerCase(), 0);
    }

    // Método para recuperar el mapa de frecuencias
    public Map<String, Integer> obtenerMapaFrecuencias() {
        return frecuenciasPalabras;
    }

    // Método para guardar las palabras y sus frecuencias en un archivo CSV
    public void escribirFrecuenciasCSV(String rutaArchivo) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            escritor.write("palabra,frecuencia\n");
            for (Map.Entry<String, Integer> entrada : frecuenciasPalabras.entrySet()) {
                escritor.write(entrada.getKey() + "," + entrada.getValue() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scannerEntrada = new Scanner(System.in);
        ContadorPalabrasCSV contador;
        contador = new ContadorPalabrasCSV();

        try {
            System.out.print("Por favor, ingrese la ruta del archivo de texto: ");
            String rutaArchivoTexto = scannerEntrada.nextLine();
            contador.procesarArchivo(rutaArchivoTexto);

            System.out.print("Ingrese la palbra que desea ver, o si quiere todas, escriba 'todo': ");
            String palabraConsulta = scannerEntrada.nextLine();

            if (palabraConsulta.equalsIgnoreCase("todo")) {
                System.out.println("Frecuencias de palabras encontradas:");
                for (Map.Entry<String, Integer> entrada : contador.obtenerMapaFrecuencias().entrySet()) {
                    System.out.println(entrada.getKey() + ": " + entrada.getValue());
                }
            } else {
                int frecuenciaPalabra = contador.obtenerFrecuencia(palabraConsulta);
                System.out.println("La palabra '" + palabraConsulta + "' aparece " + frecuenciaPalabra + " veces.");
            }

            System.out.print("Indique la ruta para guardar el archivo CSV: ");
            String rutaArchivoCSV = scannerEntrada.nextLine();
            contador.escribirFrecuenciasCSV(rutaArchivoCSV);
            System.out.println("Las frecuencias se han guardado en " + rutaArchivoCSV);

        } catch (IOException e) {
            System.out.println("Se produjo un error al leer o escribir el archivo: " + e.getMessage());
        } finally {
            scannerEntrada.close();
        }
    }
}
