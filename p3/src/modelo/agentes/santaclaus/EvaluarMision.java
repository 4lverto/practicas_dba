package modelo.agentes.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.text.Normalizer;

/**
 *
 * @author Germán López
 */
public class EvaluarMision extends OneShotBehaviour {

    /**
     * @brief Instancia del agente.
     */
    private final SantaClaus agente;

    /**
     * @brief Constructor por defecto.
     *
     * @param agente Agente que se toma para la copia.
     */
    public EvaluarMision(SantaClaus agente) {
        this.agente = agente;
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }

    @Override
    public void action() {
        ACLMessage msg = agente.blockingReceive();

        if (msg.getPerformative() == ACLMessage.PROPOSE) {

            String contenido = normalizarTexto(msg.getContent());
            System.out.println("\n\tSanta claus recibe: '" + contenido + "'");

            if (contenido.substring(0, 17).equals("Rakas Joulupukki ") && contenido.substring(contenido.length() - 7, contenido.length()).equals(" Kiitos")) {
                if (Math.floor(Math.random() * 10) <= 7) {
                    agente.mensaje = "Hyvaa joulua ManoloElMejor Nahdaan pian";
                    agente.mensajeAgente = msg;
                    agente.aceptado = true;
                } else {
                    agente.mensaje = "Hyvaa joulua NO ERES DIGNO Nahdaan pian";
                    agente.mensajeAgente = msg;
                }
            } else {
                System.out.println(("\nSANTA -> Error, mensaje en formato incorrecto"));
                agente.doDelete();
            }
        } else {
            System.out.println(("\nSANTA -> Error de protocolo"));
            agente.doDelete();
        }

    }

}
