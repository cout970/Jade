package cout970.auction.buyer;

import cout970.auction.buyer.behaviour.BuyerListenBidResponse;
import cout970.auction.buyer.behaviour.BuyerListenStartAuction;
import cout970.auction.buyer.behaviour.BuyerReceivePriceFromSeller;
import cout970.auction.domain.Book;
import cout970.auction.util.YellowPages;
import jade.content.lang.sl.SLCodec;
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

        getContentManager().registerLanguage(new SLCodec());
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
