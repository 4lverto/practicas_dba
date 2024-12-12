package modelo.agentes;

import jade.core.Agent;
import java.util.ArrayList;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;
import modelo.Mapa;
import modelo.comportamientos.*;
import modelo.sensores.Vision;
import modelo.sensores.Energia;

/**
 * @brief Clase que representa al agente autónomo que se desplaza en el entorno.
 * 
 * La clase Agente extiende la clase Agent de JADE y gestiona el ciclo de vida
 * del agente, incluyendo la configuración inicial, la toma de decisiones y el
 * seguimiento de su entorno. Emplea un comportamiento periódico para evaluar
 * contínuamente su posición y actuar en consecuencia.
 */
public class Agente extends Agent {

    /**
     * @brief Entorno en el que se mueve el agente
     */
    private Entorno entorno;
    
    /**
     * @brief Lista de sensores asociados al agente
     */
    private ArrayList<Sensor> sensores;
    
    /**
     * @brief Mapa que almacena la memoria del agente sobre el entorno
     */
    private Mapa mapaMemoria;
    
    /**
     * @brief Lista de posiciones por las que ha pasado el agente
     */
    // private ArrayList<Posicion> trazaRecorrido;
    
    
    /**
     * @brief Métood de configuración incial del agente, llamado al inicio.
     * 
     * Este método recibe el entorno como argumento, inicializa el mapa de
     * memoria y los sensores. Además, configura un comportamiento periódico
     * que permite al agente evaluar su situación y decidir el próximo
     * movimiento.
     */
    @Override
    protected void setup() {
        
        // Inicializar el entorno:
        Object[] args = getArguments();
        
        // El único argumento que se espera recibir es la instancia de Entorno:
        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            entorno = (Entorno) args[0]; // Asigamos a entorno
            this.mapaMemoria = new Mapa(100, 100); // Inicializamos la memoria
            // Configuramos el estado inicial llamando a 
            // actualizarPercepciones(posición actual)
            this.sensores = 
                    this.entorno.actualizarPercepciones(entorno.obtenerPosAgente());
            
            // Inicializamos la traza de recorrido
            //trazaRecorrido = new ArrayList<>();
        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }
        
        // Añadir los comportamientos de los que dispone el agente para que los 
        // lleve a cabo:
        addBehaviour(new ActualizarMemoria(this));        
        addBehaviour(new DecidirMovimiento(this));
        addBehaviour(new PasosTotales(this));
    }
    
    /**
     * @brief Función que usaremos para tomar la decisión de qué movimiento realizar
     */
    public void decidirMovimiento() {
        ArrayList<Posicion> camino = Astar.busqueda(mapaMemoria, entorno.obtenerPosAgente(), entorno.obtenerPosObjetivo());
        
        if (camino != null && !camino.isEmpty()) {
            this.sensores = this.entorno.actualizarPercepciones(camino.get(camino.size()-2));
        }
    }
    
    /**
     * @brief Actualiza la memoria del agente con la infromación de sus sensores
     * 
     * La memoria del agente se actualiza con los datos obtenidos de su sensor
     * de visión, que detecta el entorno inmediato. Los valores de cada celda
     * se integran en el mapa de memoria para mejorar las decisiones de 
     * movimiento.
     */
    public void actualizarMemoria() {
        int x = this.entorno.obtenerPosAgente().obtenerX();
        int y = this.entorno.obtenerPosAgente().obtenerY();

        Vision sensorVision = (Vision) this.sensores.get(0);
        
        // Metemos en celdasContiguas la matriz de visión que devuelve
        // el sensor (las 8 posiciones de alrededor)
        int[][] celdasContiguas = sensorVision.obtenerVision();
        
        // Añadimos a la memoria del agente estas celdasContiguas teniendo
        // en cuenta la posición desde donde las vemos.
        actualizarMapaConVision(x, y, celdasContiguas);
        //this.mapaMemoria.mostrarMapa();
    }

    /**
     * @brief Actualiza el mapa de memoria del agente con la información del
     * sensor de visión.
     * @param x Coordenada X de la posición del agente desde donde se obtuvo
     * la información del sensor de visión.
     * @param y Coordenada Y de la posición del agente desde donde se obtuvo
     * la información del sensor de visión.
     * @param celdasContiguas Datos proporcionados por el sensor de visión con
     * la información inmediata alrededor del agente. Por lógica, será una
     * matriz 3x3.
     */
    private void actualizarMapaConVision(int x, int y, int[][] celdasContiguas) {
        
        // Recorremos las celdas contiguas
        for (int i = 0; i < celdasContiguas.length; i++) {
            for (int j = 0; j < celdasContiguas[i].length; j++) {
                
                // Para cada casilla le definimos su "valor" aplicando un 
                // desplazamiento en las coordenads (x,y) del agente.
                int nuevaX = x + (i - 1);
                int nuevaY = y + (j - 1);
                
                // Para cada celda contigua verificaremos si la posición es 
                // válida, en cuyo caso convertiremos el valor del sensor
                // a un valor adecuado (LIBRE u OBSTACULO)
                if (mapaMemoria.casillaEsValida(nuevaX, nuevaY)) {
                    int valorCasilla = obtenerValorParaCasilla(celdasContiguas[i][j]);
                    
                    // Cada celda será actualizada en la memoria del agente, 
                    // para reflejar su estado real en función de la inmediata
                    // visión del agente
                    mapaMemoria.establecerCasilla(nuevaX, nuevaY, valorCasilla);
                }
            }
        }
    }
    
    /**
     * @brief Convierte el valor del sensor de visión en un valor adecuado para 
     * el mapa.
     * @param valorSensor Valor leído por el sensor
     * @return Valor adecuado para almacenar la casilla en la memoria del agente
     */
    private int obtenerValorParaCasilla(int valorSensor) {
        
        int valorCasilla;
        
        if(valorSensor==0){
            valorCasilla=1;
        }else{
            valorCasilla=valorSensor;
        }
        
        return valorCasilla;
    }
    
     /**
     * @brief Función que se utilizará para comprobar si hemos alcanzado el objetivo
     * @return True si la posición actual del agente es igual a la del objetivo y False en caso contrario
     */
    public boolean objetivoAlcanzado() {
        return entorno.obtenerPosAgente().sonIguales(entorno.obtenerPosObjetivo());
    }
    
    /**
     * @brief Devuelve el numero de pasos gastado por el agente
     * @return Número de pasos necesitados para alcanzar (o no) el obejtivo
     */
    public int obtenerPasosTotales(){
        if(this.objetivoAlcanzado()){
            Energia energia =  (Energia) this.sensores.get(1);
            
            return energia.obtenerEnergia()-1;
        }
        return -1;
    }
    
    /**
     * @brief Mostrará la traza de pasos seguida
     
    public void mostrarTraza(){
        System.out.println("Recorrido seguido por el agente:");
        
        for(int i=0;i<trazaRecorrido.size()-1;i++){
            System.out.print(trazaRecorrido.get(i) + " -> ");
        }
        
        System.out.println("y finalmente... " + trazaRecorrido.get(trazaRecorrido.size()-1));
    }*/
}
