package app;


import java.io.IOException;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Energia;
import modelo.sensores.Sensor;
import modelo.sensores.Vision;


/**
 * @brief Clase que ejecuta el simulador.
 */
public class Main {
    
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

    public static void main(String[] args) throws IOException {
        pruebasEntornoYSensores();
    }
    
}
