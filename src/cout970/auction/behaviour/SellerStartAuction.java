package cout970.auction.behaviour;

import cout970.auction.Bid;
import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.MsgBuilder;
import cout970.auction.util.YellowPages;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

/**
 * Created by cout970 on 5/8/17.
 */
public class SellerStartAuction extends OneShotBehaviour {

    private static final SLCodec CODEC = new SLCodec();

    public SellerStartAuction(Seller a) {
        super(a);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {

        Auction auction = getAgent().getAuction();
        updateBuyers(auction);
        Bid bid = new Bid(auction.getBook(), auction.getCurrentPrize());

        ACLMessage msg = new MsgBuilder()
                .setPerformative(ACLMessage.INFORM)
                .setSender(getAgent())
                .setReceivers(getAgent().getAuction().getBuyers())
                .setConversationId("inform-start-of-auction")
                .setContentObj(bid)
                .build();

        getAgent().send(msg);
        System.out.println("["+getAgent().getLocalName()+"] Starting auction");
        getAgent().sendPriceToBuyers();
    }

    private void updateBuyers(Auction auction) {
        List<AID> ids = YellowPages.search(getAgent());
        auction.getBuyers().clear();
        auction.getBuyers().addAll(ids);
    }
}
