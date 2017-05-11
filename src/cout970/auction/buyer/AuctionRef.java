package cout970.auction.buyer;

import cout970.auction.Book;
import jade.core.AID;

/**
 * Created by cout970 on 2017/05/11.
 */
public class AuctionRef {

    private Book book;
    private AID seller;
    private float localCurrentPrice;
    private boolean active = true;

    public AuctionRef(Book book, AID seller, float localCurrentPrice) {
        this.book = book;
        this.seller = seller;
        this.localCurrentPrice = localCurrentPrice;
    }

    public AID getSeller() {
        return seller;
    }

    public Book getBook() {
        return book;
    }

    public float getLocalCurrentPrice() {
        return localCurrentPrice;
    }

    public void setLocalCurrentPrice(float localCurrentPrice) {
        this.localCurrentPrice = localCurrentPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
