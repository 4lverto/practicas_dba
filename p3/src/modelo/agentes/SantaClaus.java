package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.Posicion;
import modelo.comportamientos.santaclaus.DesvelarPosicion;
import modelo.comportamientos.santaclaus.SolicitarTraduccion;
import modelo.comportamientos.santaclaus.EvaluarMision;
import modelo.comportamientos.santaclaus.EnviarEvaluacion;

/**
 *
 * @author migue-maca
 */
public class SantaClaus extends Agent {

    private String mensaje = "test";
    private boolean aceptado = false;
    ACLMessage mensajeAgente;
    Posicion posSantaClaus; 

    public void establecerMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String obtenerMensaje() {
        return (this.mensaje);
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
    
    public Posicion obtenerPosicionSantaClaus(){
        return this.posSantaClaus;
    }

    @Override
    protected void setup() {
        SequentialBehaviour comportamientos = new SequentialBehaviour();

        comportamientos.addSubBehaviour(new EvaluarMision(this));
        comportamientos.addSubBehaviour(new SolicitarTraduccion("Traducion Santa", this));
        comportamientos.addSubBehaviour(new EnviarEvaluacion(this));
        comportamientos.addSubBehaviour(new DesvelarPosicion(this));

        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        addBehaviour(comportamientos);
    }

    @Override
    protected void takeDown() {
        System.out.println("\n\tFinalizado el agente " + this.getLocalName());
    }

}
