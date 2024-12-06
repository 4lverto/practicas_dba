package tutorial_alberto;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class EmisorAgent extends Agent{
    @Override
    protected void setup(){
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        msg.addReceiver(new AID("alberto-receptor",AID.ISLOCALNAME));
        msg.setContent("Hola agente");
        
        send(msg);
        
        doDelete();
    }
    
    @Override
    public void takeDown(){
        System.out.println("\n-Emisor: El agente " + getLocalName() + " ha finalizado. \n");
    }
}

