package cout970.auction.seller;

import cout970.auction.seller.behaviour.SellerListenBids;
import cout970.auction.seller.behaviour.SellerSendPrizeToBuyers;
import cout970.auction.seller.behaviour.SellerInformAuction;
import cout970.auction.seller.behaviour.SellerUpdatePrice;
import cout970.auction.seller.gui.SellerGui;
import cout970.auction.util.Event;
import cout970.auction.util.MsgBuilder;
import cout970.ontology.AuctionOntology;
import cout970.ontology.Book;
import jade.core.Agent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by cout970 on 5/8/17.
 */
public class Seller extends Agent {

    private Map<Book, Auction> auctions = new HashMap<>();
    private List<Event> events = new ArrayList<>();
    private List<Consumer<Event>> eventListeners = new ArrayList<>();
    private JFrame gui;

    @Override
    protected void setup() {
        getContentManager().registerLanguage(MsgBuilder.CODEC);
        getContentManager().registerOntology(AuctionOntology.getInstance());

        gui = SellerGui.startGui(this);
        addBehaviour(new SellerListenBids(this));
    }

    public void startAuction(Book book, float initialPrice, float reservationPrice, float increment) {
        Auction auction = new Auction(initialPrice, reservationPrice, increment, book);
        auctions.put(book, auction);

        announceAuction(auction);
        addBehaviour(new SellerUpdatePrice(this, auction));
        addEvent(new Event("Inicio de la subasta", "Iniciada la subasta del libro: " + book.getTitle()));
    }

    public void sendPriceToBuyers() {
        addBehaviour(new SellerSendPrizeToBuyers(this));
    }

    public void announceAuction(Auction auction) {
        addBehaviour(new SellerInformAuction(this, auction));
    }

    public JFrame getGui() {
        return gui;
    }

    @Override
    protected void takeDown() {
        gui.setVisible(false);
        gui = null;
    }

    public Map<Book, Auction> getAuctions() {
        return auctions;
    }

    public void stop() {
        doDelete();
    }

    public void addEvent(Event e) {
        events.add(e);
        eventListeners.forEach((it) -> it.accept(e));
        System.out.println(e.getTitle() + ": " + e.getDescription());
    }

    public void registerListener(Consumer<Event> listener) {
        eventListeners.clear();
        eventListeners.add(listener);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Consumer<Event>> getListener() {
        return eventListeners;
    }
}
