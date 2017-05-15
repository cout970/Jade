package cout970.auction.buyer;

import cout970.auction.buyer.behaviour.BuyerListenBidResponse;
import cout970.auction.buyer.behaviour.BuyerListenStartAuction;
import cout970.auction.buyer.behaviour.BuyerReceivePriceFromSeller;
import cout970.auction.util.MsgBuilder;
import cout970.auction.util.YellowPages;
import cout970.ontology.AuctionOntology;
import cout970.ontology.Book;
import jade.core.Agent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cout970 on 5/8/17.
 */
public class Buyer extends Agent {

    private Map<Book, AuctionRef> auctions = new ConcurrentHashMap<>();

    @Override
    protected void setup() {
        YellowPages.register(this);

        getContentManager().registerLanguage(MsgBuilder.CODEC);
        getContentManager().registerOntology(AuctionOntology.getInstance());

        addBehaviour(new BuyerListenStartAuction(this));
        addBehaviour(new BuyerReceivePriceFromSeller(this));
        addBehaviour(new BuyerListenBidResponse(this));
    }

    @Override
    protected void takeDown() {
        YellowPages.unregister(this);
        super.takeDown();
    }

    public Map<Book, AuctionRef> getAuctions() {
        return auctions;
    }
}
