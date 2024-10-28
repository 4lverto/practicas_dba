package modelo;

/**
 * @class Sensor
 * 
 * @brief Clase abstracta que representa un sensor en el entorno.
 * Los sensores específicos deben implementar el método actualizar.
 */
public abstract class Sensor {

    /**
     * @brief Método abstracto para actualizar la información captada por el sensor.
     */
    public abstract void actualizar();
}
