package cout970.auction;

import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cout970 on 5/10/17.
 */
public class Auction {

    private List<AID> buyers = new ArrayList<>();
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
