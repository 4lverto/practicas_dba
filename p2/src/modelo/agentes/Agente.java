package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;

public class Agente extends Agent {
    private Entorno entorno;
    private ArrayList<Sensor> sensores;

    @Override
    protected void setup() {
        this.sensores = new ArrayList<>();
        
        Object[] args = getArguments();
        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            entorno = (Entorno) args[0];
        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }
        
        // Agregar comportamiento cíclico para el movimiento del agente
        addBehaviour(new MovimientoCiclico());
    }

    public boolean objetivoAlcanzado() {
        return entorno.obtenerPosAgente().sonIguales(entorno.obtenerPosObjetivo());
    }

    public void decidirMovimiento() {
        Posicion posActual = entorno.obtenerPosAgente();
        Posicion posObjetivo = entorno.obtenerPosObjetivo();
        
        Posicion[] movimientos = {
            new Posicion(posActual.obtenerX() + 1, posActual.obtenerY()),  // Abajo
            new Posicion(posActual.obtenerX() - 1, posActual.obtenerY()),  // Arriba
            new Posicion(posActual.obtenerX(), posActual.obtenerY() + 1),  // Derecha
            new Posicion(posActual.obtenerX(), posActual.obtenerY() - 1)   // Izquierda
        };
        
        Posicion mejorMovimiento = null;
        int mejorDistancia = Integer.MAX_VALUE;

        for (Posicion movimiento : movimientos) {
            if (entorno.posEsValida(movimiento)) {
                int distancia = calcularDistanciaManhattan(movimiento, posObjetivo);
                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorMovimiento = movimiento;
                }
            }
        }

        if (mejorMovimiento != null) {
            entorno.actualizarPercepciones(mejorMovimiento);
        }
    }

    private int calcularDistanciaManhattan(Posicion p1, Posicion p2) {
        return Math.abs(p1.obtenerX() - p2.obtenerX()) + Math.abs(p1.obtenerY() - p2.obtenerY());
    }

    /**
     * @class MovimientoCiclico
     * @brief Comportamiento cíclico que permite al agente decidir y ejecutar su movimiento en cada iteración.
     */
    private class MovimientoCiclico extends CyclicBehaviour {
        @Override
        public void action() {
            if (!objetivoAlcanzado()) {
                decidirMovimiento();
            } else {
                System.out.println("¡Objetivo alcanzado!");
                myAgent.doDelete();  // Terminar agente una vez alcanzado el objetivo
            }
        }
    }
}
