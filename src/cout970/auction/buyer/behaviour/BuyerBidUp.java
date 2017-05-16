package cout970.auction.buyer.behaviour;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.util.MsgBuilder;
import cout970.ontology.Bid;
import cout970.ontology.BidUp;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by cout970 on 2017/05/16.
 */
public class BuyerBidUp extends OneShotBehaviour {

    private AuctionRef auction;

    public BuyerBidUp(Agent a, AuctionRef auction) {
        super(a);
        this.auction = auction;
    }

    @Override
    public void action() {
        if (auction.getMaxPrice() == -1) {
            return;
        }

        Bid bid = new Bid(auction.getBook(), auction.getLocalCurrentPrice());

        if (auction.getMaxPrice() >= bid.getPrice()) { // puja
            sendMsg(auction, bid, ACLMessage.PROPOSE);
        } else { // no puja
            auction.setBidUp(false);
            sendMsg(auction, bid, ACLMessage.NOT_UNDERSTOOD);
        }
    }

    private void sendMsg(AuctionRef ref, Bid bid, int type) {
        ACLMessage response = new MsgBuilder()
                .setPerformative(type)
                .setSender(getAgent())
                .setReceiver(ref.getSeller())
                .setConversationId("auction-bid")
                .setContentObj(new BidUp(bid))
                .setContentManager(getAgent().getContentManager())
                .build();

        getAgent().send(response);
    }
}
