package cout970.auction.buyer.behaviour;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.auction.util.MsgHelper;
import cout970.ontology.BidUp;
import cout970.ontology.Book;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;

/**
 * Created by cout970 on 2017/05/11.
 */
public class BuyerListenBidResponse extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("bid-response", msg.getConversationId()));

    public BuyerListenBidResponse(Buyer a) {
        super(a);
    }

    @Override
    public Buyer getAgent() {
        return (Buyer) super.getAgent();
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(template);
        if (msg == null) {
            return;
        }

        BidUp bidUp = MsgHelper.getContentObj(msg, getAgent().getContentManager());
        Book book = bidUp.getBid().getBook();
        AuctionRef auction = getAgent().getAuctions().get(book);
        if (auction == null) {
            return;
        }

        if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
            auction.setBidUp(true);
        } else {
            auction.setBidUp(false);
        }
        getAgent().getListener().accept("setBidUp");
    }
}
