package modelo.agentes.rudolph;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import modelo.Entorno;
import modelo.Posicion;

/**
 *
 * @author migue-maca
 */
public class Rudolph extends Agent {

    protected Posicion[] renos;

    @Override
    protected void setup() {
        Object[] args = getArguments();

        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            Entorno entorno = (Entorno) args[0];

            renos = new Posicion[8];

            renos = entorno.obtenerPosReno();
        }
        
        SequentialBehaviour comportamientos = new SequentialBehaviour();

        comportamientos.addSubBehaviour(new EvaluarClave(this));
        comportamientos.addSubBehaviour(new DevolverReno(this));

        // Iniciar el flujo de comunicación (por ahora lo he puesto aquí):
        addBehaviour(comportamientos);
    }
}