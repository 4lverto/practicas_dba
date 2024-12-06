package tutorial_alberto;

import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Profile;
import jade.core.Runtime;
import jade.wrapper.AgentController;

import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import tutorial_alberto.EmisorAgent;
import tutorial_alberto.ReceptorAgent;

public class Tutorial_alberto {

    public static void main(String[] args) throws StaleProxyException {
        System.out.println("Hola mundo");
        
        // Obtener la instancia del entorno de ejecución:
        Runtime rt = Runtime.instance();
        
        // Configurar el perfil para el contenedor principal:
        Profile p  = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.CONTAINER_NAME, "1099");
        
        // Crear el contenedor de agentes:
        ContainerController cc = rt.createMainContainer(p);
        
        AgentController emisor = cc.createNewAgent("alberto-emisor", EmisorAgent.class.getCanonicalName(), null);
        emisor.start();
        
        AgentController receptor = cc.createNewAgent("alberto-receptor", ReceptorAgent.class.getCanonicalName(), null);
        receptor.start();
    }
}
