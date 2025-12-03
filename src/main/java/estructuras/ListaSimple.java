package estructuras;

public class ListaSimple<T> {

    private NodoSimple<T> inicio;
    private int size;

    public ListaSimple() {
        inicio = null;
        size = 0;
    }

    // INSERTAR AL FINAL
    public void agregar(T dato) {
        NodoSimple<T> nuevo = new NodoSimple<>(dato);

        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoSimple<T> aux = inicio;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
        size++;
    }

    // OBTENER POR ÍNDICE
    public T obtener(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }

        NodoSimple<T> aux = inicio;
        for (int i = 0; i < index; i++) {
            aux = aux.getSiguiente();
        }
        return aux.getDato();
    }

    // BUSCAR ELEMENTO (comparando con equals)
    public boolean contiene(T valor) {
        NodoSimple<T> aux = inicio;
        while (aux != null) {
            if (aux.getDato().equals(valor)) return true;
            aux = aux.getSiguiente();
        }
        return false;
    }

    // ELIMINAR POR OBJETO
    public boolean eliminar(T valor) {
        if (inicio == null) return false;

        if (inicio.getDato().equals(valor)) {
            inicio = inicio.getSiguiente();
            size--;
            return true;
        }

        NodoSimple<T> aux = inicio;
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getDato().equals(valor)) {
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                size--;
                return true;
            }
            aux = aux.getSiguiente();
        }

        return false;
    }

    public int size() {
        return size;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    public NodoSimple<T> getInicio() {
        return inicio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodoSimple<T> aux = inicio;

        while (aux != null) {
            sb.append(aux.getDato()).append(" -> ");
            aux = aux.getSiguiente();
        }
        sb.append("null");

        return sb.toString();
    }
}