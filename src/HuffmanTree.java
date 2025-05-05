import java.io.*;
import java.util.*;

/**
 * Clase que construye y maneja el árbol de Huffman.
 */
public class HuffmanTree {
    private HuffmanNode root;
    private Map<Character, String> huffmanCodes = new HashMap<>();

    /**
     * Construye el árbol de Huffman a partir de un mapa de frecuencias.
     */
    public void buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode parent = new HuffmanNode(left, right);
            queue.add(parent);
        }

        root = queue.poll(); // Nodo raíz final
        generateCodes(root, "");
    }

    /**
     * Genera los códigos binarios para cada carácter.
     */
    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
        } else {
            generateCodes(node.left, code + "0");
            generateCodes(node.right, code + "1");
        }
    }

    /**
     * Devuelve el mapa de códigos binarios para cada carácter.
     */
    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }

    /**
     * Devuelve la raíz del árbol (útil para serializar o descomprimir).
     */
    public HuffmanNode getRoot() {
        return root;
    }

    /**
     * Método para contar frecuencias desde un archivo.
     */
    public static Map<Character, Integer> buildFrequencyMap(String filePath) throws IOException {
        Map<Character, Integer> freqMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = br.read()) != -1) {
                char character = (char) ch;
                freqMap.put(character, freqMap.getOrDefault(character, 0) + 1);
            }
        }
        return freqMap;
    }

    public void compress(String inputFilePath, String outputFilePath) throws IOException {
        // Paso 1: Leer el archivo original
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        StringBuilder encodedText = new StringBuilder();
    
        int c;
        while ((c = reader.read()) != -1) {
            char ch = (char) c;
            String code = huffmanCodes.get(ch);
            if (code != null) {
                encodedText.append(code);
            }
        }
        reader.close();
    
        // Paso 2: Escribir el texto codificado en el archivo .huff
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        writer.write(encodedText.toString());
        writer.close();
    }

    public void decompress(String inputFilePath, String outputFilePath) throws IOException {
        // Leer el archivo comprimido
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        String encodedText = reader.readLine();
        reader.close();
    
        // Decodificar usando el árbol
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode current = root;
    
        for (int i = 0; i < encodedText.length(); i++) {
            char bit = encodedText.charAt(i);
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
    
            // Si llegamos a una hoja, agregamos el carácter
            if (current.left == null && current.right == null) {
                decodedText.append(current.character);
                current = root; // Reiniciamos desde la raíz
            }
        }
    
        // Escribir el texto decodificado
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
        writer.write(decodedText.toString());
        writer.close();
    }
    
    
}
