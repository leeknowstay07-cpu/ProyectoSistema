package persistencias;

/**
 * Interfaz para representar una acción que pueda deshacerse.
 */
public interface Accion {
    /**
     * Deshace la acción.
     * @param sistema referencia para poder revertir cambios
     */
    void deshacer(IPersistenciaFachada sistema);

    /**
     * Descripción de la acción
     * @return 
     */
    String descripcion();
}