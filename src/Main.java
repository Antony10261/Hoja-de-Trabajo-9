import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Ruta al archivo de prueba
            String filePath = "testfiles/archivo1.txt";

            // Paso 1: Crear el mapa de frecuencias
            Map<Character, Integer> freqMap = HuffmanTree.buildFrequencyMap(filePath);

            System.out.println("FRECUENCIAS:");
            for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
                System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
            }

            // Paso 2: Construir el árbol de Huffman
            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(freqMap);

            // Paso 3: Obtener y mostrar los códigos de Huffman
            Map<Character, String> codes = tree.getHuffmanCodes();

            System.out.println("\nCÓDIGOS DE HUFFMAN:");
            for (Map.Entry<Character, String> entry : codes.entrySet()) {
                System.out.println("'" + entry.getKey() + "' : " + entry.getValue());
            }

            // Paso 4: Comprimir el archivo
            String outputFilePath = "testfiles/archivo1.huff";
            tree.compress(filePath, outputFilePath);
            System.out.println("\nArchivo comprimido guardado en: " + outputFilePath);
            
            // Paso 5: Descomprimir el archivo
            String decodedFilePath = "testfiles/archivo1.decoded.txt";
            tree.decompress(outputFilePath, decodedFilePath);
            System.out.println("\nArchivo decodificado guardado en: " + decodedFilePath);


        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
