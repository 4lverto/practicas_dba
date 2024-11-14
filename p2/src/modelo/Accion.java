package modelo;

/**
 * @brief Enumerado con los tipos de acciones de las que dispondrá el agente.
 */
public enum Accion {
    ARRIBA(-1, 0),
    ABAJO(1, 0),
    IZQUIERDA(0, -1),
    DERECHA(0, 1),
    DIAGONAL_ARRIBA_DERECHA(-1, 1),
    DIAGONAL_ARRIBA_IZQUIERDA(-1, -1),
    DIAGONAL_ABAJO_DERECHA(1, 1),
    DIAGONAL_ABAJO_IZQUIERDA(1, -1);

    private final int deltaX;
    private final int deltaY;

    // Constructor para definir los cambios en las coordenadas
    Accion(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * @brief Devuelve el delta de movimiento asociado a la acción.
     * 
     * @return Una posición que representa el cambio en las coordenadas (X, Y).
     */
    public Posicion obtenerDelta() {
        return new Posicion(this.deltaX, this.deltaY);
    }
}
