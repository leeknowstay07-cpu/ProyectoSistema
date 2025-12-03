package persistencias;

import modelos.Curso;

public class AccionRegistroCurso implements Accion {
    private Curso curso;

    public AccionRegistroCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public void deshacer(IPersistenciaFachada sistema) {
        sistema.eliminarCurso(curso.getClave());
    }

    @Override
    public String descripcion() {
        return "Registro curso: " + curso.getNombre();
    }
}