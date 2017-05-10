package cout970.auction;

/**
 * Created by cout970 on 5/10/17.
 */
public class Book {

    private String ISBN;
    private String name;
    // ...

    public Book(String ISBN, String name) {
        this.ISBN = ISBN;
        this.name = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getName() {
        return name;
    }
}
