package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;
import modelo.sensores.Vision;

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

        // Agregar comportamiento cíclico para el movimiento del agente con intervalo de 1 ms
        addBehaviour(new TickerBehaviour(this, 1750) {
            @Override
            protected void onTick() {
                if (!objetivoAlcanzado()) {
                    decidirMovimiento();
                } else {
                    System.out.println("¡Objetivo alcanzado!");
                    myAgent.doDelete();  // Terminar agente una vez alcanzado el objetivo
                }
                actualizarMemoria();
            }
        });
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
            new Posicion(posActual.obtenerX(), posActual.obtenerY() - 1),   // Izquierda
            new Posicion(posActual.obtenerX() - 1, posActual.obtenerY() - 1),   // Diagonal Arriba Izquierda
            new Posicion(posActual.obtenerX() + 1, posActual.obtenerY() - 1),   // Diagonal Arriba Derecha
            new Posicion(posActual.obtenerX() - 1, posActual.obtenerY() + 1),   // Diagonal Abajo Izquierda
            new Posicion(posActual.obtenerX() + 1, posActual.obtenerY() + 1),   // Diagonal Abajo Derecha
        };

        Posicion mejorMovimiento = null;
        int mejorDistancia = Integer.MAX_VALUE;

        for (Posicion movimiento : movimientos) {
            if (entorno.posEsValida(movimiento)) {
                if (movimiento.sonIguales(posObjetivo)) {
                    mejorMovimiento = movimiento;
                    break;  
                }

                // Calcular la distancia de Manhattan y seleccionar el mejor movimiento
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
     * @class actualizarMemoria
     * @brief Cada vez que se llama se actualiza la memoria poniendo
     * 1: obstaculos
     * 0: libres
     */
    public void actualizarMemoria() {
        Vision vision = new Vision(entorno);

        // Llamar al método actualizar de la clase Vision
        vision.actualizar();

        // Obtener la matriz de celdas contiguas visibles
        int[][] celdasVisibles = vision.obtenerVision();

        // Mostrar la matriz de celdas contiguas (por ejemplo, con un ciclo para imprimirla)
        for (int i = 0; i < celdasVisibles.length; i++) {
            for (int j = 0; j < celdasVisibles[i].length; j++) {
                
                System.out.print(celdasVisibles[i][j] + " ");
            }
            System.out.println();  // Nueva línea después de cada fila
        }

    }
}
