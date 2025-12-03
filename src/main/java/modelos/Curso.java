package modelos;

import estructuras.ListaDobleCircular;
import estructuras.ListaSimple;
import estructuras.ListaSimpleCircular;

/**
 * Representa un curso del sistema.
 * 
 * Contiene:
 * - Información básica del curso (clave, nombre, capacidad máxima).
 * - Lista de estudiantes inscritos.
 * - Lista de espera de estudiantes (doble circular).
 * - Lista circular de roles para asignación de líderes/tutores.
 * 
 * Permite rotar roles entre los estudiantes inscritos.
 */
public class Curso {

    private String clave; // Clave única del curso
    private String nombre; // Nombre del curso
    private int capacidadMaxima = 2; // Capacidad máxima de estudiantes

    private ListaSimple<Estudiante> inscritos;       // Lista de estudiantes inscritos
    private ListaDobleCircular<Estudiante> listaEspera; // Lista de espera (doble circular)
    private ListaSimpleCircular<Estudiante> roles;  // Lista circular de roles (líderes/tutores)

    /**
     * Constructor que inicializa el curso con clave y nombre.
     * Inicializa las listas de inscritos, espera y roles.
     * @param clave Clave única del curso.
     * @param nombre Nombre del curso.
     */
    public Curso(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
        this.inscritos = new ListaSimple<>();
        this.listaEspera = new ListaDobleCircular<>();
        this.roles = new ListaSimpleCircular<>();
    }

    // GETTERS Y SETTERS
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public ListaSimple<Estudiante> getInscritos() {
        return inscritos;
    }

    public void setInscritos(ListaSimple<Estudiante> inscritos) {
        this.inscritos = inscritos;
    }

    public ListaDobleCircular<Estudiante> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(ListaDobleCircular<Estudiante> listaEspera) {
        this.listaEspera = listaEspera;
    }

    public ListaSimpleCircular<Estudiante> getRoles() {
        return roles;
    }

    public void setRoles(ListaSimpleCircular<Estudiante> roles) {
        this.roles = roles;
    }


    /**
     * Rota el rol de líder/tutor dentro del curso.
     * La lista circular se encarga de mover el puntero automáticamente.
     * @return Estudiante que recibe el rol actual o null si no hay estudiantes con rol.
     */
    public Estudiante rotarRol() {
        if (roles.estaVacia()) {
            System.out.println("No hay estudiantes con rol asignado en este curso.");
            return null;
        }

        Estudiante nuevo = roles.rotarRol();
        System.out.println("Nuevo tutor/líder: " + nuevo.getNombre());
        return nuevo;
    }

    /**
     * Devuelve una representación legible del curso.
     * @return Nombre del curso y clave.
     */
    @Override
    public String toString() {
        return nombre + " (" + clave + ")";
    }
}