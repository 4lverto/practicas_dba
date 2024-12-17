
package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.comportamientos.santaclaus.SantaSolicitarTraduccion;
import modelo.comportamientos.santaclaus.EvaluarMision;
import modelo.comportamientos.santaclaus.EnviarEvaluacion;

/**
 *
 * @author migue-maca
 */
public class SantaClaus extends Agent {
    
    private String mensajeTraducido = "test";
    private boolean aceptado = false;
    ACLMessage mensajeAgente;

    
    public void establecerMensajeTraducido(String mensaje) {
    this.mensajeTraducido = mensaje;
    }
    
    public String obtenerMensajeTraducido() {
        return (this.mensajeTraducido);
    }
    
    public void establecerAceptado(boolean bool) {
    this.aceptado = bool;
    }
    
    public boolean obtenerAceptado() {
        return (this.aceptado);
    }
    
    public void modificarMensajeAgente(ACLMessage mensajeAgente) {
        this.mensajeAgente = mensajeAgente;
    }
    
    public ACLMessage obtenerMensajeAgente() {
        return (this.mensajeAgente);
    }
    
    
    @Override
    protected void setup() {
        SequentialBehaviour comportamientos = new SequentialBehaviour();
        
        comportamientos.addSubBehaviour(new EvaluarMision(this));
        comportamientos.addSubBehaviour(new SantaSolicitarTraduccion("Traducion Santa", this));
        comportamientos.addSubBehaviour(new EnviarEvaluacion(this));
        
        
        
        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        addBehaviour(comportamientos);
    }

    @Override
    protected void takeDown() {
        System.out.println("Finalizado el agente " + this.getLocalName());
    }
    
}
