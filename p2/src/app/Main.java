package app;

import java.io.IOException;
import modelo.Mapa;

/**
 * @brief Clase que ejecuta el simulador.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Mapa mapa = new Mapa("mapas/mapWithComplexObstacle2.txt");
        
        mapa.mostrarMapa();
    }
    
}
