package app;


import controlador.Controlador;
import java.io.IOException;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Energia;
import modelo.sensores.Sensor;
import modelo.sensores.Vision;
import vista.VistaGrafica;
import vista.VistaTexto;


/**
 * @brief Clase que ejecuta el simulador.
 */
public class Main {
    
    /**
     * @brief Método principal que contiene la lógica de ejecución del simulador.
     * 
     * @param args Argumentos.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        //pruebasEntornoYSensores();
        //pruebaVistaTextual();
        pruebaVistaGrafica();
        
        
        
//        // Crear las posiciones del agente y de la casilla objetivo (hay que 
//        // tener cuidado de que sean válidas para el mapa elegido):
//        Posicion posAgente   = new Posicion(2, 4);
//        Posicion posObjetivo = new Posicion(7, 1);
//        
//        // Crear el entorno para pasarlo al controlador:
//        Entorno entorno = Entorno.obtenerInstancia(
//                posAgente, 
//                posObjetivo);
//        // Establecer el mapa:
//        entorno.establecerMapa("mapas/mapWithComplexObstacle2.txt");
//        
//        // Crear el controlador, el cual lanzará al agente (el entorno le llega
//        // al agente a través del controlador, con las posiciones y el mapa 
//        // debidamente inicializados):
//        Controlador controlador = new Controlador(
//                "Agente-DBA-P2",
//                "modelo.agentes.Agente",
//                entorno);
//        
//        // Iniciar la simulación. Esta terminará cuando el agente llegue a la 
//        // casilla objetivo (el agente lo controla internamente).
//        controlador.ejecutar();
    }
    
    
    
    public static void pruebaVistaGrafica() throws IOException {
        Posicion posAgente   = new Posicion(2, 3);
        Posicion posObjetivo = new Posicion(7,1);
        Entorno entorno      = Entorno.obtenerInstancia(posAgente, posObjetivo);
        
        entorno.establecerMapa("mapas/mapWithComplexObstacle2.txt");
        
        VistaGrafica vista = new VistaGrafica(entorno);
        
        // Pequeña simulación de prueba:
        int i = 1;
        
        while (i <= 5) {
            try {
                Thread.sleep(1500); // Pausar segundo y medio.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Cambio en el entorno:
            entorno.actualizarPercepciones(
                    new Posicion(
                            entorno.obtenerPosAgente().obtenerX(),
                            entorno.obtenerPosAgente().obtenerY() + 1));
            
            i ++;
        }
        
//        // Cambio en el entorno:
//        Posicion pos = new Posicion(2, 4);
//        entorno.actualizarPercepciones(pos);
//        
//        // Cambio en el entorno:
//        pos = new Posicion(2, 5);
//        System.out.println();
//        entorno.actualizarPercepciones(pos);
    }
    
    public static void pruebasEntornoYSensores() throws IOException {
        Posicion posAgente   = new Posicion(2, 3);
        Posicion posObjetivo = new Posicion(7,1);
        Entorno entorno      = Entorno.obtenerInstancia(posAgente, posObjetivo);
        
        entorno.establecerMapa("mapas/mapWithComplexObstacle2.txt");
        entorno.obtenerMapa().mostrarMapa();
        
        Energia energia = new Energia(entorno);
        Vision vision   = new Vision(entorno);
        
        // Estado inicial:
        System.out.println();
        System.out.println("Sensores iniciales: ");
        System.out.println("Sensor de energía: " + 
                energia.obtenerEnergia());
        System.out.println("Sensor de visión:");
        int[][] v = vision.obtenerVision();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
        
        // Siguiente estado:
        Posicion pos = new Posicion(2, 4);
        ArrayList<Sensor> sensores = 
                entorno.actualizarPercepciones(pos);
        System.out.println();
        entorno.obtenerMapa().mostrarMapa();
        
        System.out.println();
        System.out.println("Sensores: ");
        System.out.println("Sensor de energía: " + 
                energia.obtenerEnergia());
        System.out.println("Sensor de visión:");
        vision = (Vision) sensores.get(1);
        v      = vision.obtenerVision();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
        
        // Siguiente estado:
        Posicion otraPos = new Posicion(3, 4);
        sensores = entorno.actualizarPercepciones(otraPos);
        System.out.println();
        entorno.obtenerMapa().mostrarMapa();
        
        System.out.println();
        System.out.println("Sensores: ");
        System.out.println("Sensor de energía: " + 
                energia.obtenerEnergia());
        System.out.println("Sensor de visión:");
        vision = (Vision) sensores.get(1);
        v      = vision.obtenerVision();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void pruebaVistaTextual() throws IOException {
        Posicion posAgente   = new Posicion(2, 3);
        Posicion posObjetivo = new Posicion(7,1);
        Entorno entorno      = Entorno.obtenerInstancia(posAgente, posObjetivo);
        
        entorno.establecerMapa("mapas/mapWithComplexObstacle2.txt");
        
        VistaTexto vista = new VistaTexto(entorno);
        
        // Cambio en el entorno:
        Posicion pos = new Posicion(2, 4);
        entorno.actualizarPercepciones(pos);
        
        // Cambio en el entorno:
        pos = new Posicion(2, 5);
        System.out.println();
        entorno.actualizarPercepciones(pos);
    }
}
