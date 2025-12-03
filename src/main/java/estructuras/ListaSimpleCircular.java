package estructuras;

public class ListaSimpleCircular<T> {

    private NodoSimple<T> actual;  // nodo "activo", que marca el rol actual

    public ListaSimpleCircular() {
        this.actual = null;
    }

    // Agregar al final (o primer nodo)
    public void agregar(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);

        if (actual == null) {
            // Primer nodo: se apunta a sí mismo
            actual = nuevo;
            nuevo.setSiguiente(nuevo);
        } else {
            // Buscar el último (el que apunta a actual)
            NodoSimple<T> temp = actual;

            while (temp.getSiguiente() != actual) {
                temp = temp.getSiguiente();
            }

            temp.setSiguiente(nuevo);
            nuevo.setSiguiente(actual);
        }
    }

    // Rotar hacia el siguiente estudiante en la lista
    public T rotarRol() {
        if (actual == null) return null;

        actual = actual.getSiguiente();
        return actual.getDato();
    }

    // Obtener quien tiene actualmente el rol
    public T getActual() {
        if (actual == null) return null;
        return actual.getDato();
    }

    public boolean estaVacia() {
        return actual == null;
    }
}