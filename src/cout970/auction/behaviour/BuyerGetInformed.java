package cout970.auction.behaviour;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;

/**
 * Created by cout970 on 5/10/17.
 */
public class BuyerGetInformed extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("inform-auction", msg.getConversationId()));

    public BuyerGetInformed(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().blockingReceive(template);
        System.out.println(msg);
    }
}
