
package vista;

import modelo.Entorno;
import modelo.Mapa;

/**
 * @class VistaTexto
 * 
 * @brief Vista textual del simulador.
 */
public class VistaTexto extends Vista {

    /**
     * Constructor por parámetros.
     * 
     * @param entorno Instancia del entorno.
     */
    public VistaTexto(Entorno entorno) {
        this.entorno = entorno;
        
        // Añadirse al entorno (observado) como observador:
        this.entorno.registrarVista(this);
        actualizar(); // Para que se muestre desde el principio.
    }

    /**
     * @brief Actualiza la vista en función del estado del entorno.
     * Implementación del método abstracto de Vista.
     */
    @Override
    public void actualizar() {
        Mapa mapa = this.entorno.obtenerMapa();
        
//        for (int i = 0; i < mapa.obtenerNumFilas(); i++) {
//            for (int j = 0; j < mapa.obtenerNumColumnas(); j++) {
//                System.out.print(mapa.obtenerCasilla(i, j) + "\t");
//            }
//            
//            // Para separar entre filas:
//            System.out.println();
//        }
    }
}
