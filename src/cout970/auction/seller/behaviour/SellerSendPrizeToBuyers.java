package cout970.auction.seller.behaviour;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.MsgBuilder;
import cout970.ontology.Bid;
import cout970.ontology.IncreasePrice;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerSendPrizeToBuyers extends OneShotBehaviour {

    private Auction auction;

    public SellerSendPrizeToBuyers(Seller a, Auction auction) {
        super(a);
        this.auction = auction;
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {

        Bid bid = new Bid(auction.getBook(), auction.getCurrentPrize());

        ACLMessage msg = new MsgBuilder()
                .setPerformative(ACLMessage.INFORM)
                .setSender(getAgent())
                .setReceivers(auction.getBuyers())
                .setConversationId("cfp-1")
                .setContentObj(new IncreasePrice(bid))
                .setContentManager(getAgent().getContentManager())
                .build();

        getAgent().send(msg);
    }
}
