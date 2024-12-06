package tutorial_alberto;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class EmisorAgent extends Agent{
    private int step=0;
    private boolean finishConversation=false;
    
    @Override
    protected void setup(){
        
        while(!finishConversation){
            switch (this.step){
                case 0 -> {
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.addReceiver(new AID("alberto-receptor",AID.ISLOCALNAME));
                    msg.setContent("Hola receptor, como esta el tio? :P");
                    msg.setConversationId("CONVERSACION-AGRADECIMIENTO");
                    send(msg);
                    this.step=1;
                }

                case 1 -> {
                    ACLMessage msg = blockingReceive();

                    if(msg.getConversationId().equals("CONVERSACION-AGRADECIMIENTO")
                            && msg.getPerformative() == ACLMessage.AGREE){
                        
                        ACLMessage respuesta=msg.createReply(ACLMessage.INFORM);
                        respuesta.setContent("Estoy saludando usando una conversacion protocolizada");
                        send(respuesta);
                        step=2;
                    }else{
                        System.out.println("\nError en el protocolo de conversacion - step"+1);
                        doDelete();
                    }
                }
                
                case 2 -> {
                    ACLMessage msg = blockingReceive();
                    
                    if(msg.getConversationId().equals("CONVERSACION-AGRADECIMIENTO")
                            && msg.getPerformative() == ACLMessage.INFORM){
                        
                        System.out.println("El emisor ha recibido el siguiente mensaje: \n->R-'" + msg.getContent() + "'");
                        finishConversation=true;
                    }else{
                        System.out.println("Error en el protocolo de conversacion - step"+2);
                    }
                }

                default -> {
                    System.out.println("\nError en el protocolo de conversacion - step "+2);
                    doDelete();
                }
            }
        }
        
        doDelete();
    }
    
    @Override
    public void takeDown(){
        System.out.println("\n-Emisor: El agente " + getLocalName() + " ha finalizado. \n");
    }
}


// /////////////////////// //
// TUTORIAL INICIAL BÁSICO //
// /////////////////////// //
/*
@Override
protected void setup(){
    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

    msg.addReceiver(new AID("alberto-receptor",AID.ISLOCALNAME));
    msg.setContent("Hola agente");

    send(msg);

    doDelete();
}*/

// ////////////////////// //
// SEGUNDA PARTE TUTORIAL //
// ////////////////////// //

/*
@Override
protected void setup(){

    while(!finishConversation){
        switch (this.step){
            case 0 -> {
                ACLMessage msg = new ACLMessage();
                msg.addReceiver(new AID("alberto-receptor",AID.ISLOCALNAME));
                msg.setContent("E - 'Hola agente. Como esta el tio? :P");
                msg.setConversationId("conversacion-agradecimiento");
                send(msg);
                this.step=1;
            }

            case 1 -> {
                ACLMessage msg = blockingReceive();

                if(msg.getConversationId().equals("conversacion-agradecimiento")){
                    System.out.println("El emisor recibe este mensaje como respuesta: \n->R - '" + msg.getContent() + "'");
                    this.finishConversation = true;
                    System.out.println("\nE-Fin de la conversacion");
                }else{
                    System.out.println("\nError en el protocolo de conversacion");
                    doDelete();
                }
            }

            default -> {
                System.out.println("\nError en el protocolo de conversacion");
                doDelete();
            }
        }
    }

    doDelete();
}*/
