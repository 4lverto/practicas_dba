package tutorial_alberto;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class EmisorAgent extends Agent{
    private int step=0;
    private boolean finishConversation=false;
    
    @Override
    protected void setup(){
        switch (this.step){
            case 0 -> {
                ACLMessage msg = new ACLMessage();
                msg.addReceiver(new AID("alberto-receptor",AID.ISLOCALNAME));
                msg.setContent("E - Hola agente. Como esta el tio? :P");
                msg.setConversationId("conversacion-agradecimiento");
                send(msg);
                this.step=1;
            }
            
            case 1 -> {
                ACLMessage msg = blockingReceive();
                
                if(msg.getConversationId().equals("conversacion-agradecimiento")){
                    System.out.println("El emisor recibe este mensaje como respuesta: '" + msg.getContent() + "'");
                    this.finishConversation = true;
                    System.out.println("\nFin de la conversacion");
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