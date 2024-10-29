
package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



/**
 * @class Mapa
 * 
 * @brief Clase que representa un mapa bidimensional.
 */
public class Mapa {
    /**
     * @brief Número de filas del mapa.
     */
    private int filas;
    
    /**
     * @brief Número de columnas del mapa.
     */
    private int columnas;
    
    /**
     * @brief Matriz bidimensional que contendrá los caracteres que representan 
     * el entorno del mapa.
     */
    private int[][] mapa;

    
    /**
     * @brief Constructor por parámetro. Construye la matriz a partir de un 
     * fichero de texto plano, donde las dos primeras filas de este contienen el
     * número de filas y columnas (respectivamente) de la matriz y el resto de
     * líneas son los caracteres que conforman la matriz, separados por 
     * tabuladores.
     * 
     * @param nombreFicheroMapa Nombre del fichero que contiene los datos del 
     * mapa
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Mapa(String nombreFicheroMapa) 
            throws FileNotFoundException, IOException {
        
        FileReader lector     = new FileReader(nombreFicheroMapa);
        BufferedReader buffer = new BufferedReader(lector);
        
        // Leer las filas y las columnas del mapa (primera y segunda línea, 
        // respectivamente):
        filas    = Integer.parseInt(buffer.readLine().trim());
        columnas = Integer.parseInt(buffer.readLine().trim());
        
        // Crear la matriz con las dimensiones almacenadas:
        mapa     = new int[filas][columnas];
        
        // Finalmente, leer los caracteres del mapa:
        for (int i = 0; i < filas; i++) {
            String[] valores = buffer.readLine().trim().split("\t");
            
            for (int j = 0; j < columnas; j++) {
                mapa[i][j] = Integer.parseInt(valores[j]);
            }
        }
        
        buffer.close();
    }
   
    /**
     * @brief Consultor para el número de filas.
     * 
     * @return Número de filas del mapa.
     */
    public int obtenerNumFilas() {
        return (filas);
    }
    
    /**
     * @brief Consultor para el número de columnas.
     * 
     * @return Número de columnas del mapa.
     */
    public int obtenerNumColumnas() {
        return (columnas);
    }
    
    /**
     * @brief Consultor para el mapa.
     * 
     * @return Matriz bidimensional con todos los elementos del mapa.
     */
    public int[][] obtenerMapa() {
        return (mapa);
    }
    
    /**
     * @brief Consultor del elemento de una casilla especificada.
     * 
     * @param fila Fila de la casilla a consultar.
     * @param columna Columna de la casilla a consultar.
     * 
     * @return El elemento de una casilla especificada.
     */
    public int obtenerCasilla(int fila, int columna) {
        int elemento = 1;
                
        try {
            elemento = mapa[fila][columna];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: las coordenadas pasadas no son válidas");
        }
        
        return (elemento);
    }
    
    /**
     * @brief Muestra el mapa de una manera simple.
     */
    public void mostrarMapa() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // Usamos tabulacioens para separar columnas
                System.out.print(mapa[i][j] + "\t"); 
            }
            
            System.out.println();
            // System.out.println();
        }
    }
}
