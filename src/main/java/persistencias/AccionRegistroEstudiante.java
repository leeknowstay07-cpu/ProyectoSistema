package persistencias;

import modelos.Estudiante;

public class AccionRegistroEstudiante implements Accion {
    private Estudiante estudiante;

    public AccionRegistroEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public void deshacer(IPersistenciaFachada sistema) {
        sistema.eliminarEstudiante(estudiante.getMatricula());
    }

    @Override
    public String descripcion() {
        return "Registro estudiante: " + estudiante.getNombre();
    }
}