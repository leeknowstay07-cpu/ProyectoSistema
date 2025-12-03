package estructuras;

/**
 * Clase que implementa un arreglo dinámico basado en datos tipo double 
 * (para manejar las calificaciones de un alumno)
 * 
 * @author Paulina, Alec, Sarah
 */
public class VectorDinamico {

    private double[] datos;    // almacenamiento interno
    private int tamanio;       // cantidad actual de elementos

    /**
     * Constructor por defecto.
     */
    public VectorDinamico() {
        this(4); // capacidad inicial de 4 si no se le asigna nada
    }

    /**
     * Constructor con capacidad inicial.
     * @param capacidadInicial
     */
    public VectorDinamico(int capacidadInicial) {
        if (capacidadInicial < 1) {
            capacidadInicial = 4;
        }
        datos = new double[capacidadInicial];
        tamanio = 0;
    }

    /**
     * Agregar un valor al final del vector
     * @param valor
     */
    public void agregar(double valor) {
        // si está lleno se redimensiona
        if (tamanio == datos.length) {
            redimensionar();
        }
        // se agrega el valor al final y se incrementa tamanio
        datos[tamanio++] = valor;
    }

    /**
     * Obtener un valor en un índice
     * @param indice
     * @return 
     */
    public double obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        return datos[indice];
    }

    /**
     * Actualizar un valor existente
     */
    public void actualizar(int indice, double valor) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        datos[indice] = valor;
    }

    /**
     * Tamaño actual del vector
     */
    public int tamanio() {
        return tamanio;
    }

    /**
     * Eliminar el último elemento (para deshacer acciones).
     * @return 
     */
    public double eliminarUltimo() {
        if (tamanio == 0) {
            throw new IllegalStateException("Vector vacío");
        }
        return datos[--tamanio];
    }

    /**
     * Redimensionar el arreglo al doble del tamaño
     */
    private void redimensionar() {
        double[] nuevo = new double[datos.length * 2];
        // copia los valores al arreglo redimensionado
        for (int i = 0; i < tamanio; i++) {
            nuevo[i] = datos[i];
        }
        datos = nuevo;
    }

    /**
     * Calcular
     * @return  el promedio de forma recursiva.
     */
    public double promedio() {
        if (tamanio == 0) {
            return 0;
        }
        return sumaRecursiva(0) / tamanio;
    }

    /**
     * Suma recursiva de elementos.
     */
    private double sumaRecursiva(int indice) {
        if (indice == tamanio) {
            return 0; // caso base
        }
        return datos[indice] + sumaRecursiva(indice + 1);
    }

    public double ultimoValor() {
        return datos[tamanio - 1];
    }

    public void reemplazarUltimo(double valor) {
        datos[tamanio - 1] = valor;
    }
}