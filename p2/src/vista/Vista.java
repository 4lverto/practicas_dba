
package vista;

import modelo.Entorno;

/**
 * @brief Clase abstracta que representa una vista del simulador.
 */
public abstract class Vista {
    /**
     * @brief Instancia con el estado del entorno para poder observarlo (patrón 
     * Observer).
     */
    protected Entorno entorno;
    
    
    
    /**
     * @brief Actualiza la vista en función del estado del entorno.
     */
    public abstract void actualizar();
}
