package persistencias;

import modelos.Estudiante;

/**
 * Representa una acción sobre las calificaciones de un estudiante.
 * Permite deshacer la acción si se trató de agregar o modificar una calificación.
 */
public class CalificacionAccion implements Accion {

    private Estudiante estudiante;   // Estudiante afectado
    private int indice;              // Índice de la calificación modificada (-1 si fue una nueva calificación)
    private Double valorAnterior;    // Valor anterior de la calificación (null si fue nueva)
    private Double valorActual;      // Nuevo valor de la calificación

    /**
     * Constructor de la acción de calificación.
     * @param estudiante Estudiante afectado.
     * @param indice Índice de la calificación modificada (-1 si fue nueva).
     * @param valorAnterior Valor anterior (null si fue nueva calificación).
     * @param valorActual Nuevo valor de la calificación.
     */
    public CalificacionAccion(Estudiante estudiante, int indice, Double valorAnterior, Double valorActual) {
        this.estudiante = estudiante;
        this.indice = indice;
        this.valorAnterior = valorAnterior;
        this.valorActual = valorActual;
    }

    /**
     * Deshace la acción realizada sobre la calificación.
     * - Si fue una nueva calificación, elimina la última ingresada.
     * - Si fue una modificación, restaura el valor anterior.
     * @param sistema Referencia al sistema de persistencia (no usada directamente aquí).
     */
    @Override
    public void deshacer(IPersistenciaFachada sistema) {
        if (estudiante == null) return;

        if (valorAnterior == null) {
            // Fue una calificación nueva, se elimina la última
            estudiante.removerUltimaCalificacion();
        } else {
            // Fue una modificación, se restaura el valor anterior
            estudiante.setCalificacionAt(indice, valorAnterior);
        }
    }

    /**
     * Devuelve una descripción de la acción, útil para registro o reporte.
     * @return Descripción de la acción realizada.
     */
    @Override
    public String descripcion() {
        return "Calificación estudiante: " + estudiante.getNombre() +
               " (antes=" + valorAnterior + ", ahora=" + valorActual + ")";
    }
}