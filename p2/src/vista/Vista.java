
package vista;

import modelo.Mapa;

/**
 * @brief Interfaz que representa una vista del simulador.
 */
public interface Vista {
    /**
     * @brief Actualiza la vista con el estado del mapa recibido.
     * 
     * @param mapa Mapa que visualizar.
     */
    public void actualizar(Mapa mapa);
}
