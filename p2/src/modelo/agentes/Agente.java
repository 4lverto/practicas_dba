package modelo.agentes;

import jade.core.Agent;
import java.io.IOException;
import modelo.Entorno;
import modelo.Posicion;

/**
 * @brief Clase que representa nuestro agente.
 */
public class Agente extends Agent {
    
    private Entorno entorno;
    private Posicion posAgente;
    private Posicion posObjetivo;

    // Constructor por defecto
    public Agente() {
        // Inicializa valores por defecto si es necesario
    }

    public Agente(Posicion posAgente, Posicion posObjetivo, String nombreFicheroMapa) throws IOException {
        this.posAgente = posAgente;
        this.posObjetivo = posObjetivo;
        entorno = Entorno.obtenerInstancia(nombreFicheroMapa, posAgente, posObjetivo);
    }

    @Override
    protected void setup() {
        System.out.println("Agente iniciado: " + getLocalName());

        // Aquí puedes añadir comportamientos para el agente
        addBehaviour(new ComportamientoMoverse());
    }

    public Entorno obtenerEntorno() {
        return entorno;
    }

    public boolean objetivoAlcanzado() {
        return posAgente.sonIguales(posObjetivo);
    }
}
