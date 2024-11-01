
package controlador;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import modelo.agentes.Agente;
import vista.Vista;

/**
 * @brief Clase que representa el controlador del simulador. Esta se encargará 
 * de hacer de intermediaria entre el modelo y la vista (patrón MVC).
 */
public class Controlador {
    
    /**
     * @brief Controlador para la creación y el lanzamiento del agente.
     */
    private AgentController controlador;
    
    /**
     * @brief Agente de la simulación.
     */
    private Agente agente;
    
    /**
     * @brief Array con las vistas de las que dispone el simulador.
     */
    private ArrayList<Vista> vistas;

    
    
    /**
     * @brief Constructor por parámetros.
     * 
     * @param agente Agente para la simulación.
     * @param vistas Array con las vistas de las que dispondrá el simulador.
     */
    public Controlador(Agente agente, ArrayList<Vista> vistas) {
        if (agente == null) {
            throw new IllegalArgumentException("El agente no puede ser nulo");
        }
        this.agente = agente;
        this.vistas = (vistas != null) ? vistas : new ArrayList<>();
        configurarControlador();
    }


    
    /**
     * @brief Constructor por parámetros (prescinde de recibir un array con las 
     * vistas).
     * 
     * @param agente Agente para la simulación.
     */
    public Controlador(Agente agente) {
        if (agente == null) {
            throw new IllegalArgumentException("El agente no puede ser nulo");
        }
        this.agente = agente;
        this.vistas = new ArrayList<>();
        configurarControlador();
    }

    
    /**
     * @brief Registra una vista.
     * 
     * @param vista Vista a registrar.
     */
    public void registrarVista(Vista vista) {
        vistas.add(vista);
    }
    
    /**
     * @brief Elimina una vista.
     * 
     * @param vista Vista a eliminar.
     */
    public void eliminarVista(Vista vista) {
        vistas.remove(vista);
    }
    
    /**
     * @brief Notifica a todas las vistas del controlador que ha habido un 
     * cambio, haciendo que cada una de estas se actualice.
     */
    public void notificarVistas() {
        for (Vista v : vistas) {
            //v.actualizar(agente.obtenerEntorno().obtenerMapa());
        }
    }
    
    /**
     * @brief Inicia la simulación.
     */
    public void iniciarSimulacion() {
        System.out.println(
                "+++++++++++++++++++ Inicio de simulación +++++++++++++++++++");
    }
    
    
    /**
     * @brief Finaliza la simulación.
     */
    public void finalizarSimulacion() {
        System.out.println(
                "+++++++++++++++++++ Fin de simulación +++++++++++++++++++");
        // Desencadenar que las vistas actualicen su estado:
        notificarVistas();
    }
    
    /**
     * @brief Ejecuta un ciclo de simulación. En cada ciclo, hace que el agente
     * realice acciones por el mapa.
     */
public void ejecutar() {
    try {
        if (controlador != null) {
            controlador.start();
            notificarVistas();
        } else {
            System.out.println("El controlador no ha sido inicializado.");
        }
    } catch (StaleProxyException e) {
        e.printStackTrace();
    }
}

    
    /**
     * @brief Devuelve si el agente ha llegado a la casilla objetivo.
     * 
     * @return 'true' si el agente está sobre la casilla objetivo; 'false' en 
     * otro caso.
     */
    public boolean objetivoAlcanzado() {
        return (agente.objetivoAlcanzado());
    }
    
private void configurarControlador() {
    // Obtener la instancia del entorno de ejecución:
    Runtime rt = Runtime.instance();
    
    // Configurar el perfil para el contenedor principal:
    Profile p  = new ProfileImpl();
    p.setParameter(Profile.MAIN_HOST, "localhost");
    p.setParameter(Profile.CONTAINER_NAME, "1099");
    
    // Crear el contenedor de agentes:
    ContainerController cc = rt.createMainContainer(p);
    
    try {
        // Crear un agente:
        controlador = cc.createNewAgent(
                        "Agente", 
                        "modelo.agentes.Agente",
                        new Object[] { /* Aquí puedes pasar los parámetros necesarios si decides hacerlo */ });
    } catch (StaleProxyException e) {
        e.printStackTrace();
    }
}

}
