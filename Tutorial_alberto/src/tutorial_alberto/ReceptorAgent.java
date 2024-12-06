package tutorial_alberto;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class ReceptorAgent extends Agent{
    
    private int step=0;
    private boolean finishConversation=false;
    
    @Override
    protected void setup(){
        
        while(!finishConversation){
            
            switch(step){
               
                case 0 -> {
                    ACLMessage msg = blockingReceive();
        
                    System.out.println("\nEl receptor recibe este mensaje: \n->E-'" + msg.getContent() + "'");
                    
                    if(msg.getPerformative() == ACLMessage.REQUEST){
                    
                        ACLMessage respuesta = msg.createReply(ACLMessage.AGREE);
                        send(respuesta);
                        
                        step=1;
                    }else{
                        System.out.println("\nError en el protocolo de convesracion - step "+1);
                        doDelete();
                    }
                }
                
                case 1 -> {
                    ACLMessage msg = blockingReceive();
                    
                    System.out.println("\nEl receptor recibe este mensaje: \n->E-'" + msg.getContent() + "'");
                    
                    if(msg.getPerformative() == ACLMessage.INFORM){
                        ACLMessage respuesta = msg.createReply(ACLMessage.INFORM);
                        respuesta.setContent("Eyy vamos a trabajar!!");
                        send(respuesta);
                        
                        finishConversation=true;
                    }else{
                        System.out.println("\nError en el protocolo de convesracion - step "+1);
                        doDelete();
                    }
                }  
            }
            
            
        }
        
        doDelete();
    }
    
    @Override
    public void takeDown(){
        System.out.println("\n-Receptor: El agente " + getLocalName() + " ha finalizado. \n");
    }
}


// /////////////////////// //
// TUTORIAL INICIAL BÁSICO //
// /////////////////////// //
/*
@Override
protected void setup(){
    ACLMessage msg = blockingReceive();
    //System.out.println(msg);

    if(msg != null && msg.getPerformative() == ACLMessage.INFORM){
        System.out.println("-Receptor: Mensaje recibdo: '" + msg.getContent() + "'");
    }else{
        System.out.println("Mensaje no entenido o no esperado");
    }

    doDelete();
}*/

// ////////////////////// //
// SEGUNDA PARTE TUTORIAL //
// ////////////////////// //

/*
@Override
protected void setup(){
    ACLMessage msg = blockingReceive();

    System.out.println("\nEl receptor recibe este mensaje: \n->'" + msg.getContent() + "'");

    ACLMessage respuesta = msg.createReply();

    respuesta.setContent("Bien!!, que alegria verte :D");
    send(respuesta);

    doDelete();
}*/