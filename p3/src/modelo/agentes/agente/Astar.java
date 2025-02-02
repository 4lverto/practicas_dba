package modelo.agentes.agente;

import modelo.Posicion;
import modelo.Mapa;
import java.util.PriorityQueue;
import java.util.ArrayList;

/**
 * @class Astar
 *
 * @brief Clase que contiene el codigo para realizar una bisqueda con algoritmo
 * A*.
 */
public class Astar {

    /**
     * @class Datos
     *
     * @brief Clase que se usa en la priority queue para evaluar un nodo abierto
     * frente a otro
     */
    private static class Datos {

        double valor;
        int i;
        int j;

        public Datos(double valor, int i, int j) {
            this.valor = valor;
            this.i = i;
            this.j = j;
        }
    }

    /**
     * @class Datos
     *
     * @brief Clase que alamacena los datos de una celda
     */
    private static class Celda {

        public Posicion padre;

        public double f, g, h;

        Celda() {
            padre = new Posicion(-1, -1);
            f = -1;
            g = -1;
            h = -1;
        }

        public Celda(Posicion padre, double f, double g, double h) {
            this.padre = padre;
            this.f = f;
            this.g = g;
            this.h = h;
        }
    }

    /**
     * @brief Función que estima la distancia del origen al destino
     *
     * Se llama solo desde el metodo busqueda
     */
    private static double heuristica(Posicion origen, Posicion dest) {
        return Math.sqrt(Math.pow((origen.obtenerFil() - dest.obtenerFil()), 2.0) + Math.pow((origen.obtenerCol() - dest.obtenerCol()), 2.0));
    }

    /**
     * @brief Función que obtiene la lista de celdas del camino encontrado
     *
     * Este metodo se llama solo desde el metodo busqueda una vez se ha llegado
     * a la salida
     */
    private static ArrayList<Posicion> obtenerCamino(Celda[][] celdas, Posicion dest) {
        // System.out.println("Camino:  ");

        ArrayList<Posicion> camino = new ArrayList<>();

        int fila = dest.obtenerFil();
        int col = dest.obtenerCol();

        Posicion siguiente;
        do {
            camino.add(new Posicion(fila, col));
            siguiente = celdas[fila][col].padre;
            fila = siguiente.obtenerFil();
            col = siguiente.obtenerCol();
        } while (celdas[fila][col].padre != siguiente);

        /*
        for (Posicion pos : camino) {
            System.out.println("{"+pos.obtenerFil()+","+pos.obtenerCol()+"}\n");
        }*/
        return camino;
    }

    /**
     * @brief Función nos da un camino en un mapa dado desde una posicion
     * destino a una origen con el metodo A*
     *
     * @return ArrayList de de celdas que forman el camino desde el destino al
     * origen
     */
    public static ArrayList<Posicion> busqueda(Mapa mapa, Posicion origen, Posicion dest) {

        //Comprobaciones iniciales
        if (!mapa.casillaEsValida(origen.obtenerFil(), origen.obtenerCol())) {
            System.out.println("\tOrigen invalido...");
            return new ArrayList();
        }

        if (!mapa.casillaEsValida(dest.obtenerFil(), origen.obtenerCol())) {
            System.out.println("\tDestino invalido...");
            return new ArrayList();
        }

        if (mapa.obtenerCasilla(origen.obtenerFil(), origen.obtenerCol()) == -1) {
            System.out.println("\tOrigen bloqueado...");
            return new ArrayList();
        }

        if (origen.sonIguales(dest)) {
            return new ArrayList();
        }

        //Creamos una matriz donde guardamos los daots de las casollas y lista de nodos cerrados
        boolean[][] cerrados = new boolean[mapa.obtenerNumFilas()][mapa.obtenerNumColumnas()];

        Celda[][] celdas = new Celda[mapa.obtenerNumFilas()][mapa.obtenerNumColumnas()];

        int i, j;

        i = origen.obtenerFil();
        j = origen.obtenerCol();
        celdas[i][j] = new Celda();
        celdas[i][j].f = 0.0;
        celdas[i][j].g = 0.0;
        celdas[i][j].h = 0.0;
        celdas[i][j].padre = new Posicion(i, j);

        //Creamos una cola con prioridad para los nodo abiertos
        PriorityQueue<Datos> abiertos = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.valor - o2.valor));

        abiertos.add(new Datos(0.0, i, j));

        //Mientras queden nodos abiertos
        while (!abiertos.isEmpty()) {
            Datos p = abiertos.peek();

            i = p.i;
            j = p.j;

            abiertos.poll();
            cerrados[i][j] = true;

            //Obtenemos las potenciales siguientes casillas
            for (int addX = -1; addX <= 1; addX++) {
                for (int addY = -1; addY <= 1; addY++) {
                    Posicion adyacente = new Posicion(i + addX, j + addY);
                    if (mapa.casillaEsValida(adyacente.obtenerFil(), adyacente.obtenerCol())) {
                        if (celdas[adyacente.obtenerFil()] == null) {
                            celdas[adyacente.obtenerFil()] = new Celda[mapa.obtenerNumColumnas()];
                        }
                        if (celdas[adyacente.obtenerFil()][adyacente.obtenerCol()] == null) {
                            celdas[adyacente.obtenerFil()][adyacente.obtenerCol()] = new Celda();
                        }

                        if (adyacente.sonIguales(dest) //Si es el destino se devuelve el camino encontrado
                                && (addX != addY || (mapa.obtenerCasilla(i + addX, j) != -1 || mapa.obtenerCasilla(i, j + addY) != -1))) {   //Comprobacion bloqueo diagonales
                            celdas[adyacente.obtenerFil()][adyacente.obtenerCol()].padre = new Posicion(i, j);
                            System.out.println("\tEl agente esta buscando su objetivo...");
                            if (mapa.obtenerCasilla(dest.obtenerFil(), dest.obtenerCol()) == -1) {
                                System.out.println("\tObjetivo inalcanzable -> Esta sobre un muro!!!...");
                                return new ArrayList();
                            }
                            return obtenerCamino(celdas, dest);

                        } else if (!cerrados[adyacente.obtenerFil()][adyacente.obtenerCol()] //Si no se ha explorado ya
                                && mapa.obtenerCasilla(adyacente.obtenerFil(), adyacente.obtenerCol()) != -1 //y es transitable
                                && (addX != addY || (mapa.obtenerCasilla(i + addX, j) != -1 || mapa.obtenerCasilla(i, j + addY) != -1))) {   //Comprobacion bloqueo diagonales

                            //Se calculay registra el valor de la celda                         
                            double gNew, hNew, fNew;
                            gNew = celdas[i][j].g + 1.0;
                            hNew = heuristica(adyacente, dest);
                            fNew = gNew + hNew;

                            if (celdas[adyacente.obtenerFil()][adyacente.obtenerCol()].f == -1
                                    || celdas[adyacente.obtenerFil()][adyacente.obtenerCol()].f > fNew) {

                                abiertos.add(new Datos(fNew, adyacente.obtenerFil(), adyacente.obtenerCol()));

                                celdas[adyacente.obtenerFil()][adyacente.obtenerCol()].g = gNew;

                                celdas[adyacente.obtenerFil()][adyacente.obtenerCol()].f = fNew;
                                celdas[adyacente.obtenerFil()][adyacente.obtenerCol()].padre = new Posicion(i, j);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("No se ha encontrado camino");
        return new ArrayList();
    }
}
