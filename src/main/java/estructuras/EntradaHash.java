package estructuras;

/**
 * Representa una entrada en un diccionario o tabla hash.
 * 
 * Contiene un par clave-valor.
 * Se utiliza principalmente en la clase Diccionario para manejar
 * colisiones mediante listas encadenadas.
 * 
 * @param <K> Tipo de la clave.
 * @param <V> Tipo del valor.
 */
public class EntradaHash<K, V> {

    private K clave;   // Clave única de la entrada
    private V valor;   // Valor asociado a la clave

    /**
     * Constructor que inicializa la entrada con su clave y valor.
     * @param clave Clave de la entrada.
     * @param valor Valor asociado a la clave.
     */
    public EntradaHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    /**
     * Obtiene la clave de la entrada.
     * @return Clave.
     */
    public K getClave() {
        return clave; 
    }
    
    /**
     * Obtiene el valor de la entrada.
     * @return Valor.
     */
    public V getValor() {
        return valor; 
    }
    
    /**
     * Modifica el valor asociado a la clave.
     * @param valor Nuevo valor.
     */
    public void setValor(V valor) {
        this.valor = valor; 
    }
}