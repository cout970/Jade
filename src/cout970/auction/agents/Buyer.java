package cout970.auction.agents;

import cout970.auction.AuctionRef;
import cout970.auction.Book;
import cout970.auction.behaviour.ListenStartAuction;
import cout970.auction.util.YellowPages;
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
        addBehaviour(new ListenStartAuction(this));
    }

    public Map<Book, AuctionRef> getAuctions() {
        return auctions;
    }
}
