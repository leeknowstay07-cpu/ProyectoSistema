package estructuras;

/**
 * Nodo para ArbolAVL genérico.
 * Separado como clase propia para reutilización.
 */
public class NodoAVL<T> {

    private T dato;
    private NodoAVL<T> izquierda;
    private NodoAVL<T> derecha;
    private int altura;

    public NodoAVL(T dato) {
        this.dato = dato;
        this.altura = 1; // nodo hoja -> altura 1
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoAVL<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoAVL<T> izquierda) {
        this.izquierda = izquierda;
    }

    public NodoAVL<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoAVL<T> derecha) {
        this.derecha = derecha;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
