package persistencias;

import estructuras.ArbolAVL;
import estructuras.ArbolBST;
import estructuras.ListaSimple;
import estructuras.NodoSimple;
import estructuras.ParPromedio;
import estructuras.Cola;
import estructuras.Pila;
import modelos.Estudiante;
import modelos.SolicitudCalificacion;

/**
 * Clase que implementa una persistencia para las acciones de los estudiantes 
 * usando un árbol binario de búsqueda y AVL
 * 
 * @author Paulina, Alec, Sarah
 */
public class PersistenciaEstudiantes {

    // BST que mantiene todos los estudiantes ordenados por matrícula
    private ArbolBST<Estudiante> bstEstudiantes;

    // Cola de solicitudes de calificación (FIFO)
    private Cola<SolicitudCalificacion> colaSolicitudes;

    public PersistenciaEstudiantes() {
        bstEstudiantes = new ArbolBST<>();
        colaSolicitudes = new Cola<>();
    }

    /**
     * Inserta un estudiante en un árbol binario ordenado por matrícula del estudiante
     * @param estudiante estudiante a insertar
     * @return true si se insertó, false en caso contrario
     */
    public boolean agregarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        return bstEstudiantes.insertar(estudiante);
    }

    /**
     * Elimina un estudiante del sistema por matrícula.
     *
     * @param matricula matrícula del estudiante a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminarEstudiante(String matricula) {
        Estudiante temp = new Estudiante();
        temp.setMatricula(matricula);
        return bstEstudiantes.eliminar(temp); // El método eliminar debe estar implementado en ArbolBST
    }

    /**
     * Busca un estudiante en el árbol binario por matrícula
     * @param matricula matrícula del estudiante a buscar
     * @return estudiante encontrado
     */
    public Estudiante buscarEstudiante(String matricula) {
        Estudiante temp = new Estudiante();
        temp.setMatricula(matricula);
        return bstEstudiantes.buscar(temp);
    }

    /**
     * Obtiene una lista con todos los estudiantes del árbol, inorder
     * @return lista de estudiantes
     */
    public ListaSimple<Estudiante> obtenerTodosEstudiantes() {
        return bstEstudiantes.inorder();
    }
    
    public void imprimirTodosEstudiantes() {
        bstEstudiantes.imprimirInorder();
    }

    /**
     * Lista estudiantes del árbol por promedio inorder
     * @return 
     */
    public ListaSimple<Estudiante> listarEstudiantesPorPromedio() {

        ListaSimple<Estudiante> todos = bstEstudiantes.inorder();

        // Crear un AVL tipo ParPromedio (promedio, estudiante)
        ArbolAVL<ParPromedio> avl = new ArbolAVL<>();

        // Insertar todos los estudiantes en el AVL
        NodoSimple<Estudiante> nodo = todos.getInicio();
        while (nodo != null) {
            Estudiante e = nodo.getDato();
            double prom = e.promedio();
            avl.insertar(new ParPromedio(prom, e));
            nodo = nodo.getSiguiente();
        }

        // Lista de los estudiantes del AVL inorder, de menor a mayor promedio
        ListaSimple<ParPromedio> listaProm = new ListaSimple<>();
        avl.inorder(listaProm);

        ListaSimple<Estudiante> resultado = new ListaSimple<>();
        NodoSimple<ParPromedio> p = listaProm.getInicio();
        while (p != null) {
            resultado.agregar(p.getDato().getEstudiante());
            p = p.getSiguiente();
        }

        return resultado;
    }

    // SOLICITUDES DE CALIFICACIÓN (COLA)
    /**
     * Encola una nueva solicitud de calificación
     * @param s
     */
    public void enviarSolicitudCalificacion(SolicitudCalificacion s) {
        if (s == null) {
            return;
        }
        colaSolicitudes.enqueue(s);
    }

    /**
     * Procesa la siguiente solicitud en la cola,
     * Actualiza la calificación del estudiante y registra la acción en la pila.
     *
     * @param acciones Pila de acciones 
     * @return
     */
    public boolean procesarSiguienteSolicitud(Pila<Accion> acciones) {
        if (colaSolicitudes.estaVacia()) {
            return false;
        }

        // desencola para obtener la solicitud que sigue
        SolicitudCalificacion s = colaSolicitudes.dequeue();
        if (s == null) {
            return false;
        }

        Estudiante est = buscarEstudiante(s.getMatricula());
        if (est == null) {
            return false;
        }

        if (s.getTipo() == SolicitudCalificacion.Tipo.NUEVA) {
            // Añadir calificación al final porque es una calificación nueva
            est.agregarCalificacion(s.getNuevaCalificacion());

            // Registrar acción en la pila para poder deshacer
            CalificacionAccion accion = new CalificacionAccion(est, -1, null, s.getNuevaCalificacion());
            acciones.push(accion);
            return true;
        } else {
            // Si la calificación es para modificar
            int idx = s.getIndice(); // obtenemos la posición a modificar
            Double anterior = est.getCalificacionAt(idx);
            if (anterior == null) {
                return false;
            }

            est.setCalificacionAt(idx, s.getNuevaCalificacion());

            // Modificamos la calificación y la añadimos a la pila de acciones
            CalificacionAccion accion = new CalificacionAccion(est, idx, anterior, s.getNuevaCalificacion());
            acciones.push(accion);
            return true;
        }
    }
}