package modelo.agentes.agente;

import jade.core.behaviours.OneShotBehaviour;
import modelo.sensores.Vision;

/**
 * @class ActualizarMemoria
 *
 * @brief Clase que representa el comportamiento del agente relativo a la
 * actualización de su memoria interna, lo cual se hace previamente a la
 * decisión del próximo movimiento a ejecutar.
 */
public class ActualizarMemoria extends OneShotBehaviour {

    /**
     * @brief Instancia del agente.
     */
    private final Agente agente;

    /**
     * @brief Constructor por parámetro. Asigna al agente.
     *
     * @param agente Agente del simulador.
     */
    public ActualizarMemoria(Agente agente) {
        this.agente = agente;
    }

    /**
     * @brief Sobreescritura del método 'action' de la clase 'Behaviour' del
     * framework JADE. Se encarga de desencadenar la acción de actualización de
     * la memoria.
     */
    @Override
    public void action() {
        int x = agente.entorno.obtenerPosAgente().obtenerFil();
        int y = agente.entorno.obtenerPosAgente().obtenerCol();

        Vision sensorVision = (Vision) agente.sensores.get(0);

        // Metemos en celdasContiguas la matriz de visión que devuelve
        // el sensor (las 8 posiciones de alrededor)
        int[][] celdasContiguas = sensorVision.obtenerVision();

        // Añadimos a la memoria del agente estas celdasContiguas teniendo
        // en cuenta la posición desde donde las vemos.
        for (int i = 0; i < celdasContiguas.length; i++) {
            for (int j = 0; j < celdasContiguas[i].length; j++) {

                // Para cada casilla le definimos su "valor" aplicando un 
                // desplazamiento en las coordenads (x,y) del agente.
                int nuevaX = x + (i - 1);
                int nuevaY = y + (j - 1);

                // Para cada celda contigua verificaremos si la posición es 
                // válida, en cuyo caso convertiremos el valor del sensor
                // a un valor adecuado (LIBRE u OBSTACULO)
                if (agente.mapaMemoria.casillaEsValida(nuevaX, nuevaY)) {
                    int valorCasilla;

                    if (celdasContiguas[i][j] == 0) {
                        valorCasilla = 1;
                    } else {
                        valorCasilla = celdasContiguas[i][j];
                    }
                    // Cada celda será actualizada en la memoria del agente, 
                    // para reflejar su estado real en función de la inmediata
                    // visión del agente
                    agente.mapaMemoria.establecerCasilla(nuevaX, nuevaY, valorCasilla);
                }
            }
        }
    }
}
