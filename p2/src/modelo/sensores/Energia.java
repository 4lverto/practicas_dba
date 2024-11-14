package modelo.sensores;

import modelo.Entorno;

/**
 * @class Energia
 * 
 * @brief Clase que representa el sensor de Energía. Este sensor cuenta los 
 * pasos del agente.
 */
public class Energia extends Sensor{
    
    /**
     * @brief Número de pasos realizados, representando el consumo de energía.
     */
    private int pasos;
    
    /**
     * @brief Constructor de Energia.
     * 
     * @param entorno Instancia del entorno.
     */
    public Energia(Entorno entorno) {
        this.pasos   = 0;
        this.entorno = entorno;
        
        // Añadirse al entorno (observado) como observador:
        this.entorno.registrarSensor(this);
    }
    
    /**
     * @brief Actualiza el sensor de energía aumentando el contador de pasos.
     * 
     * Se llamará cada vez que el agente se mueva
     */
    @Override
    public void actualizar(){
        this.pasos++;
    }
    
    /**
     * @brief Obtiene el número actual de pasos, que representa la energía consumida.
     * 
     * @return Número de pasos realizados.
     */
    public int obtenerEnergia(){
        return this.pasos;
    } 
    
}
