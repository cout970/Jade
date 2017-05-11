package cout970.auction.seller;

import cout970.auction.Book;
import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cout970 on 5/10/17.
 */
public class Auction {

    // Compradores en la subasta
    private List<AID> buyers = new ArrayList<>();
    // Compradores dispuestos a pujar
    private List<AID> interestedBuyers = new ArrayList<>();
    private float currentPrize;
    private float reservationPrize;
    private Book book;

    public Auction(float currentPrize, float reservationPrize, Book book) {
        this.currentPrize = currentPrize;
        this.reservationPrize = reservationPrize;
        this.book = book;
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

    public void setCurrentPrize(float currentPrize) {
        this.currentPrize = currentPrize;
    }
}
