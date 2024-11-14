
package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import modelo.Mapa;


/**
 * @class PanelMapa
 * 
 * @brief Clase que representa el panel del mapa, y que mostrará cada celda
 * de un color específico según su tipo. 
 */
public class PanelMapa extends JPanel {
    
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
               switch (mapa.obtenerCasilla(i, j)) {
                   case Mapa.LIBRE: // Suelo libre
                       g.setColor(Color.WHITE);
                       break;
                   case Mapa.AGENTE: // Agente
                       g.setColor(Color.BLUE);
                       break;
                   case Mapa.OBJETIVO: // Objetivo
                       g.setColor(Color.GREEN);
                       break;
                   case Mapa.OBSTACULO: // Obstáculo
                       g.setColor(Color.DARK_GRAY);
                       break;

                   default: // Casilla desconocida
                       g.setColor(Color.RED);
                       break;
               }

               g.fillRect(j * FACTOR, i * FACTOR, FACTOR, FACTOR);
               g.setColor(Color.BLACK);
               g.drawRect(j * FACTOR, i * FACTOR, FACTOR, FACTOR);
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
