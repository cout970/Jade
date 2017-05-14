package cout970.auction.seller.behaviour;

import cout970.auction.domain.Bid;
import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.Event;
import cout970.auction.util.MsgBuilder;
import cout970.auction.util.MsgHelper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerListenBids extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("auction-bid", msg.getConversationId()));

    public SellerListenBids(Seller a) {
        super(a);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(template);
        if (msg == null) {
            return;
        }

        boolean accept = msg.getPerformative() == ACLMessage.PROPOSE;
        Bid bid = MsgHelper.getContentObj(msg);
        Auction auction = getAgent().getAuctions().get(bid.getBook());
        if (auction == null) {
            return;
        }

        if (!accept) {
            return;
        }
        int perfomative;
        getAgent().addEvent(new Event("Puja", msg.getSender().getLocalName() + " ha pujado "+String.format("%.2f", bid.getPrice())));

        if (bid.getPrice() == auction.getCurrentPrize()) {
            perfomative = ACLMessage.ACCEPT_PROPOSAL;
            auction.getInterestedBuyers().add(msg.getSender());
        } else {
            perfomative = ACLMessage.REJECT_PROPOSAL;
        }

        ACLMessage response = new MsgBuilder()
                .setPerformative(perfomative)
                .setSender(getAgent())
                .setReceiver(msg.getSender())
                .setConversationId("bid-response")
                .setContentObj(bid)
                .setContentManager(getAgent().getContentManager())
                .build();

        getAgent().send(response);
    }
}
