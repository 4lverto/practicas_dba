package modelo;

/**
 * @class Energia
 * 
 * @brief Clase que representa el sensor de Energía. Este sensor cuenta los pasos del agente.
 */


public class Energia extends Sensor{
    
    /**
     * @brief Número de pasos realizados, representando el consumo de energía.
     */
    private int pasos;
    
    /**
     * @brief Constructor de Energia.
     */
    public Energia() {
        this.pasos = 0;
    
    }
    
    /**
     * @brief Actualiza el sensor de energía aumentando el contador de pasos.
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
    public int obtenerEerngia(){
        return this.pasos;
    } 
    
}
