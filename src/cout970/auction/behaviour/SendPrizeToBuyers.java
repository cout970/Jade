package cout970.auction.behaviour;

import cout970.auction.Auction;
import cout970.auction.Bid;
import cout970.auction.agents.Seller;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SendPrizeToBuyers extends OneShotBehaviour {

    private static final SLCodec CODEC = new SLCodec();

    public SendPrizeToBuyers(Seller a) {
        super(a);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        for (AID aid : getAgent().getAuction().getBuyers()) {
            msg.addReceiver(aid);
        }

        msg.setSender(getAgent().getAID());
        msg.setLanguage(CODEC.getName());
        msg.setConversationId("cfp-1");
        try {
            Auction auction = getAgent().getAuction();
            Bid bid = new Bid(auction.getBook(), auction.getCurrentPrize());
            msg.setContentObject(bid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        getAgent().send(msg);
    }
}
