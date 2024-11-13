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
            this.mapaMemoria = new Mapa(10, 10);
            this.sensores = this.entorno.actualizarPercepciones(entorno.obtenerPosAgente());
        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }

        addBehaviour(new TickerBehaviour(this, 2000) {
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

        // Movimientos posibles: abajo, arriba, derecha, izquierda y diagonales
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

        // Evaluar cada posible movimiento
        for (Map.Entry<Accion, Posicion> entry : movimientos.entrySet()) {
            Posicion delta = entry.getValue();
            Posicion movimiento = new Posicion(posActual.obtenerX() + delta.obtenerX(), posActual.obtenerY() + delta.obtenerY());

            // Verificar si el movimiento es válido (dentro de los límites del mapa y no es un obstáculo)
            if (entorno.posEsValida(movimiento)) {
                // Si el movimiento lleva directamente al objetivo, seleccionarlo
                if (movimiento.sonIguales(posObjetivo)) {
                    mejorMovimiento = movimiento;
                    break;
                }

                // Calcular la distancia Manhattan al objetivo
                int distancia = calcularDistanciaManhattan(movimiento, posObjetivo);
                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorMovimiento = movimiento;
                }
            }
        }

        // Si se encontró un movimiento válido, actualizar la memoria
        if (mejorMovimiento != null) {
            this.sensores = this.entorno.actualizarPercepciones(mejorMovimiento);
        }
    }

    private int calcularDistanciaManhattan(Posicion p1, Posicion p2) {
        return Math.abs(p1.obtenerX() - p2.obtenerX()) + Math.abs(p1.obtenerY() - p2.obtenerY());
    }

    public void actualizarMemoria() {
        int x = this.entorno.obtenerPosAgente().obtenerX();
        int y = this.entorno.obtenerPosAgente().obtenerY();

        // Obtener visión del sensor (suponemos que siempre existe al menos un sensor en la lista)
        Vision sensorVision = (Vision) this.sensores.get(0);
        int[][] celdasContiguas = sensorVision.obtenerVision();

        // Recorrer la matriz de celdas contiguas y actualizar el mapa solo con valores válidos
        for (int i = 0; i < celdasContiguas.length; i++) {
            for (int j = 0; j < celdasContiguas[i].length; j++) {

                if (celdasContiguas[i][j] != -1) {
                    int nuevaX = x + (i - 1);  // Ajustar el índice para la posición relativa
                    int nuevaY = y + (j - 1);  // Ajustar el índice para la posición relativa

                    if (mapaMemoria.casillaEsValida(nuevaX, nuevaY)) {
                        // Establecer el valor en la memoria
                        mapaMemoria.establecerCasilla(nuevaX, nuevaY, celdasContiguas[i][j]);

                        if (i != 4 || j != 4) {  // Excluir la posición central (4,4) del rastro
                            mapaMemoria.establecerCasilla(nuevaX, nuevaY, 1);
                        }
                    }
                }
            }
        }
        this.mapaMemoria.mostrarMapa();
    }
}
