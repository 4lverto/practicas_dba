package tutorial_alberto;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class ReceptorAgent extends Agent{
    @Override
    protected void setup(){
        ACLMessage msg = blockingReceive();
        
        System.out.println("\nEl receptor recibe este mensaje: '" + msg.getContent() + "'");
        
        ACLMessage respuesta = msg.createReply();
        
        respuesta.setContent("\nBien!!, que alegria verte :D");
        send(respuesta);
        
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