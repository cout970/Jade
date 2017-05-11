package cout970.auction;

import java.io.Serializable;

/**
 * Created by cout970 on 2017/05/11.
 */
public class Bid implements Serializable {

    private Book book;
    private float price;

    public Bid(Book book, float price) {
        this.book = book;
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "book=" + book +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bid)) {
            return false;
        }

        Bid bid = (Bid) o;

        if (Float.compare(bid.price, price) != 0) {
            return false;
        }
        return book != null ? book.equals(bid.book) : bid.book == null;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }
}
