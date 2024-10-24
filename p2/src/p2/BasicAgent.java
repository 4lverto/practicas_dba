package p2;

import jade.core.Agent;
import java.util.Iterator;

/**
 *
 * @author migue-maca
 */
public class BasicAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("\nHello world! I'm the basic agent.\n");
        System.out.println("My localname is " + getAID().getLocalName());
        System.out.println("My GUID is " + getAID().getName());
        System.out.println("My addresses are:");
        
        Iterator it = getAID().getAllAddresses();
        while (it.hasNext()) {
            System.out.println("- " + it.next());
        }
        
        // Terminar el agente:
        doDelete();
    }
    
    @Override
    public void takeDown() {
        System.out.println("Terminating agent...");
    }
}
