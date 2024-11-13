package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;
import modelo.Accion;
import modelo.Mapa;
import modelo.sensores.Vision;

public class Agente extends Agent {
    private Entorno entorno;
    private ArrayList<Sensor> sensores;
    private Mapa mapaMemoria;

    @Override
    protected void setup() {
        
        Object[] args = getArguments();
        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            entorno = (Entorno) args[0];
            this.mapaMemoria = entorno.obtenerMapa();
            this.sensores = entorno.actualizarPercepciones(entorno.obtenerPosAgente());
        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }

        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
                if (!objetivoAlcanzado()) {
                    actualizarMemoria();
                    decidirMovimiento();
                } else {
                    System.out.println("¡Objetivo alcanzado!");
                    myAgent.doDelete();
                }
            }
        });
    }

    public boolean objetivoAlcanzado() {
        return entorno.obtenerPosAgente().sonIguales(entorno.obtenerPosObjetivo());
    }

    public void decidirMovimiento() {
        Posicion posActual = entorno.obtenerPosAgente();
        Posicion posObjetivo = entorno.obtenerPosObjetivo();

        Map<Accion, Posicion> movimientos = new EnumMap<>(Accion.class);
        movimientos.put(Accion.ABAJO, new Posicion(1, 0));
        movimientos.put(Accion.ARRIBA, new Posicion(-1, 0));
        movimientos.put(Accion.DERECHA, new Posicion(0, 1));
        movimientos.put(Accion.IZQUIERDA, new Posicion(0, -1));
        movimientos.put(Accion.DIAGONAL_ARRIBA_DERECHA, new Posicion(-1, 1));
        movimientos.put(Accion.DIAGONAL_ARRIBA_IZQUIERDA, new Posicion(-1, -1));
        movimientos.put(Accion.DIAGONAL_ABAJO_DERECHA, new Posicion(1, 1));
        movimientos.put(Accion.DIAGONAL_ABAJO_IZQUIERDA, new Posicion(1, -1));

        Posicion mejorMovimiento = null;
        int mejorDistancia = Integer.MAX_VALUE;

        for (Map.Entry<Accion, Posicion> entry : movimientos.entrySet()) {
            Posicion delta = entry.getValue();
            Posicion movimiento = new Posicion(posActual.obtenerX() + delta.obtenerX(), posActual.obtenerY() + delta.obtenerY());

            if (entorno.posEsValida(movimiento)) {
                if (movimiento.sonIguales(posObjetivo)) {
                    mejorMovimiento = movimiento;
                    break;
                }

                int distancia = calcularDistanciaManhattan(movimiento, posObjetivo);
                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorMovimiento = movimiento;
                }
            }
        }

        if (mejorMovimiento != null) {
             this.sensores = entorno.actualizarPercepciones(mejorMovimiento);
    
            // Casteo de la primer entrada de la lista de sensores a Vision
            Vision visionSensor = (Vision) this.sensores.get(0);

            // Obtener la matriz de celdas contiguas
            int[][] celdasContiguas = visionSensor.obtenerVision();

            // Mostrar todas las celdas contiguas
            for (int i = 0; i < celdasContiguas.length; i++) {
                for (int j = 0; j < celdasContiguas[i].length; j++) {
                    // Mostrar el valor de cada celda de la matriz
                    System.out.print(celdasContiguas[i][j] + " ");
                }
                // Salto de línea para cada fila
                System.out.println();
            }
        }

    }

    private int calcularDistanciaManhattan(Posicion p1, Posicion p2) {
        return Math.abs(p1.obtenerX() - p2.obtenerX()) + Math.abs(p1.obtenerY() - p2.obtenerY());
    }

    public void actualizarMemoria() {
        //this.mapaMemoria.mostrarMapa();
    }
}
