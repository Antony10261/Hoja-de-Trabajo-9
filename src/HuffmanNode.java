
/**
 * Clase que representa un nodo en el árbol de Huffman.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    public char character; // Solo para hojas
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;

    /**
     * Constructor para nodos hoja (con carácter).
     */
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    /**
     * Constructor para nodos internos (sin carácter).
     */
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.character = '\0'; // Valor nulo para nodos internos
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
    }

    /**
     * Comparador para usar en colas de prioridad (menor frecuencia primero).
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }

    /**
     * Verifica si el nodo es una hoja (no tiene hijos).
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}
