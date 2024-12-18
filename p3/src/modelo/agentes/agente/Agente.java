package modelo.agentes.agente;

import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;
import modelo.Mapa;

;

/**
 * @brief Clase que representa al agente autónomo que se desplaza en el entorno.
 *
 * La clase Agente extiende la clase Agent de JADE y gestiona el ciclo de vida
 * del agente, incluyendo la configuración inicial, la toma de decisiones y el
 * seguimiento de su entorno. Emplea un comportamiento periódico para evaluar
 * contínuamente su posición y actuar en consecuencia.
 */
public class Agente extends Agent {

    /**
     * @brief Entorno en el que se mueve el agente
     */
    protected Entorno entorno;

    /**
     * @brief Lista de sensores asociados al agente
     */
    protected ArrayList<Sensor> sensores;

    /**
     * @brief Mapa que almacena la memoria del agente sobre el entorno
     */
    protected Mapa mapaMemoria;

    // Para la comunicación (falta documentar):
    protected String codigoSecreto = "";
    protected String mensaje = "Bro Estoy dispuesto a ofrecerme voluntario para la misión En Plan";
    protected Posicion posObjetivo;
    protected ACLMessage mensajeSanta;
    protected ACLMessage mensajeRudolph;

    /**
     * @brief Lista de posiciones por las que ha pasado el agente
     */
    // private ArrayList<Posicion> trazaRecorrido;
    /**
     * @brief Métood de configuración incial del agente, llamado al inicio.
     *
     * Este método recibe el entorno como argumento, inicializa el mapa de
     * memoria y los sensores. Además, configura un comportamiento periódico que
     * permite al agente evaluar su situación y decidir el próximo movimiento.
     */
    @Override
    protected void setup() {

        System.out.println("Iniciando AgenteBuscador...");

        // Inicializar el entorno:
        Object[] args = getArguments();

        // El único argumento que se espera recibir es la instancia de Entorno:
        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            entorno = (Entorno) args[0]; // Asigamos a entorno
            this.mapaMemoria = new Mapa(100, 100); // Inicializamos la memoria
            // Configuramos el estado inicial llamando a 
            // actualizarPercepciones(posición actual)
            this.sensores = this.entorno.actualizarPercepciones(entorno.obtenerPosAgente());

            // Inicializamos la traza de recorrido
            //trazaRecorrido = new ArrayList<>();
        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }

        // Añadir los comportamientos de los que dispone el agente para que los 
        // lleve a cabo:
        //addBehaviour(new ActualizarMemoria(this));        
        //addBehaviour(new DecidirMovimiento(this));
        //addBehaviour(new PasosTotales(this));
        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        SequentialBehaviour comportamientos = new SequentialBehaviour();

        comportamientos.addSubBehaviour(new SolicitarTraduccion("Traduccion inicial", this));
        comportamientos.addSubBehaviour(new ProponerMisionSanta(this));
        comportamientos.addSubBehaviour(new EstablecerCanalSeguroRudolph(this));

        FSMBehaviour fsm = new FSMBehaviour(this);
        fsm.registerFirstState(new SolicitarReno(this), "Solicitar");
        fsm.registerState(new ActualizarMemoria(this), "Actualizar");
        fsm.registerState(new DecidirMovimiento(this), "Mover");
        fsm.registerLastState(new OneShotBehaviour(this) {
            @Override
            public void action() {
                System.out.println("\n\tTodos los renos han sido rescatados\n");
                mensaje = "Bro Donde estas? En Plan";
            }
        }, "Fin");
        fsm.registerDefaultTransition("Solicitar", "Actualizar");
        fsm.registerDefaultTransition("Actualizar", "Mover");
        fsm.registerDefaultTransition("Mover", "Actualizar");
        fsm.registerTransition("Mover", "Solicitar", 1);
        fsm.registerTransition("Solicitar", "Fin", 1);

        comportamientos.addSubBehaviour(fsm);
        
        
        comportamientos.addSubBehaviour(new SolicitarTraduccion("Traduccion-solicitud-coordenadas", this));
        comportamientos.addSubBehaviour(new SolicitarPosicionSanta(this));

        FSMBehaviour fsmSanta = new FSMBehaviour(this);
        fsmSanta.registerFirstState(new ActualizarMemoria(this), "Actualizar");
        fsmSanta.registerState(new DecidirMovimiento(this), "Mover");
        fsmSanta.registerLastState(new OneShotBehaviour(this) {
            @Override
            public void action() {
                mensaje = "Bro Ya llegue En Plan";
                System.out.println("\n\tAgente ha llegado a Santa Claus\n");
            }
        }, "Fin");

        fsmSanta.registerDefaultTransition("Actualizar", "Mover");
        fsmSanta.registerDefaultTransition("Mover", "Actualizar");
        fsmSanta.registerTransition("Mover", "Fin", 1);

        comportamientos.addSubBehaviour(fsmSanta);

        comportamientos.addSubBehaviour(new SolicitarTraduccion("Traduccion-confirmacion-llegada", this));
        comportamientos.addSubBehaviour(new DespedidaSanta(this));

        comportamientos.addSubBehaviour(new PasosTotales(this));

        addBehaviour(comportamientos);
    }
}
