package modelo.sensores;

import modelo.Mapa;
import modelo.Posicion;

/**
 * @class Vision
 * 
 * @brief Clase que representa un sensor de visión.
 *   Este sensor obtiene las celdas contiguas visibles alrededor del agente.
 */
public class Vision extends Sensor {
    
    /**
     * @brief Matriz de enteros que representa las celdas contiguas visibles.
     */
    private int[][] celdasContiguas;
    
    /**
     * @brief Mapa en el que se efectuará la visión.
     */
    private Mapa mapa;
    
    /**
     * @brief Posición del agente en el mapa.
     */
    private Posicion posAgente;
    
    

    /**
     * Constructor de Vision, inicializa la matriz de celdas contiguas.
     */
    public Vision(Mapa mapa, Posicion posAgente) {
        // Inicializar la matriz de celdas contiguas según el entorno o el tamaño que desees.
        this.celdasContiguas = new int[3][3]; // Por ejemplo, una visión de 3x3 alrededor del agente.
        this.mapa            = mapa;
        this.posAgente       = posAgente;
    }

    /**
     * Actualiza el sensor de visión. (En una implementación real, este método
     * debería llenar celdasContiguas con los datos de las celdas visibles).
     */
    @Override
    public void actualizar() {
        int x = posAgente.obtenerX();
        int y = posAgente.obtenerY();
        
        
        // Actualizar la diagonal superior izquierda:
        if (mapa.casillaEsValida((x-1), (y-1))) {
            celdasContiguas[0][0] = mapa.obtenerCasilla(x-1, y-1);
        } else {
            celdasContiguas[0][0] = -1;
        }
        
        
        // Actualizar la celda de arriba:
        if (mapa.casillaEsValida((x-1), y)) {
            celdasContiguas[0][1] = mapa.obtenerCasilla(x-1, y);
        } else {
            celdasContiguas[0][1] = -1;
        }
        
        // Actualizar la diagonal superior derecha:
        if (mapa.casillaEsValida((x-1), (y+1))) {
            celdasContiguas[0][2] = mapa.obtenerCasilla(x-1, y+1);
        } else {
            celdasContiguas[0][2] = -1;
        }
        
        // Actualizar la celda de la derecha:
        if (mapa.casillaEsValida(x, (y+1))) {
            celdasContiguas[1][2] = mapa.obtenerCasilla(x, y+1);
        } else {
            celdasContiguas[1][2] = -1;
        }
        
        // Actualizar la diagonal inferior derecha:
        if (mapa.casillaEsValida((x+1), (y+1))) {
            celdasContiguas[2][2] = mapa.obtenerCasilla(x+1, y+1);
        } else {
            celdasContiguas[2][2] = -1;
        }
        
        // Actualizar la celda de abajo:
        if (mapa.casillaEsValida((x+1), y)) {
            celdasContiguas[2][1] = mapa.obtenerCasilla(x+1, y);
        } else {
            celdasContiguas[2][1] = -1;
        }
        
        // Actualizar la diagonal inferior izquierda:
        if (mapa.casillaEsValida((x+1), y-1)) {
            celdasContiguas[2][0] =  mapa.obtenerCasilla(x+1, y-1);
        } else {
            celdasContiguas[2][0] = -1;
        }
                
        // Actualizar la celda de la izquierda:
        if (mapa.casillaEsValida(x, (y-1))) {
            celdasContiguas[1][0] = mapa.obtenerCasilla(x, y-1);
        } else {
            celdasContiguas[1][0] = -1;
        }
    }

    /**
     * Obtiene la matriz de celdas contiguas visibles.
     * 
     * @return Matriz de celdas contiguas visibles alrededor del agente.
     */
    public int[][] obtenerVision() {
        return celdasContiguas;
    }
    
    /**
     * @brief Actualiza la posición del agente, de manera que la actualización 
     * de las casillas contiguas en el mapa pueda efectuarse.
     * 
     * @param pos Posición actual del agente.
     */
    public void actualizarPosAgente(Posicion pos) {
        posAgente = pos;
    }
}
