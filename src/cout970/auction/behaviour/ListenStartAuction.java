package cout970.auction.behaviour;

import cout970.auction.AuctionRef;
import cout970.auction.Bid;
import cout970.auction.Book;
import cout970.auction.agents.Buyer;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * Created by cout970 on 5/10/17.
 */
public class ListenStartAuction extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("inform-start-of-auction", msg.getConversationId()));

    public ListenStartAuction(Buyer a) {
        super(a);
    }

    @Override
    public Buyer getAgent() {
        return (Buyer) super.getAgent();
    }

    @Override
    public void action() {
        try {
            ACLMessage msg = getAgent().blockingReceive(template);
            Bid bid = (Bid) msg.getContentObject();

            Map<Book, AuctionRef> auctions = getAgent().getAuctions();
            AuctionRef ref = new AuctionRef(bid.getBook(), msg.getSender(), bid.getPrice());

            auctions.put(bid.getBook(), ref);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
