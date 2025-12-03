package persistencias;

import estructuras.Diccionario;
import estructuras.ListaDobleCircular;
import estructuras.ListaSimple;
import modelos.Curso;
import modelos.Estudiante;

/**
 * Clase de persistencia para cursos.
 * Gestiona los cursos registrados y las inscripciones de estudiantes,
 * así como la lista de espera y la rotación de roles.
 */
public class PersistenciaCursos {

    // Diccionario para almacenar los cursos por su clave única
    private Diccionario<String, Curso> cursos;

    /**
     * Constructor que inicializa el diccionario de cursos.
     */
    public PersistenciaCursos() {
        cursos = new Diccionario<>();
    }

    /**
     * Agrega un curso al sistema.
     * @param c Curso a agregar.
     * @return true si se agregó correctamente, false si ya existía.
     */
    public boolean agregarCurso(Curso c) {
        return cursos.insertar(c.getClave(), c);
    }

    /**
     * Elimina un curso del sistema según su clave.
     * @param clave Clave del curso a eliminar.
     * @return true si se eliminó correctamente, false si no existía.
     */
    public boolean eliminarCurso(String clave) {
        return cursos.eliminar(clave);
    }

    /**
     * Busca un curso por su clave.
     * @param clave Clave del curso.
     * @return Curso encontrado o null si no existe.
     */
    public Curso buscarCursoPorId(String clave) {
        return cursos.buscar(clave);
    }

    /**
     * Devuelve todos los cursos registrados.
     * @return ListaSimple con todos los cursos.
     */
    public ListaSimple<Curso> listarCursos() {
        return cursos.listarValores();
    }

    /**
     * Inscribe un estudiante en un curso o lo agrega a la lista de espera si está lleno.
     * Además valida que el estudiante no esté ya inscrito ni en la lista de espera.
     * @param claveCurso Clave del curso.
     * @param estudiante Estudiante a inscribir.
     * @return true si se inscribió o se agregó a lista de espera, false si ya estaba registrado.
     */
    public boolean inscribirEstudiante(String claveCurso, Estudiante estudiante) {
        Curso curso = cursos.buscar(claveCurso);
        if (curso == null) {
            return false;
        }

        // Validar si ya está inscrito
        for (int i = 0; i < curso.getInscritos().size(); i++) {
            if (curso.getInscritos().obtener(i).getMatricula().equals(estudiante.getMatricula())) {
                System.out.println("Estudiante ya inscrito en este curso.");
                return false;
            }
        }

        // Validar si ya está en lista de espera
        ListaDobleCircular<Estudiante>.Nodo nodo = curso.getListaEspera().getCabeza();
        if (nodo != null) {
            int contador = 0;
            do {
                if (nodo.getDato().getMatricula().equals(estudiante.getMatricula())) {
                    System.out.println("Estudiante ya en lista de espera.");
                    return false;
                }
                nodo = nodo.getSiguiente();
                contador++;
            } while (nodo != curso.getListaEspera().getCabeza() && contador < curso.getListaEspera().size());
        }

        // Inscripción o lista de espera según la capacidad
        if (curso.getInscritos().size() < curso.getCapacidadMaxima()) {
            curso.getInscritos().agregar(estudiante);  // Agrega a inscritos
            curso.getRoles().agregar(estudiante);      // Agrega al sistema de roles
            System.out.println("Estudiante inscrito");
        } else {
            curso.getListaEspera().agregar(estudiante); // Agrega a lista de espera
            System.out.println("Curso lleno. Agregado a lista de espera.");
        }

        return true;
    }

    /**
     * Muestra los primeros n estudiantes de la lista de espera de un curso.
     * @param claveCurso Clave del curso.
     * @param n Número de estudiantes a mostrar.
     */
    public void mostrarEspera(String claveCurso, int n) {
        Curso curso = cursos.buscar(claveCurso);
        if (curso != null) {
            System.out.println("Lista de espera del curso " + curso.getNombre() + ":");
            curso.getListaEspera().mostrarN(n);
        }
    }

    /**
     * Rota el rol del curso (por ejemplo, líder o encargado de turno) y devuelve el estudiante asignado.
     * @param claveCurso Clave del curso.
     * @return Estudiante con el nuevo rol o null si no hay estudiantes.
     */
    public Estudiante rotarRol(String claveCurso) {
        Curso curso = cursos.buscar(claveCurso);
        if (curso == null) {
            return null;
        }

        // Si la lista circular está vacía, no hay a quién asignar
        if (curso.getRoles().estaVacia()) {
            return null;
        }

        // La lista circular maneja la rotación automáticamente
        return curso.getRoles().rotarRol();
    }

    /**
     * Obtiene el estudiante que actualmente tiene el rol activo en un curso.
     * @param claveCurso Clave del curso.
     * @return Estudiante con el rol actual o null si no hay ninguno.
     */
    public Estudiante obtenerRolActual(String claveCurso) {
        Curso curso = cursos.buscar(claveCurso);
        if (curso == null) {
            return null;
        }
        return curso.getRoles().getActual();
    }
}