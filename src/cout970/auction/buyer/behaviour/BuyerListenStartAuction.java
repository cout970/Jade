package cout970.auction.buyer.behaviour;

import cout970.auction.domain.Bid;
import cout970.auction.domain.Book;
import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.auction.util.MsgHelper;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * Created by cout970 on 5/10/17.
 */
public class BuyerListenStartAuction extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("inform-start-of-auction", msg.getConversationId()));

    public BuyerListenStartAuction(Buyer a) {
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

        Bid bid = MsgHelper.getContentObj(msg);

        Map<Book, AuctionRef> auctions = getAgent().getAuctions();
        AuctionRef ref = new AuctionRef(bid.getBook(), msg.getSender(), bid.getPrice());

        auctions.put(bid.getBook(), ref);
        System.out.println("["+getAgent().getLocalName()+"] Subscribed to an auction for the book: " + ref.getBook().getName());

    }
}
