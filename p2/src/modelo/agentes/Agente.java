package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.*;
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

        // Comportamiento que se repite cada 2 segundos
        addBehaviour(new TickerBehaviour(this, 2000) {
            @Override
            protected void onTick() {
                if (!objetivoAlcanzado()) {
                    actualizarMemoria();
                    ejecutarAlgoritmoAEstrella();
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

    // Implementación del algoritmo A*
    public void ejecutarAlgoritmoAEstrella() {
        Posicion inicio = entorno.obtenerPosAgente();
        Posicion objetivo = entorno.obtenerPosObjetivo();

        PriorityQueue<Nodo> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Posicion> closedList = new HashSet<>();

        Nodo nodoInicio = new Nodo(inicio, 0, calcularDistanciaManhattan(inicio, objetivo), null);
        openList.add(nodoInicio);

        Nodo nodoFinal = null;

        while (!openList.isEmpty()) {
            Nodo nodoActual = openList.poll();
            closedList.add(nodoActual.posicion);

            // Si se llega al objetivo
            if (nodoActual.posicion.sonIguales(objetivo)) {
                nodoFinal = nodoActual;
                break;
            }

            // Explorar vecinos
            for (Accion accion : Accion.values()) {
                Posicion vecino = obtenerPosicionVecino(nodoActual.posicion, accion);

                if (vecino == null || !entorno.posEsValida(vecino) || closedList.contains(vecino)) {
                    continue;
                }

                int gNuevo = nodoActual.g + 1;
                int hNuevo = calcularDistanciaManhattan(vecino, objetivo);
                Nodo vecinoNodo = new Nodo(vecino, gNuevo, hNuevo, nodoActual);

                openList.add(vecinoNodo);
            }
        }

        if (nodoFinal != null) {
            // Mover al agente al siguiente paso en el camino encontrado
            Nodo siguientePaso = nodoFinal;
            while (siguientePaso.padre != null && siguientePaso.padre.padre != null) {
                siguientePaso = siguientePaso.padre;
            }

            // Actualizar la posición del agente en el entorno
            entorno.actualizarPercepciones(siguientePaso.posicion);
            System.out.println("Moviendo a la posición: " + siguientePaso.posicion.obtenerX() + ", " + siguientePaso.posicion.obtenerY());
        } else {
            System.out.println("No se encontró un camino al objetivo.");
        }
    }

    // Calcula la distancia Manhattan entre dos posiciones
    private int calcularDistanciaManhattan(Posicion p1, Posicion p2) {
        return Math.abs(p1.obtenerX() - p2.obtenerX()) + Math.abs(p1.obtenerY() - p2.obtenerY());
    }

    // Obtener la nueva posición según la acción
    private Posicion obtenerPosicionVecino(Posicion actual, Accion accion) {
        switch (accion) {
            case ABAJO: return new Posicion(actual.obtenerX() + 1, actual.obtenerY());
            case ARRIBA: return new Posicion(actual.obtenerX() - 1, actual.obtenerY());
            case DERECHA: return new Posicion(actual.obtenerX(), actual.obtenerY() + 1);
            case IZQUIERDA: return new Posicion(actual.obtenerX(), actual.obtenerY() - 1);
            case DIAGONAL_ARRIBA_DERECHA: return new Posicion(actual.obtenerX() - 1, actual.obtenerY() + 1);
            case DIAGONAL_ARRIBA_IZQUIERDA: return new Posicion(actual.obtenerX() - 1, actual.obtenerY() - 1);
            case DIAGONAL_ABAJO_DERECHA: return new Posicion(actual.obtenerX() + 1, actual.obtenerY() + 1);
            case DIAGONAL_ABAJO_IZQUIERDA: return new Posicion(actual.obtenerX() + 1, actual.obtenerY() - 1);
            default: return null;
        }
    }

    public void actualizarMemoria() {
        int x = entorno.obtenerPosAgente().obtenerX();
        int y = entorno.obtenerPosAgente().obtenerY();

        Vision sensorVision = (Vision) sensores.get(0);
        int[][] celdasContiguas = sensorVision.obtenerVision();

        for (int i = 0; i < celdasContiguas.length; i++) {
            for (int j = 0; j < celdasContiguas[i].length; j++) {
                if (celdasContiguas[i][j] != -1) {
                    int nuevaX = x + (i - 1);
                    int nuevaY = y + (j - 1);

                    if (mapaMemoria.casillaEsValida(nuevaX, nuevaY)) {
                        mapaMemoria.establecerCasilla(nuevaX, nuevaY, celdasContiguas[i][j]);
                        if (i != 4 || j != 4) {
                            mapaMemoria.establecerCasilla(nuevaX, nuevaY, 1);
                        }
                    }
                }
            }
        }
        mapaMemoria.mostrarMapa();
    }

    // Clase interna para los nodos del algoritmo A*
    class Nodo {
        Posicion posicion;
        int g; // costo desde el inicio hasta el nodo actual
        int h; // estimación heurística al objetivo
        int f; // g + h
        Nodo padre;

        Nodo(Posicion posicion, int g, int h, Nodo padre) {
            this.posicion = posicion;
            this.g = g;
            this.h = h;
            this.f = g + h;
            this.padre = padre;
        }
    }
}
