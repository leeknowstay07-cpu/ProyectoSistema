package modelos;

public class Accion {

    public enum Tipo {
        REGISTRO,
        INSCRIPCION,
        BAJA,
        CALIFICACION
    }

    private Tipo tipo;
    private String matricula;
    private String curso;
    private Double calificacionAnterior;

    public Accion(Tipo tipo, String matricula, String curso, Double calificacionAnterior) {
        this.tipo = tipo;
        this.matricula = matricula;
        this.curso = curso;
        this.calificacionAnterior = calificacionAnterior;
    }

    public Tipo getTipo() {
        return tipo; 
    }
    
    public String getMatricula() {
        return matricula; 
    }
    
    public String getCurso() {
        return curso; 
    }
    
    public Double getCalificacionAnterior() {
        return calificacionAnterior; 
    }
}