/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Clase que representa un nodo para el árbol binario de búsqueda
 * 
 * @author Paulina, Alec, Sarah
 * @param <T>
 */
public class NodoBST<T extends Comparable<T>> {
    private T dato;
    private NodoBST<T> izquierda;
    private NodoBST<T> derecha;

    public NodoBST(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoBST<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(NodoBST<T> izquierda) {
        this.izquierda = izquierda;
    }

    public NodoBST<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(NodoBST<T> derecha) {
        this.derecha = derecha;
    }
}