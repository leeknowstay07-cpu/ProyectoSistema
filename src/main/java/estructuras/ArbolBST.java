/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Clase que representa un árbol binario de búsqueda genérico
 * 
 * @author Paulina, Alec, Sarah
 * @param <T>
 */
public class ArbolBST<T extends Comparable<T>> {

    private NodoBST<T> raiz;

    public ArbolBST() {
        this.raiz = null;
    }

    /**
     * Inserta un estudiante en el BST.
     *
     * @param dato Estudiante a insertar
     * @return true si se insertó, false si ya existe una matrícula igual
     */
    public boolean insertar(T dato) {
        if (dato == null) {
            throw new IllegalArgumentException("Estudiante o matrícula nula");
        }

        // Insertar estudiante directamente
        if (raiz == null) {
            raiz = new NodoBST(dato);
            return true;
        }
        NodoBST<T> actual = raiz;
        while (true) {
            int comparacion = dato.compareTo(actual.getDato());
            if (comparacion == 0) {
                // matrícula duplicada (no insertamos)
                return false;
                // si es menor se va a la izquierda
            } else if (comparacion < 0) {
                // si encontramos espacio vacío insertamos
                if (actual.getIzquierda() == null) {
                    actual.setIzquierda(new NodoBST(dato));
                    return true;
                    // si no, vamos recorriendo hacia la izquierda hasta encontrar un espacio null
                } else {
                    actual = actual.getIzquierda();
                }
            } else { // si es mayor se va a la derecha
                // si encontramos espacio vacío insertamos
                if (actual.getDerecha() == null) {
                    actual.setDerecha(new NodoBST(dato));
                    return true;
                    // si no, vamos recorriendo hacia la derecha hasta encontrar un espacio null
                } else {
                    actual = actual.getDerecha();
                }
            }
        }
    }

    /**
     * Elimina un nodo del BST que coincide con el dato.
     *
     * @param dato Dato a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminar(T dato) {
        NodoBST<T> padre = null;
        NodoBST<T> actual = raiz;

        // Buscar el nodo
        while (actual != null && dato.compareTo(actual.getDato()) != 0) {
            padre = actual;
            if (dato.compareTo(actual.getDato()) < 0) {
                actual = actual.getIzquierda();
            } else {
                actual = actual.getDerecha();
            }
        }

        if (actual == null) {
            return false; // no encontrado
        }

        // Caso 1: Nodo con solo un hijo o sin hijos
        if (actual.getIzquierda() == null || actual.getDerecha() == null) {
            NodoBST<T> hijo = (actual.getIzquierda() != null) ? actual.getIzquierda() : actual.getDerecha();

            if (padre == null) {
                raiz = hijo; // eliminar la raíz
            } else if (padre.getIzquierda() == actual) {
                padre.setIzquierda(hijo);
            } else {
                padre.setDerecha(hijo);
            }
        } // Caso 2: Nodo con dos hijos
        else {
            // Buscar sucesor (mínimo del subárbol derecho)
            NodoBST<T> padreSucesor = actual;
            NodoBST<T> sucesor = actual.getDerecha();
            while (sucesor.getIzquierda() != null) {
                padreSucesor = sucesor;
                sucesor = sucesor.getIzquierda();
            }

            // Copiar valor del sucesor al nodo a eliminar
            actual.setDato(sucesor.getDato());

            // Eliminar sucesor
            if (padreSucesor.getIzquierda() == sucesor) {
                padreSucesor.setIzquierda(sucesor.getDerecha());
            } else {
                padreSucesor.setDerecha(sucesor.getDerecha());
            }
        }

        return true;
    }

    /**
     * Busca un estudiante por matrícula
     *
     * @param dato matrícula a buscar
     * @return regresa el estudiante encontrado o null si no se encontró
     */
    public T buscar(T dato) {
        NodoBST<T> actual = raiz;

        while (actual != null) {
            int comparacion = dato.compareTo(actual.getDato());

            if (comparacion == 0) {
                return actual.getDato();
            } else if (comparacion < 0) {
                actual = actual.getIzquierda();
            } else {
                actual = actual.getDerecha();
            }
        }
        return null;
    }

    /**
     * Recorre el árbol en in-order y devuelve la lista de estudiantes (ordenada
     * por matrícula ascendente).
     *
     * @return lista de estudiantes en orden
     */
    public ListaSimple<T> inorder() {
        ListaSimple<T> lista = new ListaSimple<>();
        inorder(raiz, lista);
        return lista;
    }

    private void inorder(NodoBST<T> nodo, ListaSimple<T> lista) {
        if (nodo != null) {
            inorder(nodo.getIzquierda(), lista);
            lista.agregar(nodo.getDato());
            inorder(nodo.getDerecha(), lista);
        }
    }

    // Solo imprime usando tu ListaSimple
    public void imprimirInorder() {
        ListaSimple<T> l = inorder();
        if (l.estaVacia()) {
            System.out.println("Árbol vacío.");
            return;
        }

        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.obtener(i));
        }
    }
}