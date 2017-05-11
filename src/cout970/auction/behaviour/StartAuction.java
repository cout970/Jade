package cout970.auction.behaviour;

import cout970.auction.Auction;
import cout970.auction.Bid;
import cout970.auction.agents.Seller;
import cout970.auction.util.YellowPages;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.List;

/**
 * Created by cout970 on 5/8/17.
 */
public class StartAuction extends OneShotBehaviour {

    private static final SLCodec CODEC = new SLCodec();

    public StartAuction(Seller a) {
        super(a);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

        updateBuyers(getAgent().getAuction());

        for (AID id : getAgent().getAuction().getBuyers()) {
            msg.addReceiver(id);
        }

        msg.setSender(getAgent().getAID());
        msg.setLanguage(CODEC.getName());
        msg.setConversationId("inform-start-of-auction");
        try {
            Auction auction = getAgent().getAuction();
            Bid bid = new Bid(auction.getBook(), auction.getCurrentPrize());
            msg.setContentObject(bid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        getAgent().send(msg);
        getAgent().addBehaviour(new SendPrizeToBuyers(getAgent()));
    }

    private void updateBuyers(Auction auction){
        List<AID> ids = YellowPages.search(getAgent());
        auction.getBuyers().clear();
        auction.getBuyers().addAll(ids);
    }
}
