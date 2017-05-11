package cout970.auction.seller;

import cout970.auction.Book;
import cout970.auction.behaviour.SellerListenBids;
import cout970.auction.behaviour.SellerSendPrizeToBuyers;
import cout970.auction.behaviour.SellerStartAuction;
import cout970.auction.behaviour.SellerUpdatePrice;
import jade.core.Agent;

import javax.swing.*;

/**
 * Created by cout970 on 5/8/17.
 */
public class Seller extends Agent {

    private JFrame frame = new JFrame("SellerGui");
    private Auction auction;

    @Override
    protected void setup() {
        Book book = new Book("12345", "Libro 1");
        auction = new Auction(10.0f, 20.0f, book);

        addBehaviour(new SellerStartAuction(this));
        addBehaviour(new SellerListenBids(this));
        addBehaviour(new SellerUpdatePrice(this));
    }

    public Auction getAuction() {
        return auction;
    }

    public void sendPriceToBuyers() {
        addBehaviour(new SellerSendPrizeToBuyers(this));
    }
}
