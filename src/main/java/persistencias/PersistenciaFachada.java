package persistencias;

import estructuras.ListaDobleCircular;
import estructuras.Pila;
import modelos.*;
import estructuras.ListaSimple;

public class PersistenciaFachada implements IPersistenciaFachada {

    private PersistenciaEstudiantes estudiantes; // Gestión de estudiantes
    private PersistenciaCursos cursos;           // Gestión de cursos

    // Pila para registrar acciones y permitir deshacerlas
    private Pila<Accion> acciones;

    /**
     * Constructor que inicializa las clases de persistencia y la pila de acciones.
     */
    public PersistenciaFachada() {
        estudiantes = new PersistenciaEstudiantes();
        cursos = new PersistenciaCursos();
        acciones = new Pila<>();
    }

    // MÉTODOS DE ESTUDIANTES

    /**
     * Agrega un estudiante al sistema y registra la acción.
     * @param e Estudiante a agregar.
     * @return true si se agregó correctamente, false si ya existía.
     */
    @Override
    public boolean agregarEstudiante(Estudiante e) {
        boolean ok = estudiantes.agregarEstudiante(e);

        if (ok) {
            acciones.push(new AccionRegistroEstudiante(e));
        }

        return ok;
    }

    /**
     * Elimina un estudiante según su matrícula y registra la acción.
     * @param matricula Matrícula del estudiante a eliminar.
     * @return true si se eliminó correctamente, false si no existe.
     */
    @Override
    public boolean eliminarEstudiante(String matricula) {
        Estudiante est = estudiantes.buscarEstudiante(matricula);
        if (est == null) {
            return false;
        }

        boolean ok = estudiantes.eliminarEstudiante(matricula);

        if (ok) {
            acciones.push(new AccionEliminarEstudiante(est));
        }

        return ok;
    }

    /**
     * Busca un estudiante por su matrícula.
     * @param m Matrícula del estudiante.
     * @return El estudiante encontrado o null si no existe.
     */
    @Override
    public Estudiante buscarEstudiante(String m) {
        return estudiantes.buscarEstudiante(m);
    }

    /**
     * Obtiene todos los estudiantes registrados en el sistema.
     * @return ListaSimple de estudiantes.
     */
    @Override
    public ListaSimple<Estudiante> obtenerTodosEstudiantes() {
        return estudiantes.obtenerTodosEstudiantes();
    }

    // MÉTODOS DE CURSOS
    /**
     * Agrega un curso al sistema y registra la acción.
     * @param c Curso a agregar.
     * @return true si se agregó correctamente, false si ya existía.
     */
    @Override
    public boolean agregarCurso(Curso c) {
        boolean ok = cursos.agregarCurso(c);

        if (ok) {
            acciones.push(new AccionRegistroCurso(c));
        }

        return ok;
    }

    /**
     * Elimina un curso según su clave y registra la acción.
     * @param claveCurso Clave del curso a eliminar.
     * @return true si se eliminó correctamente, false si no existe.
     */
    @Override
    public boolean eliminarCurso(String claveCurso) {
        Curso eliminado = cursos.buscarCursoPorId(claveCurso);
        if (eliminado == null) {
            return false;
        }

        boolean ok = cursos.eliminarCurso(claveCurso);
        if (ok) {
            acciones.push(new AccionEliminarCurso(eliminado));
        }

        return ok;
    }

    /**
     * Busca un curso por su ID/clave.
     * @param id Clave del curso.
     * @return Curso encontrado o null si no existe.
     */
    @Override
    public Curso buscarCursoPorId(String id) {
        return cursos.buscarCursoPorId(id);
    }

    /**
     * Lista todos los cursos registrados.
     * @return ListaSimple de cursos.
     */
    @Override
    public ListaSimple<Curso> listarCursos() {
        return cursos.listarCursos();
    }

    /**
     * Inscribe un estudiante en un curso y registra la acción.
     * @param claveCurso Clave del curso.
     * @param estudiante Estudiante a inscribir.
     * @return true si la inscripción fue exitosa, false si no.
     */
    @Override
    public boolean inscribirEstudiante(String claveCurso, Estudiante estudiante) {
        boolean ok = cursos.inscribirEstudiante(claveCurso, estudiante);

        if (ok) {
            acciones.push(new AccionInscripcion(claveCurso, estudiante));
        }

        return ok;
    }

    // LISTAS DE INSCRITOS / ESPERA
    /**
     * Lista los estudiantes inscritos en un curso.
     * @param claveCurso Clave del curso.
     * @return ListaSimple de estudiantes inscritos, o null si no existe el curso.
     */
    @Override
    public ListaSimple<Estudiante> listarInscritos(String claveCurso) {
        Curso c = cursos.buscarCursoPorId(claveCurso);
        if (c == null) {
            return null;
        }
        return c.getInscritos();
    }

    /**
     * Muestra los primeros n estudiantes de la lista de espera de un curso.
     * @param claveCurso Clave del curso.
     * @param n Número de estudiantes a mostrar.
     * @return ListaSimple de estudiantes en espera.
     */
    @Override
    public ListaSimple<Estudiante> mostrarListaEspera(String claveCurso, int n) {
        Curso c = cursos.buscarCursoPorId(claveCurso);
        if (c == null) {
            return null;
        }

        ListaSimple<Estudiante> listaN = new ListaSimple<>();
        ListaDobleCircular<Estudiante>.Nodo actual = c.getListaEspera().getCabeza();
        int contador = 0;

        if (actual == null) {
            return listaN;
        }

        do {
            listaN.agregar(actual.getDato());
            actual = actual.getSiguiente();
            contador++;
        } while (contador < n && actual != c.getListaEspera().getCabeza());

        return listaN;
    }

    // CALIFICACIONES (COLA)
    /**
     * Envía una solicitud de calificación a la cola de estudiantes.
     * @param s Solicitud de calificación.
     * @return true siempre que se envíe correctamente.
     */
    @Override
    public boolean enviarSolicitudCalificacion(SolicitudCalificacion s) {
        estudiantes.enviarSolicitudCalificacion(s);
        return true;
    }

    /**
     * Procesa la siguiente solicitud de calificación en la cola.
     * @return true si se procesó correctamente, false si la cola está vacía.
     */
    @Override
    public boolean procesarSiguienteSolicitud() {
        return estudiantes.procesarSiguienteSolicitud(acciones);
    }

    // DESHACER ACCIONES (PILA)
    /**
     * Deshace la última acción registrada.
     * @return true si se deshizo correctamente, false si no hay acciones.
     */
    @Override
    public boolean deshacerUltimaAccion() {
        if (acciones.estaVacia()) {
            return false;
        }

        Accion a = acciones.pop();
        a.deshacer(this);

        return true;
    }

    // REPORTES
    /**
     * Lista los estudiantes ordenados por promedio.
     * @return ListaSimple de estudiantes.
     */
    @Override
    public ListaSimple<Estudiante> listarEstudiantesPorPromedio() {
        return estudiantes.listarEstudiantesPorPromedio();
    }

    // ROLES EN CURSOS (LISTA CIRCULAR)
    /**
     * Rota el rol del curso (por ejemplo, líder de equipo o encargado) y devuelve el estudiante con el rol.
     * @param claveCurso Clave del curso.
     * @return Estudiante que recibe el rol.
     */
    @Override
    public Estudiante rotarRol(String claveCurso) {
        return cursos.rotarRol(claveCurso);
    }

    /**
     * Obtiene el estudiante que actualmente tiene el rol en un curso.
     * @param claveCurso Clave del curso.
     * @return Estudiante con el rol actual.
     */
    @Override
    public Estudiante obtenerRolActual(String claveCurso) {
        return cursos.obtenerRolActual(claveCurso);
    }

    /**
     * Imprime en consola todos los estudiantes registrados.
     */
    @Override
    public void imprimirTodosEstudiantes() {
        estudiantes.imprimirTodosEstudiantes();
    }

    /**
     * Devuelve la última acción registrada sin deshacerla.
     * @return La última acción o null si no hay ninguna.
     */
    public Accion obtenerUltimaAccion() {
        if (acciones.estaVacia()) {
            return null;
        }
        return acciones.peek(); 
    }
}