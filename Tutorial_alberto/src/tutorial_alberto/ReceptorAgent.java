package tutorial_alberto;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class ReceptorAgent extends Agent{
    @Override
    protected void setup(){
        ACLMessage msg = blockingReceive();
        System.out.println(msg);
        doDelete();
    }
    
    @Override
    public void takeDown(){
        System.out.println("\n-Receptor: El agente " + getLocalName() + " ha finalizado. \n");
    }
}
