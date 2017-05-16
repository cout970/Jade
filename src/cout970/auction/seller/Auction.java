package cout970.auction.seller;

import cout970.ontology.Book;
import jade.core.AID;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by cout970 on 5/10/17.
 */
public class Auction {

    private float priceIncrease;

    // Compradores en la subasta
    private List<AID> buyers = new ArrayList<>();
    // Compradores dispuestos a pujar
    private List<AID> interestedBuyers = new ArrayList<>();
    private float currentPrize;

    // Compradores que pujaron en la ultima ronda
    private List<AID> lastInterestedBuyers = new ArrayList<>();
    private float lastPrice;

    private float reservationPrize;
    private Book book;

    private AuctionState state = AuctionState.WAITING;

    private List<Consumer<Float>> listeners = new ArrayList<>();

    public Auction(float currentPrize, float reservationPrize, float increment, Book book) {
        this.currentPrize = currentPrize;
        this.priceIncrease = increment;
        this.reservationPrize = reservationPrize;
        this.book = book;
    }

    public void increasePrice() {

        float newPrice = currentPrize + priceIncrease;
        lastPrice = currentPrize;
        currentPrize = newPrice;

        lastInterestedBuyers.clear();
        lastInterestedBuyers.addAll(interestedBuyers);
        interestedBuyers.clear();

        listeners.forEach((it) -> it.accept(currentPrize));
    }

    public AuctionState getState() {
        return state;
    }

    public void setState(AuctionState state) {
        this.state = state;
    }

    public List<AID> getLastInterestedBuyers() {
        return lastInterestedBuyers;
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public void setListeners(List<Consumer<Float>> listeners) {
        this.listeners = listeners;
    }

    public List<AID> getBuyers() {
        return buyers;
    }

    public List<AID> getInterestedBuyers() {
        return interestedBuyers;
    }

    public float getCurrentPrize() {
        return currentPrize;
    }

    public float getReservationPrize() {
        return reservationPrize;
    }

    public Book getBook() {
        return book;
    }

    public void onEnd() {
        setState(AuctionState.FINISHED);
        listeners.forEach((it) -> it.accept(currentPrize));
    }
}
