package estructuras;

/**
 * Implementación genérica de un árbol AVL (árbol binario de búsqueda auto-balanceado).
 * 
 * Características:
 * - Mantiene el balanceo automáticamente tras cada inserción.
 * - Permite inserciones, recorrido inorder y verificación de vacío.
 * 
 * @param <T> Tipo de dato que debe implementar Comparable para comparación.
 */
public class ArbolAVL<T extends Comparable<T>> {

    private NodoAVL<T> raiz; // Nodo raíz del árbol
    private int tamano;      // Número de nodos en el árbol

    /**
     * Constructor que inicializa un árbol vacío.
     */
    public ArbolAVL() {
        this.raiz = null;
        this.tamano = 0;
    }

    /**
     * Calcula la altura de un nodo.
     * @param n Nodo.
     * @return Altura del nodo, 0 si es null.
     */
    private int altura(NodoAVL<T> n) {
        return n == null ? 0 : n.getAltura(); 
    }
    
    /**
     * Calcula el factor de balance de un nodo.
     * @param n Nodo.
     * @return Diferencia entre altura del subárbol izquierdo y derecho.
     */
    private int getBalance(NodoAVL<T> n) {
        return n == null ? 0 : altura(n.getIzquierda()) - altura(n.getDerecha()); 
    }

    /**
     * Rotación simple a la derecha.
     * @param y Nodo a rotar.
     * @return Nodo que reemplaza a y.
     */
    private NodoAVL<T> rotacionDerecha(NodoAVL<T> y) {
        NodoAVL<T> x = y.getIzquierda();
        NodoAVL<T> t2 = x.getDerecha();

        x.setDerecha(y);
        y.setIzquierda(t2);

        y.setAltura(Math.max(altura(y.getIzquierda()), altura(y.getDerecha())) + 1);
        x.setAltura(Math.max(altura(x.getIzquierda()), altura(x.getDerecha())) + 1);

        return x;
    }

    /**
     * Rotación simple a la izquierda.
     * @param x Nodo a rotar.
     * @return Nodo que reemplaza a x.
     */
    private NodoAVL<T> rotacionIzquierda(NodoAVL<T> x) {
        NodoAVL<T> y = x.getDerecha();
        NodoAVL<T> t2 = y.getIzquierda();

        y.setIzquierda(x);
        x.setDerecha(t2);

        x.setAltura(Math.max(altura(x.getIzquierda()), altura(x.getDerecha())) + 1);
        y.setAltura(Math.max(altura(y.getIzquierda()), altura(y.getDerecha())) + 1);

        return y;
    }

    /**
     * Inserta un dato en el árbol AVL.
     * @param dato Dato a insertar.
     * @return true si se insertó correctamente, false si era duplicado.
     */
    public boolean insertar(T dato) {
        if (dato == null) throw new IllegalArgumentException("Dato nulo");
        int prev = tamano;
        raiz = insertarRec(raiz, dato);
        return tamano > prev;
    }

    // Inserción recursiva con balanceo
    private NodoAVL<T> insertarRec(NodoAVL<T> nodo, T dato) {
        if (nodo == null) {
            tamano++;
            return new NodoAVL<>(dato);
        }

        int cmp = dato.compareTo(nodo.getDato());
        if (cmp < 0) {
            nodo.setIzquierda(insertarRec(nodo.getIzquierda(), dato));
        } else if (cmp > 0) {
            nodo.setDerecha(insertarRec(nodo.getDerecha(), dato));
        } else {
            return nodo; // duplicado
        }

        nodo.setAltura(Math.max(altura(nodo.getIzquierda()), altura(nodo.getDerecha())) + 1);
        int balance = getBalance(nodo);

        // Casos de rotación
        if (balance > 1 && dato.compareTo(nodo.getIzquierda().getDato()) < 0)
            return rotacionDerecha(nodo); // LL
        if (balance < -1 && dato.compareTo(nodo.getDerecha().getDato()) > 0)
            return rotacionIzquierda(nodo); // RR
        if (balance > 1 && dato.compareTo(nodo.getIzquierda().getDato()) > 0) { // LR
            nodo.setIzquierda(rotacionIzquierda(nodo.getIzquierda()));
            return rotacionDerecha(nodo);
        }
        if (balance < -1 && dato.compareTo(nodo.getDerecha().getDato()) < 0) { // RL
            nodo.setDerecha(rotacionDerecha(nodo.getDerecha()));
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    /**
     * Recorre el árbol en orden y agrega los elementos a una lista simple.
     * @param lista Lista donde se agregan los elementos.
     */
    public void inorder(ListaSimple<T> lista) {
        inorderRec(raiz, lista);
    }

    private void inorderRec(NodoAVL<T> nodo, ListaSimple<T> lista) {
        if (nodo == null) return;
        inorderRec(nodo.getIzquierda(), lista);
        lista.agregar(nodo.getDato());
        inorderRec(nodo.getDerecha(), lista);
    }

    /**
     * Verifica si el árbol está vacío.
     * @return true si no tiene nodos.
     */
    public boolean estaVacio() {
        return raiz == null; 
    }
    
    /**
     * Devuelve el número de nodos en el árbol.
     * @return Tamaño del árbol.
     */
    public int tamano() {
        return tamano; 
    }

    /**
     * Imprime los elementos del árbol en orden ascendente.
     */
    public void imprimirInorder() {
        imprimirRec(raiz); 
    }
    
    private void imprimirRec(NodoAVL<T> n) {
        if (n == null) return;
        imprimirRec(n.getIzquierda());
        System.out.println(n.getDato());
        imprimirRec(n.getDerecha());
    }
}