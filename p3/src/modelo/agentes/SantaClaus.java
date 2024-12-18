package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.Entorno;
import modelo.Mapa;
import modelo.Posicion;
import modelo.comportamientos.santaclaus.SantaSolicitarTraduccion;
import modelo.comportamientos.santaclaus.EvaluarMision;
import modelo.comportamientos.santaclaus.EnviarEvaluacion;
import modelo.comportamientos.santaclaus.DesvelarPosicion;

/**
 *
 * @author migue-maca
 */
public class SantaClaus extends Agent {

    private String mensajeTraducido = "test";
    private boolean aceptado = false;
    ACLMessage mensajeAgente;
    
    private Entorno entorno;
    
    Posicion posSantaClaus;     

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
    
    public Posicion obtenerPosicionSantaClaus(){
        return this.posSantaClaus;
    }
    
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
        comportamientos.addSubBehaviour(new SantaSolicitarTraduccion("Traducion Santa", this));
        comportamientos.addSubBehaviour(new EnviarEvaluacion(this));
        comportamientos.addSubBehaviour(new DesvelarPosicion(this));

        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        addBehaviour(comportamientos);
    }

    @Override
    protected void takeDown() {
        System.out.println("Finalizado el agente " + this.getLocalName());
    }

}
