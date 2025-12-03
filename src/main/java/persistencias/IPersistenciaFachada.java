package persistencias;

import estructuras.ListaSimple;
import modelos.Estudiante;
import modelos.Curso;
import modelos.SolicitudCalificacion;

/**
 * Interfaz que define las operaciones de persistencia del sistema.
 * 
 * Agrupa métodos para:
 * - Gestión de estudiantes.
 * - Gestión de cursos.
 * - Inscripciones y listas de espera.
 * - Manejo de calificaciones.
 * - Registro y deshacer de acciones.
 * - Reportes y roles en cursos.
 */
public interface IPersistenciaFachada {

    // MÉTODOS DE ESTUDIANTES
    /**
     * Agrega un estudiante al sistema.
     * @param e Estudiante a agregar.
     * @return true si se agregó correctamente, false si ya existía.
     */
    boolean agregarEstudiante(Estudiante e);

    /**
     * Elimina un estudiante según su matrícula.
     * @param matricula Matrícula del estudiante a eliminar.
     * @return true si se eliminó correctamente, false si no existe.
     */
    boolean eliminarEstudiante(String matricula);

    /**
     * Busca un estudiante por su matrícula.
     * @param matricula Matrícula del estudiante.
     * @return El estudiante encontrado o null si no existe.
     */
    Estudiante buscarEstudiante(String matricula);

    /**
     * Obtiene todos los estudiantes registrados.
     * @return ListaSimple con todos los estudiantes.
     */
    ListaSimple<Estudiante> obtenerTodosEstudiantes();

    /**
     * Imprime en consola todos los estudiantes registrados.
     */
    void imprimirTodosEstudiantes();

    // MÉTODOS DE CURSOS
    /**
     * Agrega un curso al sistema.
     * @param c Curso a agregar.
     * @return true si se agregó correctamente, false si ya existía.
     */
    boolean agregarCurso(Curso c);

    /**
     * Elimina un curso según su clave.
     * @param claveCurso Clave del curso a eliminar.
     * @return true si se eliminó correctamente, false si no existe.
     */
    boolean eliminarCurso(String claveCurso);

    /**
     * Busca un curso por su ID o clave.
     * @param id Clave del curso.
     * @return Curso encontrado o null si no existe.
     */
    Curso buscarCursoPorId(String id);

    /**
     * Lista todos los cursos registrados.
     * @return ListaSimple con todos los cursos.
     */
    ListaSimple<Curso> listarCursos();

    // MÉTODOS DE INSCRIPCIÓN
    /**
     * Inscribe un estudiante en un curso.
     * @param claveCurso Clave del curso.
     * @param estudiante Estudiante a inscribir.
     * @return true si la inscripción fue exitosa, false si ya estaba inscrito o en lista de espera.
     */
    boolean inscribirEstudiante(String claveCurso, Estudiante estudiante);

    /**
     * Retorna la lista de estudiantes inscritos en un curso.
     * @param claveCurso Clave del curso.
     * @return ListaSimple de inscritos.
     */
    ListaSimple<Estudiante> listarInscritos(String claveCurso);

    /**
     * Retorna una lista con los primeros N estudiantes de la lista de espera.
     * @param claveCurso Clave del curso.
     * @param n Número de estudiantes a obtener.
     * @return ListaSimple con los primeros N estudiantes en espera.
     */
    ListaSimple<Estudiante> mostrarListaEspera(String claveCurso, int n);

    // MÉTODOS DE CALIFICACIONES
    /**
     * Envía una solicitud de calificación.
     * @param s Solicitud de calificación.
     * @return true si se envió correctamente.
     */
    boolean enviarSolicitudCalificacion(SolicitudCalificacion s);

    /**
     * Procesa la siguiente solicitud de calificación en la cola.
     * @return true si se procesó correctamente, false si no hay solicitudes.
     */
    boolean procesarSiguienteSolicitud();

    // MÉTODOS DE ACCIONES (DESHACER)
    /**
     * Deshace la última acción realizada en el sistema (registro, eliminación, inscripción, etc.).
     * @return true si se deshizo correctamente, false si no hay acciones que deshacer.
     */
    boolean deshacerUltimaAccion();

    // MÉTODOS DE REPORTES
    /**
     * Lista los estudiantes ordenados por promedio.
     * @return ListaSimple de estudiantes ordenada.
     */
    ListaSimple<Estudiante> listarEstudiantesPorPromedio();

    // MÉTODOS DE ROLES (CURSOS)
    /**
     * Rota el rol del curso (por ejemplo, líder de equipo) y devuelve el estudiante asignado.
     * @param claveCurso Clave del curso.
     * @return Estudiante que recibe el rol, o null si no hay estudiantes.
     */
    Estudiante rotarRol(String claveCurso);

    /**
     * Obtiene el estudiante que actualmente tiene el rol activo en un curso.
     * @param claveCurso Clave del curso.
     * @return Estudiante con el rol actual o null si no hay ninguno.
     */
    Estudiante obtenerRolActual(String claveCurso);
}