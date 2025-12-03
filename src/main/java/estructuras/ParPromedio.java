package estructuras;

import modelos.Estudiante;

/**
 * Contenedor para almacenar (promedio, referencia a Estudiante).
 * Comparable por promedio ascendente; desempata por matrícula para orden consistente.
 */
public class ParPromedio implements Comparable<ParPromedio> {
    private double promedio;
    private Estudiante est;

    public ParPromedio(double promedio, Estudiante est) {
        this.promedio = promedio;
        this.est = est;
    }

    public double getPromedio() {
        return promedio;
    }

    public Estudiante getEstudiante() {
        return est;
    }

    @Override
    public int compareTo(ParPromedio o) {
        int cmp = Double.compare(this.promedio, o.promedio);
        if (cmp != 0) return cmp;

        // desempate por matrícula (asegura orden estable)
        if (this.est != null && o.est != null && this.est.getMatricula() != null && o.est.getMatricula() != null) {
            return this.est.getMatricula().compareTo(o.est.getMatricula());
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f -> %s", promedio, est == null ? "null" : est.getMatricula());
    }
}