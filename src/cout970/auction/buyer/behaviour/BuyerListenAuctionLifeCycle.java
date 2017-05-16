package cout970.auction.buyer.behaviour;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.auction.util.MsgHelper;
import cout970.ontology.Bid;
import cout970.ontology.Book;
import cout970.ontology.InformWinner;
import cout970.ontology.InformAuction;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * Created by cout970 on 5/10/17.
 */
public class BuyerListenAuctionLifeCycle extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("inform-of-auction", msg.getConversationId()) ||
                    Objects.equals("inform-end-of-auction", msg.getConversationId()));

    public BuyerListenAuctionLifeCycle(Buyer a) {
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

        if (Objects.equals(msg.getConversationId(), "inform-of-auction")) {
            start(msg);
        } else {
            stop(msg);
        }
    }

    private void stop(ACLMessage msg) {
        InformWinner content = MsgHelper.getContentObj(msg, getAgent().getContentManager());
        Bid bid = content.getBid();
        Map<Book, AuctionRef> auctions = getAgent().getAuctions();

        auctions.remove(bid.getBook());
        getAgent().getListener().accept("endAuction");
    }

    private void start(ACLMessage msg) {
        InformAuction content = MsgHelper.getContentObj(msg, getAgent().getContentManager());
        Bid bid = content.getBid();

        Map<Book, AuctionRef> auctions = getAgent().getAuctions();
        if (!auctions.containsKey(bid.getBook())) {
            AuctionRef ref = new AuctionRef(bid.getBook(), msg.getSender(), bid.getPrice());
            auctions.put(bid.getBook(), ref);
        }

        getAgent().getListener().accept("newAuction");
    }
}
