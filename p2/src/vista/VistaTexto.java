
package vista;

import modelo.Mapa;

/**
 * @class VistaTexto
 * 
 * @brief Vista textual del simulador.
 */
public class VistaTexto implements Vista {

    /**
     * @brief Actualiza la vista con el estado del mapa recibido.
     * Implementación del método de la interfaz Vista.
     * 
     * @param mapa Mapa que visualizar.
     */
    @Override
    public void actualizar(Mapa mapa) {
        for (int i = 0; i < mapa.obtenerNumFilas(); i++) {
            for (int j = 0; j < mapa.obtenerNumColumnas(); j++) {
                System.out.print(mapa.obtenerCasilla(i, j) + "\t");
            }
            
            // Para separar entre filas:
            System.out.println();
        }
    }
}
