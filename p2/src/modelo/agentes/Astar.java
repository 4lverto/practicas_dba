package modelo.agentes;

import java.io.IOException;
import modelo.Posicion;
import modelo.Mapa;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Astar {

    public static class Details {

        double value;
        int i;
        int j;

        public Details(double value, int i, int j) {
            this.value = value;
            this.i = i;
            this.j = j;
        }
    }

    public static class Celda {

        public Posicion parent;

        public double f, g, h;

        Celda() {
            parent = new Posicion(-1, -1);
            f = -1;
            g = -1;
            h = -1;
        }

        public Celda(Posicion parent, double f, double g, double h) {
            this.parent = parent;
            this.f = f;
            this.g = g;
            this.h = h;
        }
    }

    double calculateHValue(Posicion src, Posicion dest) {
        return Math.sqrt(Math.pow((src.obtenerX() - dest.obtenerX()), 2.0) + Math.pow((src.obtenerY() - dest.obtenerY()), 2.0));
    }

    ArrayList<Posicion> tracePath(
            Celda[][] cellDetails,
            Posicion dest) {
        System.out.println("The Path:  ");

        ArrayList<Posicion> path = new ArrayList<>();

        int row = dest.obtenerX();
        int col = dest.obtenerY();

        Posicion nextNode = cellDetails[row][col].parent;
        do {
            path.add(new Posicion(row, col));
            nextNode = cellDetails[row][col].parent;
            row = nextNode.obtenerX();
            col = nextNode.obtenerY();
        } while (cellDetails[row][col].parent != nextNode);

        for (Posicion pos : path) {
            System.out.println("{"+pos.obtenerX()+","+pos.obtenerY()+"}\n");
        }

        return path;
    }

    ArrayList<Posicion> aStarSearch(Mapa mapa, Posicion src, Posicion dest) {

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
        cellDetails[i][j].parent = new Posicion(i, j);

        PriorityQueue<Details> openList = new PriorityQueue<>((o1, o2) -> (int) Math.round(o1.value - o2.value));

        openList.add(new Details(0.0, i, j));

        while (!openList.isEmpty()) {
            Details p = openList.peek();

            i = p.i;
            j = p.j;

            openList.poll();
            closedList[i][j] = true;

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
                        
                        if (neighbour.sonIguales(dest)) {
                            cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].parent = new Posicion(i, j);
                            System.out.println("The destination cell is found");
                            return tracePath(cellDetails, dest);
                        } else if (!closedList[neighbour.obtenerX()][neighbour.obtenerY()]
                                && mapa.obtenerCasilla(neighbour.obtenerX(), neighbour.obtenerY()) == 0) {
                            double gNew, hNew, fNew;
                            gNew = cellDetails[i][j].g + 1.0;
                            hNew = calculateHValue(neighbour, dest);
                            fNew = gNew + hNew;

                            if (cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].f == -1
                                    || cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].f > fNew) {

                                openList.add(new Details(fNew, neighbour.obtenerX(), neighbour.obtenerY()));

                                cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].g = gNew;

                                cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].f = fNew;
                                cellDetails[neighbour.obtenerX()][neighbour.obtenerY()].parent = new Posicion(i, j);
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

        Mapa mapa = new Mapa("mapas/mapWithDiagonalWall.txt");

        Posicion src = new Posicion(0, 0);

        Posicion dest = new Posicion(6, 6);

        Astar app = new Astar();
        app.aStarSearch(mapa, src, dest);

    }
}
