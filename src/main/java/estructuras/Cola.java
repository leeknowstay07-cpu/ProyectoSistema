package estructuras;

/**
 * Implementación genérica de una cola (FIFO - First In, First Out).
 * 
 * Utiliza nodos simples enlazados (NodoSimple) para almacenar los elementos.
 * Permite operaciones básicas: enqueue, dequeue y ver si está vacía.
 * 
 * @param <T> Tipo de datos que almacena la cola.
 */
public class Cola<T> {

    private NodoSimple<T> frente; // Nodo al frente de la cola (primer elemento)
    private NodoSimple<T> fin;    // Nodo al final de la cola (último elemento)
    private int size;             // Número de elementos en la cola

    /**
     * Constructor que inicializa una cola vacía.
     */
    public Cola() {
        frente = fin = null;
        size = 0;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param dato Elemento a agregar.
     */
    public void enqueue(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);
        if (fin == null) {
            // Cola vacía: el nuevo nodo es frente y fin
            frente = fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        size++;
    }

    /**
     * Elimina y devuelve el elemento del frente de la cola.
     * @return Elemento eliminado, o null si la cola está vacía.
     */
    public T dequeue() {
        if (frente == null) return null;

        T dato = frente.getDato();
        frente = frente.getSiguiente();

        if (frente == null) fin = null; // Si la cola quedó vacía, fin también es null
        size--;
        return dato;
    }

    /**
     * Verifica si la cola está vacía.
     * @return true si no hay elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return size == 0;
    }

    /**
     * Devuelve el número de elementos en la cola.
     * @return Tamaño de la cola.
     */
    public int size() {
        return size;
    }
}