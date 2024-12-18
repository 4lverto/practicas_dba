package modelo.agentes.santaclaus;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;

import modelo.Entorno;
import modelo.Posicion;

/**
 *
 * @author migue-maca
 */
public class SantaClaus extends Agent {


    protected String mensaje = "";

    protected boolean aceptado = false;
    
    protected ACLMessage mensajeAgente;
    
    protected Entorno entorno;
    
    protected Posicion posSantaClaus;     

    
    @Override
    protected void setup() {
        
        Object[] args = getArguments();
        
        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            entorno = (Entorno) args[0]; // Asigamos a entorno
            this.posSantaClaus = entorno.obtenerPosSantaClaus();

        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }

        SequentialBehaviour comportamientos = new SequentialBehaviour();

        comportamientos.addSubBehaviour(new EvaluarMision(this));
        comportamientos.addSubBehaviour(new SolicitarTraduccion("Traducion Santa", this));
        comportamientos.addSubBehaviour(new EnviarEvaluacion(this));
        comportamientos.addSubBehaviour(new DesvelarPosicion(this));
        comportamientos.addSubBehaviour(new Despedida(this));

        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        addBehaviour(comportamientos);
    }
}
