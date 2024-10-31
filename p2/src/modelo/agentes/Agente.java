
package modelo.agentes;

import java.io.IOException;
import modelo.Entorno;
import modelo.Posicion;

/**
 * @brief Clase que representa nuestro agente.
 */
public class Agente {
    /**
     * @brief Entorno de la simulación con el que interactuará el agente para 
     * desplazarse por el mapa.
     */
    private Entorno entorno;
    
    /**
     * @brief Posición del agente.
     */
    private Posicion posAgente;
    
    /**
     * @brief Posición de la casilla objetivo.
     */
    private Posicion posObjetivo;

    
    
    /**
     * @brief Constructor por parámetros.
     * 
     * @param posAgente Posición inicial del agente.
     * @param posObjetivo Posición inicial de la casilla objetivo.
     */
    public Agente(
            Posicion posAgente, 
            Posicion posObjetivo,
            String nombreFicheroMapa) throws IOException {
        
        this.posAgente   = posAgente;
        this.posObjetivo = posObjetivo;
        
        // Crear el entorno:
        entorno = Entorno.obtenerInstancia(
                nombreFicheroMapa, 
                posAgente, 
                posObjetivo);
    }
    
    /**
     * @brief Consultor del entorno.
     * 
     * @return Instancia con el entorno actual.
     */
    public Entorno obtenerEntorno() {
        return (entorno);
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
