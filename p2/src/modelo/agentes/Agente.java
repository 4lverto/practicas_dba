package modelo.agentes;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import modelo.Entorno;
import modelo.Posicion;
import modelo.sensores.Sensor;
import modelo.Accion;
import modelo.Mapa;
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
     * @brief Métood de configuración incial del agente, llamado al inicio.
     * 
     * Este método recibe el entorno como argumento, inicializa el mapa de
     * memoria y los sensores. Además, configura un comportamiento periódico
     * que permite al agente evaluar su situación y decidir el próximo
     * movimiento.
     */
    @Override
    protected void setup() {
        
        // Inicialización del entorno 
        Object[] args = getArguments();
        
        // El único argumento que se espera recibir es la instancia de Entorno.
        if (args != null && args.length == 1 && args[0] instanceof Entorno) {
            entorno = (Entorno) args[0]; // Asigamos a entorno
            this.mapaMemoria = new Mapa(10, 10); // Inicializamos la memoria
            // Configuramos el estado inicial llamando a actualizarPercepciones(posición actual)
            this.sensores = this.entorno.actualizarPercepciones(entorno.obtenerPosAgente());
        } else {
            System.out.println("\nError: no se recibió el entorno.");
            doDelete();
            return;
        }
        
        // Definimos un comportamiento periódico para poder ver la traza de movimientos
        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
                
                // Si áun no alcanzamos el objetivo, actualizamos memoria y decidimos
                // el siguiente movimiento
                if (!objetivoAlcanzado()) {
                    actualizarMemoria();
                    decidirMovimiento();
                } else { // En caso contrario significa que hemos logrado el objetivo
                    System.out.println("¡Objetivo alcanzado! :D ");
                    
                    // Aquí podríamos mostrar la "energía" gastada (pasos para llegar)
                    System.out.println(enCuantosPasosHeLlegado());
                    
                    myAgent.doDelete();
                }
            }
        });
    }
    
    /**
     * @brief Función que se utilizará para comprobar si hemos alcanzado el objetivo
     * @return True si la posición actual del agente es igual a la del objetivo y False en caso contrario
     */
    public boolean objetivoAlcanzado() {
        return entorno.obtenerPosAgente().sonIguales(entorno.obtenerPosObjetivo());
    }

    /**
     * @brief Mostramos el número de pasos o gasto de energía que nos ha supuesto alcanzar el obejivo
     * @return Texto finalizando la ejecución
     */
    public String enCuantosPasosHeLlegado() {
        // Buscar el sensor de tipo Energia en la lista de sensores
        Energia sensorEnergia = null;

        // Uso del bucle for tradicional para recorrer la lista de sensores
        for (int i = 0; i < this.sensores.size(); i++) {
            Sensor sensor = this.sensores.get(i);
            if (sensor instanceof Energia) {
                sensorEnergia = (Energia) sensor;
                break;
            }
        }

        // Asignación de numPasos usando if-else
        int numPasos;
        if (sensorEnergia != null) {
            numPasos = sensorEnergia.obtenerEnergia()-1;
        } else {
            numPasos = 0;
        }

        String texto = "He gastado la friolera de " + numPasos + " puntos de energia";

        return texto;
    }


    
    /**
     * @brief Función que usaremos para tomar la decisión de qué movimiento realizar
     */
    public void decidirMovimiento() {
        // Recuperamos las posiciones actuales
        Posicion posActual = entorno.obtenerPosAgente();
        Posicion posObjetivo = entorno.obtenerPosObjetivo();

        // Posibles movimientos según las acciones del enum
        Posicion mejorMovimiento = null;
        int mejorDistancia = Integer.MAX_VALUE; // Será un valor alto casi infinito

        // Evaluar cada acción del enumerado Accion
        for (Accion accion : Accion.values()) { // Para cada Acción posible:
            Posicion delta = accion.obtenerDelta();  // Llamar al método que obtiene el delta
            // Calculamos la que sería la nueva posición agregándo delta a la posición actual
            Posicion movimiento = new Posicion(posActual.obtenerX() + delta.obtenerX(), posActual.obtenerY() + delta.obtenerY());

            // Verificar si el movimiento es válido (dentro de los límites del mapa y no es un obstáculo)
            if (entorno.posEsValida(movimiento)) {
                // Si el movimiento lleva directamente al objetivo, seleccionarlo
                if (movimiento.sonIguales(posObjetivo)) {
                    mejorMovimiento = movimiento;
                    break;
                }

                // Calcular la distancia Manhattan al objetivo
                int distancia = calcularDistanciaManhattan(movimiento, posObjetivo);
                if (distancia < mejorDistancia) {
                    mejorDistancia = distancia;
                    mejorMovimiento = movimiento;
                }
            }
        }

        // Si se encontró un movimiento válido, actualizar la memoria
        if (mejorMovimiento != null) {
            this.sensores = this.entorno.actualizarPercepciones(mejorMovimiento);
        }
    }
    
    /**
     * @brief Calcula la distancia Manhattan entre 2 posiciones
     * @param p1 Primera posición
     * @param p2 Segunda posición
     * @return Distancia Manhattan entre p1 y p2
     */
    private int calcularDistanciaManhattan(Posicion p1, Posicion p2) {
        return Math.abs(p1.obtenerX() - p2.obtenerX()) + Math.abs(p1.obtenerY() - p2.obtenerY());
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
        this.mapaMemoria.mostrarMapa();
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

}
