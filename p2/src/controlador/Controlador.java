
package controlador;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import modelo.Entorno;


/**
 * @brief Clase que representa el controlador del simulador. 
 * 
 * 
 * Permite la creación, configuración y lanzamiento del agente en un entorno 
 * JADE. Configura el perfil de JADE y gestiona el ciclo de vida del agente.
 */
public class Controlador {
    
    /**
     * @brief Controlador para la creación y el lanzamiento del agente.
     */
    private AgentController controlador;
    
    /**
     * @brief Constructor por parámetros.
     * 
     * @param nombreAgente Nombre que se asignará al agente.
     * @param agente Agente para la simulación (nombre de la clase del agente,
     * teniendo en cuenta el/los paquete/s en el/los que esta se encuentra).
     * @param entorno Instancia del entorno para pasársela al agente de cara a 
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
        p.setParameter(Profile.GUI, "false");
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
