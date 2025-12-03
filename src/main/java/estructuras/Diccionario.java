package estructuras;

/**
 * Implementación genérica de un diccionario (tabla hash) usando
 * **hashing con encadenamiento** mediante listas simples.
 * 
 * Permite insertar, buscar, eliminar y listar valores.
 * 
 * @param <K> Tipo de clave.
 * @param <V> Tipo de valor.
 */
public class Diccionario<K, V> {

    private ListaSimple<EntradaHash<K, V>>[] tabla; // Tabla de listas encadenadas
    private int capacidad;                          // Número de "cubetas" de la tabla

    /**
     * Constructor por defecto, inicializa con capacidad 53 (número primo recomendable, reducen colisiones).
     */
    public Diccionario() {
        this(53);
    }

    /**
     * Constructor que permite definir la capacidad de la tabla.
     * @param capacidad Número de cubetas.
     */
    public Diccionario(int capacidad) {
        this.capacidad = capacidad;
        tabla = new ListaSimple[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaSimple<>();
        }
    }

    /**
     * Función hash que convierte la clave en un índice válido para la tabla.
     * @param clave Clave a transformar.
     * @return Índice de la tabla.
     */
    private int hash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    /**
     * Inserta un par clave-valor en el diccionario.
     * No permite claves duplicadas.
     * @param clave Clave del elemento.
     * @param valor Valor asociado a la clave.
     * @return true si se insertó correctamente, false si la clave ya existía.
     */
    public boolean insertar(K clave, V valor) {
        int idx = hash(clave);
        ListaSimple<EntradaHash<K, V>> lista = tabla[idx];

        // Verificar si la clave ya existe
        NodoSimple<EntradaHash<K, V>> nodo = lista.getInicio();
        while (nodo != null) {
            if (nodo.getDato().getClave().equals(clave)) {
                return false; // Clave duplicada
            }
            nodo = nodo.getSiguiente();
        }

        lista.agregar(new EntradaHash<>(clave, valor));
        return true;
    }

    /**
     * Busca un valor a partir de su clave.
     * @param clave Clave del elemento a buscar.
     * @return Valor asociado a la clave, o null si no se encuentra.
     */
    public V buscar(K clave) {
        int idx = hash(clave);
        ListaSimple<EntradaHash<K, V>> lista = tabla[idx];

        NodoSimple<EntradaHash<K, V>> nodo = lista.getInicio();
        while (nodo != null) {
            if (nodo.getDato().getClave().equals(clave)) {
                return nodo.getDato().getValor();
            }
            nodo = nodo.getSiguiente();
        }
        return null;
    }

    /**
     * Elimina un elemento del diccionario usando su clave.
     * @param clave Clave del elemento a eliminar.
     * @return true si se eliminó correctamente, false si no se encontró.
     */
    public boolean eliminar(K clave) {
        int idx = hash(clave);
        ListaSimple<EntradaHash<K, V>> lista = tabla[idx];

        NodoSimple<EntradaHash<K, V>> nodo = lista.getInicio();
        while (nodo != null) {
            if (nodo.getDato().getClave().equals(clave)) {
                return lista.eliminar(nodo.getDato());
            }
            nodo = nodo.getSiguiente();
        }
        return false;
    }

    /**
     * Devuelve todos los valores almacenados en el diccionario.
     * @return Lista simple con todos los valores.
     */
    public ListaSimple<V> listarValores() {
        ListaSimple<V> r = new ListaSimple<>();

        for (int i = 0; i < capacidad; i++) {
            NodoSimple<EntradaHash<K, V>> nodo = tabla[i].getInicio();
            while (nodo != null) {
                r.agregar(nodo.getDato().getValor());
                nodo = nodo.getSiguiente();
            }
        }
        return r;
    }
}