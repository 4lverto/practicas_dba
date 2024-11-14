
package vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import modelo.Mapa;


/**
 * @class PanelMapa
 * 
 * @brief Clase que representa el panel del mapa, y que mostrará cada celda
 * de un color específico según su tipo. 
 */
public class PanelMapa extends JPanel {
    
    private Image imagenLibre;
    private Image imagenObstaculo;
    private Image imagenObjetivo;
    private Image imagenVisitada;
    private Image imagenAgente;
       
    /**
     * @brief Factor a aplicar para el dimensionado del panel.
     */
    private static final int FACTOR = 65;
    
    /**
     * @brief Mapa a representar.
     */
   private Mapa mapa;

   /**
    * @brief Constructor por parámetro. Establece las dimensiones del panel.
    * 
    * @param mapa Mapa a representar.
    */
   public PanelMapa(Mapa mapa) {
        this.mapa = mapa;
        
        setPreferredSize(new Dimension(
               mapa.obtenerNumColumnas() * FACTOR, 
               mapa.obtenerNumFilas() * FACTOR));

        // Cargar las imágenes desde el sistema de archivos
        try {
            imagenLibre = ImageIO.read(new File("resources/LIBRE.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenAgente = ImageIO.read(new File("resources/AGENTE3.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenObjetivo = ImageIO.read(new File("resources/OBJETIVO2.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenObstaculo = ImageIO.read(new File("resources/OBSTACULO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
            imagenVisitada = ImageIO.read(new File("resources/VISITADO.jpg")).getScaledInstance(FACTOR, FACTOR, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /**
    * Método sobreescrito de JPanel para pintar el componente.
    * 
    * @param g Objeto con los gráficos para personalizar el panel.
    */
  @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (int i = 0; i < mapa.obtenerNumFilas(); i++) {
        for (int j = 0; j < mapa.obtenerNumColumnas(); j++) {
            // Elegir la imagen adecuada según el tipo de casilla
            Image imagen = null;
            switch (mapa.obtenerCasilla(i, j)) {
                case Mapa.LIBRE:
                    imagen = imagenLibre;
                    break;
                case Mapa.OBSTACULO:
                    imagen = imagenObstaculo;
                    break;
                case Mapa.OBJETIVO:
                    imagen = imagenObjetivo;
                    break;
                case Mapa.VISITADA:
                    imagen = imagenVisitada;
                    break;
                case Mapa.AGENTE:
                    imagen = imagenAgente;
                    break;
                default:
                    imagen = imagenLibre; // o alguna imagen para casillas desconocidas
                    break;
            }

            // Dibujar la imagen en la posición de la celda
            if (imagen != null) {
                g.drawImage(imagen, j * FACTOR, i * FACTOR, FACTOR, FACTOR, this);
            }
        }
    }
}


   /**
    * @brief Modificar para el mapa.
    * 
    * @param mapa Mapa nuevo a asignar.
    */
   public void establecerMapa(Mapa mapa) {
       this.mapa = mapa;
   }
   
   /**
    * @brief Consultor para el factor del panel.
    * 
    * @return Factor del panel.
    */
   public static int obtenerFactor() {
       return (FACTOR);
   }
}
