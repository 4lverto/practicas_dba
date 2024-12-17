
package modelo.agentes;

import jade.core.Agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author migue-maca
 */
public class ElfoTraductor extends Agent {
    
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this){
            
            @Override
            public void action(){
                ACLMessage mensaje = receive();
                
                if(mensaje!=null){
                    System.out.println("\n\t DEBUG -> Mensaje recibido " + mensaje.getContent());
                    
                    if(mensaje.getPerformative()== ACLMessage.REQUEST){
                        
                        String contenido = "Rakas Joulupukki Olen valmis vapaaehtoiseksi tehtävään Kiitos";
                        System.out.println("DEBUG -> Enviando respuesta: " + contenido);
    
                        ACLMessage respuesta = mensaje.createReply();
                        respuesta.setPerformative(ACLMessage.INFORM);
                        respuesta.setContent(contenido);
                        send(respuesta);
                        System.out.println("\nELFO TRADUCTOR -> Traduccion enviada al Agente Buscador");  
                    }else{
                        System.out.println("\nELFO TRADUCTOR -> Mensaje no valido");                     
                    }
                }else{
                    block();
                }  
            }     
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("Finalizado el agente " + this.getLocalName());
    }
}