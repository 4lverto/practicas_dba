package app;

import controlador.Controlador;
import java.io.IOException;
import modelo.Mapa;
import modelo.Posicion;
import modelo.agentes.Agente;
import modelo.sensores.Vision;
import vista.Vista;
import vista.VistaTexto;

/**
 * @brief Clase que ejecuta el simulador.
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        Mapa mapa = new Mapa("mapas/mapWithComplexObstacle2.txt");
//        
//        mapa.mostrarMapa();
//        
//        // Prueba del nuevo método para establecer la casilla:
//        mapa.establecerCasilla(1, 2, 1);
//        System.out.println();
//        mapa.mostrarMapa();
//        
//        // Prueba del sensor de la visión:
//        Posicion posInicial     = new Posicion(2, 3);
//        Vision vision           = new Vision(mapa, posInicial);
//        int[][] celdasContiguas = vision.obtenerVision();
//        
//        System.out.println();
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(celdasContiguas[i][j] + " ");
//            }
//            System.out.println();
//        }
//        // Cambiar posición del agente (en una zona del mapa con todas las zonas 
//        // contiguas dentro de los límites del mapa):
//        vision.actualizarPosAgente(new Posicion(5, 3));
//        vision.actualizar();
//        celdasContiguas = vision.obtenerVision();
//        
//        System.out.println();
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(celdasContiguas[i][j] + " ");
//            }
//            System.out.println();
//        }
//        // Cambiar posición del agente (en una zona del mapa con todas las zonas 
//        // contiguas superiores fuera de los límites del mapa):
//        vision.actualizarPosAgente(new Posicion(0, 2));
//        vision.actualizar();
//        celdasContiguas = vision.obtenerVision();
//        
//        System.out.println();
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(celdasContiguas[i][j] + " ");
//            }
//            System.out.println();
//        }

//        // Prueba de la vista textual (por terminal):
//        Mapa mapa = new Mapa("mapas/mapWithComplexObstacle2.txt");
//        Vista vistaTexto = new VistaTexto();
//        vistaTexto.actualizar(mapa);



        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // El programa se usaría de la siguiente manera (en principio):
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //
        
        // 1. Crear el agente:
        
        // Suponiendo que son posiciones válidas:
        Posicion posAgente   = new Posicion(2, 4);
        Posicion posObjetivo = new Posicion(8, 0);
        Agente agente        = new Agente(posAgente, posObjetivo);
        
        // 2. Crear el controlador:
        Controlador controlador = new Controlador(agente);
        
        // 3. Crear y registrar las vistas:
        controlador.registrarVista(new VistaTexto());
        
        // 4. Simular la partida:
        controlador.iniciarSimulacion();
        
        // Mientras no se alcance el objetivo, seguir simulando:
        while (!controlador.objetivoAlcanzado()) {
            controlador.ejecutar();
        }
        
        // 5. En este punto, el agente ha llegado a la casilla objetivo:
        controlador.finalizarSimulacion();
    }
    
}
