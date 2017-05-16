package cout970.auction.buyer;

import cout970.ontology.Book;
import jade.core.AID;

/**
 * Created by cout970 on 2017/05/11.
 */
public class AuctionRef {

    private Book book;
    private AID seller;
    private float localCurrentPrice;
    private float maxPrice = -1;
    private boolean bidUp;

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

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getLocalCurrentPrice() {
        return localCurrentPrice;
    }

    public void setLocalCurrentPrice(float localCurrentPrice) {
        this.localCurrentPrice = localCurrentPrice;
    }

    public boolean hasBidUp() {
        return bidUp;
    }

    public void setBidUp(boolean pujado) {
        this.bidUp = pujado;
    }
}
