
package modelo.agentes;

import java.io.IOException;
import modelo.Entorno;
import modelo.Posicion;

/**
 * @brief Clase que representa nuestro agente.
 */
public class Agente {
    
    /**
     * @brief Posición del agente.
     */
    private Posicion posAgente;
    
    /**
     * @brief Posición de la casilla objetivo.
     */
    private final Posicion posObjetivo;

    
    
    /**
     * @brief Constructor por parámetros.
     * 
     * @param posAgente Posición inicial del agente.
     * @param posObjetivo Posición inicial de la casilla objetivo.
     */
    public Agente(
            Posicion posAgente, 
            Posicion posObjetivo) throws IOException {
        
        this.posAgente   = posAgente;
        this.posObjetivo = posObjetivo;
    }
    
    /**
     * @brief Devuelve si el agente ha llegado a la casilla objetivo.
     * 
     * @return 'true' si el agente está sobre la casilla objetivo; 'false' en 
     * otro caso.
     */
    public boolean objetivoAlcanzado() {
        return (posAgente.sonIguales(posObjetivo));
    }
}
