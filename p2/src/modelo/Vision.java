package modelo;

/**
 * @class Vision
 * 
 * @brief Clase que representa un sensor de visión.
 *   Este sensor obtiene las celdas contiguas visibles alrededor del agente.
 */
public class Vision extends Sensor {
    
    /**
     * Matriz de enteros que representa las celdas contiguas visibles.
     */
    private int[][] celdasContiguas;

    /**
     * Constructor de Vision, inicializa la matriz de celdas contiguas.
     */
    public Vision() {
        // Inicializar la matriz de celdas contiguas según el entorno o el tamaño que desees.
        this.celdasContiguas = new int[3][3]; // Por ejemplo, una visión de 3x3 alrededor del agente.
        
    }

    /**
     * Actualiza el sensor de visión. (En una implementación real, este método
     * debería llenar celdasContiguas con los datos de las celdas visibles).
     */
    @Override
    public void actualizar() {
        // Aquí se podría implementar la lógica para actualizar las celdas visibles
        // alrededor de la posición del agente.
        
    }

    /**
     * Obtiene la matriz de celdas contiguas visibles.
     * 
     * @return Matriz de celdas contiguas visibles alrededor del agente.
     */
    public int[][] obtenerVision() {
        return celdasContiguas;
    }
}
