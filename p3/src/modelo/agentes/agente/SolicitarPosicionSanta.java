package modelo.agentes.agente;


import modelo.Posicion;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;

public class SolicitarPosicionSanta extends OneShotBehaviour {

    private final Agente agente;
    
    private Posicion leerPosicion(String mensaje) {
        String[] valores = mensaje.split(",");
        return new Posicion(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
    }
    
    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public SolicitarPosicionSanta(Agente agente) {
        this.agente = agente;
    }

    /**
     * @brief Funcion para normalizar el texto
     * @param texto
     * @return
     */
    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = agente.mensajeSanta.createReply(ACLMessage.INFORM);
        respuesta.setContent(normalizarTexto(agente.mensaje));
        agente.send(respuesta);
        
        // Espera a que Santa le responda con las coordenadas
        ACLMessage mensajeCoordenadas = agente.blockingReceive();

        if (respuesta.getPerformative() == ACLMessage.INFORM) {
            agente.mensajeSanta = mensajeCoordenadas;
            agente.posObjetivo = leerPosicion(mensajeCoordenadas.getContent());
            System.out.println("\n\tPosicion santa recibida: " + agente.posObjetivo.toString());
        }
    }
}
