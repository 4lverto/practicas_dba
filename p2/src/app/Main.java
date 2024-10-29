package app;

import java.io.IOException;
import modelo.Mapa;
import modelo.Posicion;
import modelo.sensores.Vision;

/**
 * @brief Clase que ejecuta el simulador.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Mapa mapa = new Mapa("mapas/mapWithComplexObstacle2.txt");
        
        mapa.mostrarMapa();
        
        // Prueba del nuevo método para establecer la casilla:
        mapa.establecerCasilla(1, 2, 1);
        System.out.println();
        mapa.mostrarMapa();
        
        // Prueba del sensor de la visión:
        Posicion posInicial     = new Posicion(2, 3);
        Vision vision           = new Vision(mapa, posInicial);
        int[][] celdasContiguas = vision.obtenerVision();
        
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(celdasContiguas[i][j] + " ");
            }
            System.out.println();
        }
        // Cambiar posición del agente (en una zona del mapa con todas las zonas 
        // contiguas dentro de los límites del mapa):
        vision.actualizarPosAgente(new Posicion(5, 3));
        vision.actualizar();
        celdasContiguas = vision.obtenerVision();
        
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(celdasContiguas[i][j] + " ");
            }
            System.out.println();
        }
        // Cambiar posición del agente (en una zona del mapa con todas las zonas 
        // contiguas superiores fuera de los límites del mapa):
        vision.actualizarPosAgente(new Posicion(0, 2));
        vision.actualizar();
        celdasContiguas = vision.obtenerVision();
        
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(celdasContiguas[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
