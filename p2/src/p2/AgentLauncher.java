package p2;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 * 
 * Ejecuta un agente de forma fácil y rápida, indicando como segundo 
 * parámetro de 'createNewAgent' la clase asociada al agente que se quiere
 * ejecutar
 * 
 * @author migue-maca
 */
public class AgentLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Obtener la instancia del entorno de ejecución:
        Runtime rt = Runtime.instance();
        
        // Configurar el perfil para el contenedor principal:
        Profile p  = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "1099");
        
        // Crear el contenedor de agentes:
        ContainerController cc = rt.createMainContainer(p);
        
        try {
            // Crear y lanzar un agente:
            AgentController ac = cc.createNewAgent(
                            "Agent", 
                            "p2.BasicAgent", 
                            null);
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
    
}