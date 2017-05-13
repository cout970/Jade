package cout970.auction.seller.behaviour;

import cout970.auction.Bid;
import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
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
        Auction auction = getAgent().getAuction();

        System.out.print("[" + getAgent().getLocalName() + "] Received msg from buyer: " + msg.getSender().getLocalName() + "; ");
        System.out.println(accept ? "Acepta la puja de " + bid.getPrice() : "Se retira");

        if (!accept) {
            return;
        }
        int perfomative;

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
                .build();

        getAgent().send(response);
    }
}
