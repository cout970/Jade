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

    private Book book;

    public void updateBuyers(List<AID> newBuyers){
        buyers = new ArrayList<>(newBuyers);
    }

    public List<AID> getBuyers() {
        return buyers;
    }

    public float getCurrentPrize() {
        return currentPrize;
    }

    public Book getBook() {
        return book;
    }
}
