package estructuras;

/**
 * Implementación genérica de una lista circular simple.
 * 
 * Características:
 * - Cada nodo apunta al siguiente formando un ciclo.
 * - Se mantiene un puntero "actual" que indica el nodo actual.
 * - Permite insertar elementos y rotar el puntero actual.
 *
 * @param <T> Tipo de dato que almacena la lista.
 */
public class ListaCircular<T> {

    private NodoSimple<T> actual; // Nodo actualmente seleccionado

    /**
     * Inserta un elemento en la lista circular.
     * Si la lista está vacía, el nodo se apunta a sí mismo.
     * @param dato Elemento a insertar.
     */
    public void insertar(T dato) {
        NodoSimple<T> n = new NodoSimple<>(dato);
        if (actual == null) {
            actual = n;
            n.setSiguiente(n); // Se apunta a sí mismo
        } else {
            n.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(n);
        }
    }

    /**
     * Rota el puntero "actual" al siguiente nodo.
     * @return Representación en cadena del dato del nodo actual tras la rotación.
     */
    public String rotar() {
        if (actual == null) return "Sin elementos";
        actual = actual.getSiguiente();
        return actual.getDato().toString();
    }
}