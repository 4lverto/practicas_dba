package modelo.sensores;

import modelo.Entorno;
import modelo.Mapa;
import modelo.Posicion;

/**
 * @class Vision
 * 
 * @brief Clase que representa un sensor de visión.
 * Este sensor obtiene las celdas contiguas visibles alrededor del agente.
 */
public class Vision extends Sensor {
    
    /**
     * @brief Matriz de enteros que representa las celdas contiguas visibles.
     */
    private int[][] celdasContiguas;
    
    

    /**
     * @brief Constructor de Vision, inicializa la matriz de celdas contiguas.
     * 
     * @param entorno Instancia del entorno.
     */
    public Vision(Entorno entorno) {
        // Inicializar la matriz de celdas contiguas según el entorno o el 
        // tamaño que desees:
        // Por ejemplo, una visión de 3x3 alrededor del agente:
        this.celdasContiguas = new int[3][3];
        this.entorno         = entorno;
        
        // Añadirse al entorno (observado) como observador:
        this.entorno.registrarSensor(this);
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
     * Actualiza el sensor de visión. (En una implementación real, este método
     * debería llenar celdasContiguas con los datos de las celdas visibles).
     */
    @Override
    public void actualizar() {
        Posicion posAgente = entorno.obtenerPosAgente();
        int x              = posAgente.obtenerX();
        int y              = posAgente.obtenerY();
        Mapa mapa          = entorno.obtenerMapa();
        
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
        
        // Actualizar su posición en particular:
        if (mapa.casillaEsValida(x, y)) {
            celdasContiguas[1][1] = mapa.obtenerCasilla(x, y);
        } else {
            celdasContiguas[1][1] = -1;
        }
    }
}
