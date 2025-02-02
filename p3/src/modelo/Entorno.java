package modelo;

import java.io.IOException;
import java.util.ArrayList;
import modelo.sensores.Sensor;
import vista.Vista;

/**
 * @class Entorno
 *
 * @brief Clase que representa el entorno del sistema. Implementa el patrón
 * Singleton para asegurar que solo exista una única instancia operativa, así
 * como el patrón Observer para permitir que tanto los sensores como las vistas
 * sean actualizados automáticamente cada vez que se produzcan cambios
 * importantes.
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
     * @brief Conjunto de sensores que podrá consultar el agente.
     */
    private final ArrayList<Sensor> sensores;

    /**
     * @brief Conjunto de vistas del simulador.
     */
    private final ArrayList<Vista> vistas;

    /**
     * @brief Posición del agente en el mapa.
     */
    private Posicion posAgente;

    /**
     * @brief Posición de Santa Claus en el mapa
     */
    private final Posicion posSantaClaus;

    /**
     * @brief Posicion de cada reno
     */
    private final Posicion[] posReno;

    /**
     * @brief Posición de la casilla objetivo en el mapa.
     */
    //private Posicion posObjetivo;
    /**
     * @brief Constructor privado para evitar la creación de múltiples
     * instancias. Inicializa el mapa a partir del archivo de texto
     * especificado.
     *
     * @param posAgente Posición inicial del agente en el mapa.
     * @param posObjetivo Posición inicial de la casilla objetivo en el mapa.
     *
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private Entorno(Posicion posAgente, Posicion[] posReno, Posicion posSantaClaus) throws IOException {

        this.posAgente = posAgente;
        this.posSantaClaus = posSantaClaus;
        this.posReno = posReno;

        this.sensores = new ArrayList<>();
        this.vistas = new ArrayList<>();

    }

    /**
     * @brief Obtiene la instancia única de la clase Entorno. Si no existe, la
     * crea utilizando el archivo de mapa proporcionado.
     *
     * @param posAgente Posición inicial del agente en el mapa.
     * @param posReno posiciones renos
     * @param posSantaClaus posicion santa
     *
     * @return La instancia única de Entorno.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Entorno obtenerInstancia(Posicion posAgente, Posicion[] posReno, Posicion posSantaClaus) throws IOException {

        // De esta forma fomentamos el patrón Singleton, pues si ya existe
        // una instancia del Entorno no damos opción a crear otra.
        if (instancia == null) {
            instancia = new Entorno(posAgente, posReno, posSantaClaus);
        }

        return (instancia);
    }

    /**
     * @brief Actualiza las percepciones del agente según su posición.
     *
     * @param nuevaPosAgente La nueva posición del agente tras haberla computado
     * previamente.
     *
     * @return Los sensores que puede consultar el agente tras haber sido
     * debidamente actualizados.
     */
    public ArrayList<Sensor> actualizarPercepciones(Posicion nuevaPosAgente) {

        // Dejar libre la antigua casilla del agente:
        this.mapa.establecerCasilla(
                this.posAgente.obtenerFil(),
                this.posAgente.obtenerCol(),
                Mapa.VISITADA);

        this.posAgente = nuevaPosAgente;

        // Actualizar la posición del agente en el mapa:
        this.mapa.establecerCasilla(
                nuevaPosAgente.obtenerFil(),
                nuevaPosAgente.obtenerCol(),
                Mapa.AGENTE);

        // En este punto, se actualizan los observadores (sensores y vistas):
        notificarSensores();
        notificarVistas();

        return (sensores); // Se devuelven los sensores actualizados.
    }

    /**
     * @brief Crea el mapa a partir de un fichero.
     *
     * @param nombreFicheroMapa Nombre del archivo que contiene los datos del
     * mapa.
     * @throws IOException
     */
    public void establecerMapa(String nombreFicheroMapa) throws IOException {

        // En el propio constructor del mapa se gestionará el atribuirle un
        // valor específico a cada casilla
        this.mapa = new Mapa(nombreFicheroMapa);

        // Colocar al agente y a la casilla objetivo en el mapa:
        //this.mapa.establecerCasilla(
        //        this.posAgente.obtenerFil(), 
        //        this.posAgente.obtenerCol(), 
        //        Mapa.AGENTE);
        //this.mapa.establecerCasilla(
        //        this.posObjetivo.obtenerFil(), 
        //        this.posObjetivo.obtenerCol(), 
        //        Mapa.OBJETIVO);
    }

    /**
     * @brief Devuelve el mapa en el estado en que se encuentre cuando se
     * realice la llamada.
     */
    public Mapa obtenerMapa() {
        return (mapa);
    }

    /**
     * @brief Consultor para la posición del agente.
     *
     * @return Posición actual del agente.
     */
    public Posicion obtenerPosAgente() {
        return (this.posAgente);
    }

    public Posicion[] obtenerPosReno() {
        return (this.posReno);
    }

    public Posicion obtenerPosSantaClaus() {
        return (this.posSantaClaus);
    }

    /**
     * @brief Verifica si una posición es válida dentro del mapa (está dentro de
     * sus límites y es una casilla libre).
     *
     * @param pos La posición a verificar.
     * @return 'true' si la posición es válida, es decir es una casilla libre;
     * 'false' en caso contrario.
     */
    public boolean posEsValida(Posicion pos) {
        return (mapa.casillaEsValida(pos.obtenerFil(), pos.obtenerCol()) && (mapa.obtenerCasilla(pos.obtenerFil(), pos.obtenerCol()) == Mapa.LIBRE
                || mapa.obtenerCasilla(pos.obtenerFil(), pos.obtenerCol()) == Mapa.OBJETIVO));
    }

    /**
     * @brief Registra una vista (observador) en el array de vistas.
     *
     * @param vista Vista a añadir.
     */
    public void registrarVista(Vista vista) {
        vistas.add(vista);
    }

    /**
     * @brief Elimina una vista (observador) del array de vistas.
     *
     * @param vista Vista a eliminar.
     */
    public void eliminarVista(Vista vista) {
        vistas.remove(vista);
    }

    /**
     * @brief Notifica a cada vista que ha habido un cambio, desencadenando que
     * cada una de estas vistas se actualice en consecuencia, permitiendo, de
     * esta manera, que automáticamente se plasmen visualmente los cambios en el
     * mapa.
     */
    public void notificarVistas() {
        for (Vista v : vistas) {
            v.actualizar();
        }
    }

    /**
     * @brief Registra un sensor (observador) en el array de sensores.
     *
     * @param sensor Sensor a añadir.
     */
    public void registrarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    /**
     * @brief Elimina un sensor (observador) del array de sensores.
     *
     * @param sensor Sensor a eliminar.
     */
    public void eliminarSensor(Sensor sensor) {
        sensores.remove(sensor);
    }

    /**
     * @brief Devuelve los sensores del entorno con el estado actual de los
     * sensores
     *
     * @return Array con lo sensores disponibles en el entorno
     */
    public ArrayList<Sensor> obtenerSensores() {
        return sensores;
    }

    /**
     * @brief Notifica a cada sensor que ha habido un cambio, desencadenando que
     * cada uno de estos sensores se actualice en consecuencia.
     */
    public void notificarSensores() {
        for (Sensor s : sensores) {
            s.actualizar();
        }
    }
}
