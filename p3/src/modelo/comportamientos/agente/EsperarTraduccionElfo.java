
package modelo.comportamientos.agente;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import modelo.agentes.Agente;
import java.text.Normalizer;
/**
 * @class EsperarTraduccionElfo
 * 
 * @brief Comportamiento para esperar la traducción del Elfo Traductor.
 */
public class EsperarTraduccionElfo extends CyclicBehaviour {
    
    /**
     * @brief Instancia del agente.
     */
    private Agente agente;

    /**
     * @brief Constructor por defecto para instanciar al agente.
     * 
     * @param agente Agente que se toma para la copia.
     */
    public EsperarTraduccionElfo(Agente agente) {
        this.agente = agente;
    }
    
    /**
     * @brief No se ni pa qué voy a usar esto pero bueno
     * @param texto
     * @return 
     */
    private String normalizarTexto(String texto){
        if(texto==null){
            return "";
        }
        return Normalizer.normalize(texto,Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").trim();
    }
    
    /**
     * @brief Método que encapsula la lógica de los mensajes.
     */
    @Override
    public void action() {
        ACLMessage respuesta = this.agente.receive();
        
        if (respuesta != null){
            
            System.out.println("\n\t DEBUG -> Mensaje recibido. Performativa: " + respuesta.getPerformative());
            
            if(respuesta.getPerformative() == 6) {
                String contenidoRecibido = respuesta.getContent();
                String contenidoEsperado = "Rakas Joulupukki Olen valmis vapaaehtoiseksi tehtävään Kiitos";

                System.out.println("\n\t DEBUG -> Contenido recibido normalizado: [" + contenidoRecibido + "]");
                System.out.println("\n\t DEBUG -> Contenido esperado normalizado: [" + contenidoEsperado + "]");
                String contenido=respuesta.getContent();
                System.out.println("\n\t DEBUG -> Contenido normalizado: " + contenido);
                
                if(contenido.equals(normalizarTexto("Rakas Joulupukki Olen valmis vapaaehtoiseksi tehtävään Kiitos"))){
                    System.out.println("\n Traducción recibida: " + respuesta.getContent());
                    this.agente.addBehaviour(new ProponerMisionSanta(
                        this.agente, 
                        respuesta.getContent()));
                    this.agente.removeBehaviour(this);
                }else{
                    System.err.println("\nERROR 1 -> Contenido del mensaje no valido: " + contenido);
                }
    
            }else{
                System.err.println("\nERROR 2 -> Mensaje no valido recibido");
            }
        }else{
            block();
        }

    }
    
}
