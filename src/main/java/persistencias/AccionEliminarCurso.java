package persistencias;

import modelos.Curso;

public class AccionEliminarCurso implements Accion {
    private Curso cursoEliminado;

    public AccionEliminarCurso(Curso cursoEliminado) {
        this.cursoEliminado = cursoEliminado;
    }

    @Override
    public void deshacer(IPersistenciaFachada sistema) {
        sistema.agregarCurso(cursoEliminado);
    }

    @Override
    public String descripcion() {
        return "Eliminación curso: " + cursoEliminado.getNombre();
    }
}