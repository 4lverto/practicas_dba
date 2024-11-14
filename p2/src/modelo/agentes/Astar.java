package modelo.agentes;

import java.io.IOException;
import modelo.Posicion;
import modelo.Mapa;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Astar {

    private static class Details {

        double valor;
        int i;
        int j;

        public Details(double valor, int i, int j) {
            this.valor = valor;
            this.i = i;
            this.j = j;
        }
    }

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

    double heuristica(Posicion origen, Posicion dest) {
        return Math.sqrt(Math.pow((origen.obtenerX() - dest.obtenerX()), 2.0) + Math.pow((origen.obtenerY() - dest.obtenerY()), 2.0));
    }

    ArrayList<Posicion> obtenerCamino(
            Celda[][] celdas,
            Posicion dest) {
        System.out.println("The Path:  ");

        ArrayList<Posicion> camino = new ArrayList<>();

        int fila = dest.obtenerX();
        int col = dest.obtenerY();

        Posicion nextNode;
        do {
            camino.add(new Posicion(fila, col));
            nextNode = celdas[fila][col].padre;
            fila = nextNode.obtenerX();
            col = nextNode.obtenerY();
        } while (celdas[fila][col].padre != nextNode);

        for (Posicion pos : camino) {
            System.out.println("{"+pos.obtenerX()+","+pos.obtenerY()+"}\n");
        }

        return camino;
    }

    ArrayList<Posicion> aStarSearch(Mapa mapa, Posicion src, Posicion dest) {

        //Comprobaciones iniciales
        if (!mapa.casillaEsValida(src.obtenerX(), src.obtenerY())) {
            System.out.println("Origen invalido...");
            return new ArrayList();
        }

        if (!mapa.casillaEsValida(dest.obtenerX(), src.obtenerY())) {
            System.out.println("Destino invalido...");
            return new ArrayList();
        }

        if (mapa.obtenerCasilla(src.obtenerX(), src.obtenerY()) == -1
                || mapa.obtenerCasilla(dest.obtenerX(), dest.obtenerY()) == -1) {
            System.out.println("Origen o destino bloqueados...");
            return new ArrayList();
        }

        if (src.sonIguales(dest)) {
            System.out.println("Ya estamos en el destino");
            return new ArrayList();
        }

        boolean[][] closedList = new boolean[mapa.obtenerNumFilas()][mapa.obtenerNumColumnas()];

        Celda[][] cellDetails = new Celda[mapa.obtenerNumFilas()][mapa.obtenerNumColumnas()];

        int i, j;

        i = src.obtenerX();
        j = src.obtenerY();
        cellDetails[i][j] = new Celda();
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].padre = new Posicion(i, j);

        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.valor - o2.valor));

        openList.add(new Details(0.0, i, j));

        while (!openList.isEmpty()) {
            Details p = openList.peek();

            i = p.i;
            j = p.j;

            openList.poll();
            closedList[i][j] = true;

            //Obtenemos las potenciales siguientes casillas
            for (int addX = -1; addX <= 1; addX++) {
                for (int addY = -1; addY <= 1; addY++) {
                    Posicion neighbour = new Posicion(i + addX, j + addY);
                    if (mapa.casillaEsValida(neighbour.obtenerX(), neighbour.obtenerY())) {
                        if (cellDetails[neighbour.obtenerX()] == null) {
                            cellDetails[neighbour.obtenerX()] = new Celda[mapa.obtenerNumColumnas()];
                        }
                        if (cellDetails[neighbour.obtenerX()][neighbour.obtenerY()] == null) {
                            cellDetails[neighbour.obtenerX()][neighbour.obtenerY()] = new Celda();
                        }
                        
                        
                            if (neighbour.sonIguales(dest) //Si es el destino
                                   && (addX != addY || (mapa.obtenerCasilla(i+addX,j) == 0 || mapa.obtenerCasilla(i,j+addY) == 0))) {   //Comprobacion bloqueo diagonales
                                cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].padre = new Posicion(i, j);
                                System.out.println("The destination cell is found");
                                return obtenerCamino(cellDetails, dest);
                            } else if (!closedList[neighbour.obtenerX()][neighbour.obtenerY()]              //Si no se ha explorado ya
                                   && mapa.obtenerCasilla(neighbour.obtenerX(), neighbour.obtenerY()) == 0  //y es transitable
                                   && (addX != addY || (mapa.obtenerCasilla(i+addX,j) == 0 || mapa.obtenerCasilla(i,j+addY) == 0))) {   //Comprobacion bloqueo diagonales
                                double gNew, hNew, fNew;
                                gNew = cellDetails[i][j].g + 1.0;
                                hNew = heuristica(neighbour, dest);
                                fNew = gNew + hNew;

                                if (cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].f == -1
                                        || cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].f > fNew) {
                                    
                                    openList.add(new Details(fNew, neighbour.obtenerX(), neighbour.obtenerY()));
                                
                                    cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].g = gNew;

                                    cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].f = fNew;
                                    cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].padre = new Posicion(i, j);
                                }
                            }
                        
                        
                    }
                }
            }
        }

        System.out.println("Failed to find the Destination Celda");
        return new ArrayList();
    }

    public static void main(String[] args) throws IOException {

        Mapa mapa = new Mapa("mapas/mapWithComplexObstacle3.txt");
        
        mapa.mostrarMapa();

        Posicion src = new Posicion(0, 0);

        Posicion dest = new Posicion(7, 6);

        Astar app = new Astar();
        app.aStarSearch(mapa, src, dest);

    }
}
