package cout970.auction.buyer;

import cout970.auction.buyer.behaviour.BuyerListenBidResponse;
import cout970.auction.buyer.behaviour.BuyerListenAuctionLifeCycle;
import cout970.auction.buyer.behaviour.BuyerReceivePriceFromSeller;
import cout970.auction.buyer.gui.BuyerGui;
import cout970.auction.util.MsgBuilder;
import cout970.auction.util.YellowPages;
import cout970.ontology.AuctionOntology;
import cout970.ontology.Book;
import jade.core.Agent;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Created by cout970 on 5/8/17.
 */
public class Buyer extends Agent {

    private Map<Book, AuctionRef> auctions = new ConcurrentHashMap<>();
    private JFrame gui;
    private Consumer<String> listener;

    @Override
    protected void setup() {
        YellowPages.register(this);

        getContentManager().registerLanguage(MsgBuilder.CODEC);
        getContentManager().registerOntology(AuctionOntology.getInstance());

        gui = BuyerGui.startGui(this);

        addBehaviour(new BuyerListenAuctionLifeCycle(this));
        addBehaviour(new BuyerReceivePriceFromSeller(this));
        addBehaviour(new BuyerListenBidResponse(this));
    }

    public void stop() {
        doDelete();
    }

    @Override
    protected void takeDown() {
        YellowPages.unregister(this);
        if (gui != null) gui.setVisible(false);
        gui = null;
    }

    public JFrame getGui() {
        return gui;
    }

    public Map<Book, AuctionRef> getAuctions() {
        return auctions;
    }

    public void setListener(Consumer<String> listener) {
        this.listener = listener;
    }

    public Consumer<String> getListener() {
        return listener;
    }
}
