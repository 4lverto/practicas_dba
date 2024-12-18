package modelo;

/**
 * @class Posicion
 *
 * @brief Clase que representa una posición en el mapa.
 *
 * Representa una ubicación específica en el mapa mediante coordenadas (x,y).
 */
public class Posicion {

    /**
     * @brief Coordenada x de la posición.
     */
    private int x;

    /**
     * @brief Coordenada y de la posición.
     */
    private int y;

    /**
     * @brief Constructor que inicializa la posición con las coordenadas dadas.
     *
     * @param x Coordenada x de la posición.
     * @param y Coordenada y de la posición.
     */
    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @brief Actualiza las coordenadas de la posición.
     *
     * @param x Nueva coordenada x.
     * @param y Nueva coordenada y.
     */
    public void actualizar(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @brief Obtiene la coordenada x de la posición.
     *
     * @return Coordenada x de la posición.
     */
    public int obtenerX() {
        return x;
    }

    /**
     * @brief Obtiene la coordenada y de la posición.
     *
     * @return Coordenada y de la posición.
     */
    public int obtenerY() {
        return y;
    }

    /**
     * @brief Comprueba si esta posición es igual a otra posición dada.
     *
     * @param otra La otra posición a comparar.
     * @return true si las posiciones son iguales, false en caso contrario.
     */
    public boolean sonIguales(Posicion otra) {
        return this.x == otra.x && this.y == otra.y;
    }

    @Override
    public String toString() {
        return "(" + obtenerX() + "," + obtenerY() + ")";
    }
}
