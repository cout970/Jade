package cout970.auction.seller.behaviour;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.Event;
import cout970.auction.util.MsgBuilder;
import cout970.auction.util.YellowPages;
import cout970.ontology.Bid;
import cout970.ontology.StartAuction;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

/**
 * Created by cout970 on 5/8/17.
 */
public class SellerInformAuction extends OneShotBehaviour {

    private static final SLCodec CODEC = new SLCodec();

    private Auction auction;

    public SellerInformAuction(Seller a, Auction auction) {
        super(a);
        this.auction = auction;
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {
        updateBuyers(auction);
        Bid bid = new Bid(auction.getBook(), auction.getCurrentPrize());

        ACLMessage msg = new MsgBuilder()
                .setPerformative(ACLMessage.INFORM)
                .setSender(getAgent())
                .setReceivers(auction.getBuyers())
                .setConversationId("inform-of-auction")
                .setContentObj(new StartAuction(bid))
                .setContentManager(getAgent().getContentManager())
                .build();

        getAgent().send(msg);
    }

    private void updateBuyers(Auction auction) {
        List<AID> ids = YellowPages.search(getAgent());
        auction.getBuyers().clear();
        auction.getBuyers().addAll(ids);
    }
}
