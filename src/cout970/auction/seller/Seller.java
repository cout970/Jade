package cout970.auction.seller;

import cout970.auction.seller.behaviour.*;
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

    public void stop() {
        doDelete();
    }

    // Crear
    public void createAuction(Book book, float initialPrice, float reservationPrice, float increment) {
        Auction auction = new Auction(initialPrice, reservationPrice, increment, book);
        auctions.put(book, auction);

        sendAuctionAnnouncement(auction);
        addBehaviour(new SellerStartAuction(this, auction));
        addEvent(new Event("Creacion de la subasta", "Libro: " + book.getTitle()));
    }

    // Actualizar precio
    public void sendPriceToBuyers(Auction auction) {
        addBehaviour(new SellerSendPrizeToBuyers(this, auction));
    }

    // Anunciar
    public void sendAuctionAnnouncement(Auction auction) {
        addBehaviour(new SellerInformAuction(this, auction));
    }

    // Empezar subasta
    public void startAuction(Auction auction) {
        addBehaviour(new SellerUpdatePrice(this, auction));
    }


    // GUI
    public JFrame getGui() {
        return gui;
    }

    @Override
    protected void takeDown() {
        gui.setVisible(false);
        gui = null;
    }

    // Getters & setters
    public Map<Book, Auction> getAuctions() {
        return auctions;
    }

    // Gui events

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
