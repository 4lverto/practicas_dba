package vista;

import modelo.Entorno;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * @class VistaGrafica
 * 
 * @brief Clase que representa la vista gráfica del simulador. Esta utiliza la
 * biblioteca Swing para representar el entorno (mapa, agente y casilla 
 * objetivo).
 */
public class VistaGrafica extends Vista {
    
    /**
     * @brief Ventana gráfica principal para el simulador.
     */
    private JFrame ventana;
    
    /**
     * @brief Panel para el mapa a visualizar.
     */
    private PanelMapa panelMapa;

    /**
     * @brief Constructor por parámetro. Inicializa la vista y crea la ventana
     * principal con una configuración por defecto.
     * 
     * @param entorno Instancia del entorno.
     */
    public VistaGrafica(Entorno entorno) throws IOException {
        this.entorno = entorno;
        // Añadirse al entorno (observado) como observador:
        this.entorno.registrarVista(this);
        
        // Reproducir la música de fondo al iniciar
        reproducirMusica("resources/fondo.wav"); // Ruta relativa

        this.ventana   = new JFrame("Prática 2 de DBA");
        this.panelMapa = new PanelMapa(entorno.obtenerMapa());
        
        // Configuración de la ventana principal:
        int anchoVentana = 
                entorno.obtenerMapa().obtenerNumColumnas() * PanelMapa.obtenerFactor();
        int altoVentana  = 
                entorno.obtenerMapa().obtenerNumFilas() * PanelMapa.obtenerFactor();
        
        this.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ventana.setLayout(new BorderLayout());
        this.ventana.add(this.panelMapa, BorderLayout.CENTER);
        this.ventana.setSize(anchoVentana, altoVentana);
        this.ventana.pack();
        this.ventana.setLocationRelativeTo(null); // Centra la ventana
        this.ventana.setVisible(true);
    }

    /**
     * @brief Actualiza la vista en función del estado del entorno.
     * Implementación del método abstracto de Vista.
     */
    @Override
    public void actualizar() {
        this.panelMapa.establecerMapa(this.entorno.obtenerMapa());
        this.panelMapa.repaint(); // Para repintar la pantalla.
    }

    /**
     * @brief Reproduce la música de fondo en un bucle.
     * 
     * @param archivoAudio Ruta al archivo de audio que se quiere reproducir.
     */
    private void reproducirMusica(String archivoAudio) {
        try {
            // Crear un objeto File con el archivo de audio
            File audioFile = new File(archivoAudio);

            // Verificar si el archivo existe
            if (!audioFile.exists()) {
                throw new IOException("El archivo de audio no existe: " + archivoAudio);
            }

            // Obtener el clip de audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();

            // Abrir el clip
            clip.open(audioStream);

            // Reproducir la canción en loop (para que se repita)
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException e) {
            System.out.println("El formato de archivo de audio no es compatible: " + archivoAudio);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error al intentar abrir el archivo de audio: " + e.getMessage());
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("No se pudo obtener un clip de audio: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
