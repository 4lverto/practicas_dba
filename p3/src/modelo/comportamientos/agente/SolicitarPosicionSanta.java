package modelo.comportamientos.agente;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;
import modelo.agentes.Agente;

public class SolicitarPosicionSanta extends OneShotBehaviour {

    private Agente agente;

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
        ACLMessage respuesta = agente.obtenerMensajeSanta().createReply(ACLMessage.INFORM);
        respuesta.setContent(normalizarTexto(agente.obtenerMensajeTraducido()));
        agente.send(respuesta);

        // Recibimos el mensaje cifrado con las coordenadas
        ACLMessage mensajeCoordenadas = agente.blockingReceive();

        if (mensajeCoordenadas.getPerformative() == ACLMessage.INFORM) {
            agente.modificarMensajeSanta(mensajeCoordenadas);
            agente.modificarPosicionObjetivo(mensajeCoordenadas.getContent());
        }
    }
}
