package estructuras;

import javax.swing.table.DefaultTableModel;
import modelos.Estudiante;

/**
 * Implementación genérica de una lista doblemente enlazada circular.
 * 
 * Características:
 * - Cada nodo apunta al siguiente y al anterior.
 * - El último nodo apunta al primero y viceversa, formando un ciclo.
 * - Permite agregar elementos al final y recorrer la lista desde la cabeza.
 * 
 * @param <T> Tipo de dato que almacena la lista.
 */
public class ListaDobleCircular<T> {

    /**
     * Clase interna Nodo que representa un elemento de la lista.
     */
    public class Nodo {

        private T dato;        // Dato que almacena el nodo
        private Nodo siguiente; // Referencia al siguiente nodo
        private Nodo anterior;  // Referencia al nodo anterior

        Nodo(T dato) {
            this.dato = dato;
        }

        public T getDato() {
            return dato;
        }

        public Nodo getSiguiente() {
            return siguiente;
        }

        public Nodo getAnterior() {
            return anterior;
        }
    }

    private Nodo cabeza; // Nodo inicial de la lista
    private int size;    // Número de elementos en la lista

    /**
     * Constructor que inicializa la lista vacía.
     */
    public ListaDobleCircular() {
        cabeza = null;
        size = 0;
    }

    /**
     * Agrega un elemento al final de la lista.
     * @param dato Elemento a agregar.
     */
    public void agregar(T dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            // Lista vacía: nuevo nodo apunta a sí mismo
            cabeza = nuevo;
            cabeza.siguiente = cabeza;
            cabeza.anterior = cabeza;
        } else {
            Nodo cola = cabeza.anterior; // Último nodo
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
        }
        size++;
    }

    /**
     * Muestra los primeros N elementos en consola.
     * @param n Número de elementos a mostrar.
     */
    public void mostrarN(int n) {
        if (cabeza == null) {
            return;
        }
        Nodo actual = cabeza;
        int contador = 0;
        do {
            System.out.println(actual.dato);
            actual = actual.siguiente;
            contador++;
        } while (contador < n && actual != cabeza);
    }

    /**
     * Muestra los primeros N elementos en un JTable.
     * Solo funciona si T es tipo Estudiante.
     * @param modelo Modelo de la tabla donde se agregan los datos.
     * @param n Número de elementos a mostrar.
     */
    public void mostrarNEnTabla(DefaultTableModel modelo, int n) {
        if (cabeza == null) {
            return;
        }
        Nodo actual = cabeza;
        int contador = 0;
        do {
            T dato = actual.dato;
            if (dato instanceof Estudiante) {
                Estudiante e = (Estudiante) dato;
                modelo.addRow(new Object[]{e.getMatricula(), e.getNombre()});
            }
            actual = actual.siguiente;
            contador++;
        } while (actual != cabeza && contador < n);
    }

    /**
     * Devuelve el nodo cabeza de la lista.
     * @return Nodo cabeza.
     */
    public Nodo getCabeza() {
        return cabeza;
    }

    /**
     * Devuelve el número de elementos en la lista.
     * @return Tamaño de la lista.
     */
    public int size() {
        return size;
    }
}