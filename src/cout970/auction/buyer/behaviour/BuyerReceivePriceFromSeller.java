package cout970.auction.buyer.behaviour;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.auction.util.MsgBuilder;
import cout970.auction.util.MsgHelper;
import cout970.ontology.Bid;
import cout970.ontology.BidUp;
import cout970.ontology.IncreasePrice;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;

/**
 * Created by cout970 on 2017/05/11.
 */
public class BuyerReceivePriceFromSeller extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("cfp-1", msg.getConversationId()));

    public BuyerReceivePriceFromSeller(Buyer a) {
        super(a);
    }

    @Override
    public Buyer getAgent() {
        return (Buyer) super.getAgent();
    }

    @Override
    public void action() {
        try {
            ACLMessage msg = getAgent().receive(template);
            if (msg == null) {
                return;
            }

            IncreasePrice content = MsgHelper.getContentObj(msg, getAgent().getContentManager());
            Bid bid = content.getBid();
            AuctionRef ref = getAgent().getAuctions().get(bid.getBook());

            if (ref == null || !msg.getSender().equals(ref.getSeller()) || !ref.isActive()) {
                return; // Ignorado
            }

            //Debug
            if (Math.random() > 0.05) { // puja

                sendMsg(ref, bid, ACLMessage.PROPOSE);
            } else { // no puja

                ref.setActive(false);
                sendMsg(ref, bid, ACLMessage.NOT_UNDERSTOOD);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
