package modelos;

import estructuras.VectorDinamico;

/**
 * Clase que implementa un objeto tipo Estudiante
 *
 * @author Paulina, Alec, Sarah
 */
public class Estudiante implements Comparable<Estudiante> {

    private String matricula;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;

    // Estructura para calificaciones
    private VectorDinamico calificaciones;

    public Estudiante() {
        this.calificaciones = new VectorDinamico();
    }

    public Estudiante(String matricula, String nombre, String telefono, String correo, String direccion) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.calificaciones = new VectorDinamico();
    }

    // Getters
    public String getMatricula() {
        return matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public VectorDinamico getCalificaciones() {
        return calificaciones;
    }

    // Setters
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //  CALIFICACIONES
    /**
     * Agregar una nueva calificación
     *
     * @param nota
     */
    public void agregarCalificacion(double nota) {
        calificaciones.agregar(nota);
    }

    /**
     * Promedio delegando a VectorDinamico
     *
     * @return
     */
    public double promedio() {
        return calificaciones.promedio();
    }

    /**
     * Número de calificaciones
     *
     * @return
     */
    public int cantidadCalificaciones() {
        return calificaciones.tamanio();
    }

    //  COMPARACIÓN POR MATRÍCULA
    @Override
    public int compareTo(Estudiante o) {
        return this.matricula.compareToIgnoreCase(o.matricula);
    }

    // Modificar calificación en índice específico
    public void setCalificacionAt(int indice, double valor) {
        calificaciones.actualizar(indice, valor);
    }

    // Obtener calificación en índice
    public Double getCalificacionAt(int indice) {
        return calificaciones.obtener(indice);
    }

    // Eliminar última calificación
    public void removerUltimaCalificacion() {
        if (calificaciones.tamanio() > 0) {
            calificaciones.eliminarUltimo();
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + matricula + ")";
    }
}