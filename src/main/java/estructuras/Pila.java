package estructuras;

/**
 * Implementación genérica de una pila (LIFO - Last In, First Out).
 * 
 * Utiliza nodos simples enlazados (NodoSimple) para almacenar los elementos.
 * Permite operaciones básicas: push, pop, peek y ver si está vacía.
 * 
 * @param <T> Tipo de datos que almacena la pila.
 */
public class Pila<T> {

    private NodoSimple<T> cima; // Nodo superior de la pila
    private int size;           // Número de elementos en la pila

    /**
     * Constructor que inicializa la pila vacía.
     */
    public Pila() {
        cima = null;
        size = 0;
    }

    /**
     * Inserta un elemento en la cima de la pila.
     * @param dato Elemento a agregar.
     */
    public void push(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);
        nuevo.setSiguiente(cima); // Apunta al nodo que estaba en la cima
        cima = nuevo;             // Actualiza la cima al nuevo nodo
        size++;
    }

    /**
     * Elimina y devuelve el elemento de la cima de la pila.
     * @return Elemento eliminado, o null si la pila está vacía.
     */
    public T pop() {
        if (cima == null) {
            return null;
        }

        T dato = cima.getDato();
        cima = cima.getSiguiente(); // Avanza la cima al siguiente nodo
        size--;
        return dato;
    }

    /**
     * Verifica si la pila está vacía.
     * @return true si no hay elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return size == 0;
    }

    /**
     * Devuelve el elemento de la cima sin eliminarlo.
     * @return Elemento en la cima, o null si la pila está vacía.
     */
    public T peek() {
        if (cima == null) {
            return null;
        }
        return cima.getDato();
    }

    /**
     * Devuelve el número de elementos en la pila.
     * @return Tamaño de la pila.
     */
    public int size() {
        return size;
    }
}