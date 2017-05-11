package cout970.auction.agents;

import cout970.auction.Auction;
import cout970.auction.Book;
import cout970.auction.behaviour.StartAuction;
import jade.core.Agent;

/**
 * Created by cout970 on 5/8/17.
 */
public class Seller extends Agent {

    private Auction auction;

    @Override
    protected void setup() {
        Book book = new Book("12345", "Libro 1");
        auction = new Auction(10.0f, 20.0f, book);

        addBehaviour(new StartAuction(this));
    }

    public Auction getAuction() {
        return auction;
    }
}
