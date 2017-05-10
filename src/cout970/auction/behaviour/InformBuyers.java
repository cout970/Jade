package cout970.auction.behaviour;

import cout970.auction.util.YellowPages;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

/**
 * Created by cout970 on 5/8/17.
 */
public class InformBuyers extends TickerBehaviour {

    private static final long PERIOD = 2000L;
    private static final SLCodec CODEC = new SLCodec();

    public InformBuyers(Agent a) {
        super(a, PERIOD);
    }

    @Override
    protected void onTick() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        addAllReceivers(msg);
        msg.setSender(getAgent().getAID());
        msg.setLanguage(CODEC.getName());
        msg.setContent("Hola mundo");
        msg.setConversationId("inform-auction");

        getAgent().send(msg);
    }

    private void addAllReceivers(ACLMessage msg){
        List<AID> ids = YellowPages.search(getAgent());

        for (AID id : ids) {
            msg.addReceiver(id);
        }
    }
}
