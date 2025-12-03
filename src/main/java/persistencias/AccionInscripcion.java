package persistencias;

import modelos.Curso;
import modelos.Estudiante;

public class AccionInscripcion implements Accion {
    private String claveCurso;
    private Estudiante estudiante;

    public AccionInscripcion(String claveCurso, Estudiante estudiante) {
        this.claveCurso = claveCurso;
        this.estudiante = estudiante;
    }

    @Override
    public void deshacer(IPersistenciaFachada sistema) {
        Curso c = sistema.buscarCursoPorId(claveCurso);
        if (c != null) {
            c.getInscritos().eliminar(estudiante);
        }
    }

    @Override
    public String descripcion() {
        return "Inscripción estudiante: " + estudiante.getNombre() + " en curso: " + claveCurso;
    }
}