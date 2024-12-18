package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.Entorno;
import modelo.Posicion;
import modelo.comportamientos.rudolph.EvaluarClave;
import modelo.comportamientos.rudolph.DevolverReno;

/**
 *
 * @author migue-maca
 */
public class Rudolph extends Agent {

    private Posicion[] renos;
    public int renosEncontrados=1;
    
    public int getRenosEncontrados(){
        return renosEncontrados;
    }
    
    public void siguienteReno(){
        renosEncontrados++;
    }

    @Override
    protected void setup() {
        Object[] args = getArguments();

        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            Entorno entorno = (Entorno) args[0];

            renos = new Posicion[8];

            renos[0] = entorno.obtenerPosReno1();
            renos[1] = entorno.obtenerPosReno2();
            renos[2] = entorno.obtenerPosReno3();
            renos[3] = entorno.obtenerPosReno4();
            renos[4] = entorno.obtenerPosReno5();
            renos[5] = entorno.obtenerPosReno6();
            renos[6] = entorno.obtenerPosReno7();
            renos[7] = entorno.obtenerPosReno8();
        }
        
        SequentialBehaviour comportamientos = new SequentialBehaviour();

        comportamientos.addSubBehaviour(new EvaluarClave(this));
        comportamientos.addSubBehaviour(new DevolverReno(renos, this));

        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        addBehaviour(comportamientos);
    }

    @Override
    protected void takeDown() {
        System.out.println("\n\tFinalizado el agente " + this.getLocalName());
    }
}