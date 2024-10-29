package modelo;

import modelo.sensores.Energia;
import modelo.sensores.Vision;
import java.io.IOException;

/**
 * @class Entorno
 * 
 * @brief Clase que representa el entorno del sistema. Implementa el patrón Singleton
 * para asegurar que solo exista una única instancia operativa.
 */
public class Entorno {
    
    /**
     * @brief Instancia única de la clase Entorno (patrón Singleton).
     */
    private static Entorno instancia;
    
    /**
     * @brief Objeto que representa el mapa del entorno.
     */
    private Mapa mapa;
    
    /**
     * @brief Sensor de energía del entorno.
     */
    private Energia energia;
    
    /**
     * @brief Sensor de visión del entorno.
     */
    private Vision vision;
    
    /**
     * @brief Posición del agente en el mapa.
     */
    private Posicion posAgente;
    
    /**
     * @brief Posición de la casilla objetivo en el mapa.
     */
    private Posicion posObjetivo;

    
    
    /**
     * @brief Constructor privado para evitar la creación de múltiples 
     * instancias.
     * Inicializa el mapa a partir del archivo de texto especificado.
     * 
     * @param nombreFicheroMapa Nombre del archivo que contiene los datos del 
     * mapa.
     * @param posAgente Posición inicial del agente en el mapa.
     * @param posObjetivo Posición inicial de la casilla objetivo en el mapa.
     * 
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Entorno(
            String nombreFicheroMapa, 
            Posicion posAgente,
            Posicion posObjetivo) throws IOException {
        
        this.mapa        = new Mapa(nombreFicheroMapa);
        this.posAgente   = posAgente;
        this.posObjetivo = posObjetivo;
        this.energia     = new Energia();
        this.vision      = new Vision(mapa, posAgente);
    }

    /**
     * @brief Obtiene la instancia única de la clase Entorno. Si no existe, la crea
     * utilizando el archivo de mapa proporcionado.
     * 
     * @param nombreFicheroMapa Nombre del archivo que contiene los datos del mapa.
     * @param posAgente Posición inicial del agente en el mapa.
     * @param posObjetivo Posición inicial de la casilla objetivo en el mapa.
     * 
     * @return La instancia única de Entorno.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Entorno obtenerInstancia(
            String nombreFicheroMapa, 
            Posicion posAgente,
            Posicion posObjetivo) throws IOException {
        
        if (instancia == null) {
            instancia = new Entorno(
                    nombreFicheroMapa, posAgente, posObjetivo);
        }
        
        return instancia;
    }

    /**
     * @brief Actualiza las percepciones del agente según su posición.
     * 
     * @param posAgente La posición actual del agente en el entorno.
     * @return La posición actualizada del agente.
     */
    public Posicion actualizarPercepciones(Posicion posAgente) {
        energia.actualizar();
        vision.actualizar();
        // Aquí se podrían añadir otras actualizaciones específicas
        return posAgente;
    }

    /**
     * @bief Muestra el entorno en la consola.
     */
    public void mostrarEntorno() {
        mapa.mostrarMapa();
    }

    /**
     * @brief Verifica si una posición es válida dentro del mapa.
     * 
     * @param pos La posición a verificar.
     * @return true si la posición es válida; false en caso contrario.
     */
    public boolean posEsValida(Posicion pos) {
        int filas = mapa.obtenerNumFilas();
        int columnas = mapa.obtenerNumColumnas();
        int x = pos.obtenerX();
        int y = pos.obtenerY();
        
        return (x >= 0 && x < filas && y >= 0 && y < columnas);
    }
}
