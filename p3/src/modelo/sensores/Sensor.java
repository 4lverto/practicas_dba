package modelo.sensores;

import modelo.Entorno;

/**
 * @class Sensor
 *
 * @brief Clase abstracta que representa un sensor en el entorno. Los sensores
 * específicos deben implementar el método actualizar.
 */
public abstract class Sensor {

    /**
     * @brief Instancia con el estado del entorno para poder observarlo (patrón
     * Observer).
     *
     * Cada sensor tiene una referencia al entorno en el que opera,
     * permitiéndole acceder a la información necesaria para detectar cambios y
     * actualizaciones en el estado
     */
    protected Entorno entorno;

    /**
     * @brief Método abstracto para actualizar la información captada por el
     * sensor.
     *
     * Deberá ser implementado por cada tipo específico de sensor
     */
    public abstract void actualizar();

}
