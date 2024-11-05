
package controlador;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import modelo.Entorno;


/**
 * @brief Clase que representa el controlador del simulador. Esta se encargará 
 * de abstraer todo el proceso, lanzando el agente y desencadenando las 
 * actualizaciones de las vistas (las cuales se hacen automáticamente cuando hay
 * un cambio importante en el entorno).
 */
public class Controlador {
    
    /**
     * @brief Controlador para la creación y el lanzamiento del agente.
     */
    private AgentController controlador;

    
    
    /**
     * @brief Constructor por parámetros.
     * 
     * @param nombreAgente Nombre que se asociará con el agente.
     * @param agente Agente para la simulación (nombre de la clase del agente,
     * teniendo en cuenta el/los paquete/s en el/los que esta se encuentra).
     * @param entorno Instancia del entorno para pasarla al agente de cara a 
     * iniciarlo.
     */
    public Controlador(
            String nombreAgente, 
            String agente, 
            Entorno entorno) {
        
        // Obtener la instancia del entorno de ejecución:
        Runtime rt = Runtime.instance();
        
        // Configurar el perfil para el contenedor principal:
        Profile p  = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "1099");
        
        // Crear el contenedor de agentes:
        ContainerController cc = rt.createMainContainer(p);
        
        try {
            // Crear el agente:
            controlador = cc.createNewAgent(
                            nombreAgente, 
                            agente,
                            new Object[] {entorno});
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @brief Inicia al agente.
     */
    public void ejecutar() {
        try {
            controlador.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
