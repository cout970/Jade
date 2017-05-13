package cout970.auction.seller.behaviour;

import cout970.auction.Bid;
import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.MsgBuilder;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerSendPrizeToBuyers extends OneShotBehaviour {

    public SellerSendPrizeToBuyers(Seller a) {
        super(a);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {
        System.out.println("["+getAgent().getLocalName()+"] Sending Prize To Buyers");
        Auction auction = getAgent().getAuction();
        Bid bid = new Bid(auction.getBook(), auction.getCurrentPrize());

        ACLMessage msg = new MsgBuilder()
                .setPerformative(ACLMessage.INFORM)
                .setSender(getAgent())
                .setReceivers(getAgent().getAuction().getBuyers())
                .setConversationId("cfp-1")
                .setContentObj(bid)
                .build();

        getAgent().send(msg);
    }
}