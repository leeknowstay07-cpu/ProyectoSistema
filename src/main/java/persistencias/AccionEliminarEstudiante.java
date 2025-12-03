package persistencias;

import modelos.Estudiante;

/**
 * Acción que permite deshacer la eliminación de un estudiante.
 */
public class AccionEliminarEstudiante implements Accion {

    private Estudiante estudiante;

    public AccionEliminarEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public void deshacer(IPersistenciaFachada sistema) {
        // Vuelve a agregar el estudiante eliminado
        sistema.agregarEstudiante(estudiante);
    }

    @Override
    public String descripcion() {
        return "Eliminación estudiante: " + estudiante.getNombre() +
               " (matrícula: " + estudiante.getMatricula() + ")";
    }
}