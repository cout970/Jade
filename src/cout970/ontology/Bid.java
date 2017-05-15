package cout970.ontology;


/**
 * Protege name: Bid
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2017/05/15, 12:25:40
 */
public class Bid implements jade.content.Concept {

    private Book book;
    private float price;

    public Bid() {
    }

    public Bid(Book book, float price) {
        this.book = book;
        this.price = price;
    }

    /**
     * Protege name: book
     */
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Protege name: price
     */
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
