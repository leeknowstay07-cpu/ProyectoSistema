package modelos;

/**
 * Clase que representa una solicitud de calificación (nueva o modificación).
 */
public class SolicitudCalificacion {
    
    // Tipo de solicitud
    public enum Tipo {
        NUEVA, MODIFICAR 
    }

    private String matricula;
    private String claveCurso; 
    private Tipo tipo;
    private int indice; // índice a modificar (si corresponde), para NUEVA se puede ignorar
    private double nuevaCalificacion;

    public SolicitudCalificacion(String matricula, String claveCurso, Tipo tipo, int indice, double nuevaCalificacion) {
        this.matricula = matricula;
        this.claveCurso = claveCurso;
        this.tipo = tipo;
        this.indice = indice;
        this.nuevaCalificacion = nuevaCalificacion;
    }

    public String getMatricula() {
        return matricula; 
    }
    
    public String getClaveCurso() {
        return claveCurso; 
    }
    
    public Tipo getTipo() {
        return tipo; 
    }
    
    public int getIndice() {
        return indice; 
    }
    
    public double getNuevaCalificacion() {
        return nuevaCalificacion; 
    }

    @Override
    public String toString() {
        return "SolicitudCalificacion{" +
                "matricula='" + matricula + '\'' +
                ", curso='" + claveCurso + '\'' +
                ", tipo=" + tipo +
                ", indice=" + indice +
                ", nuevaCalificacion=" + nuevaCalificacion +
                '}';
    }
}