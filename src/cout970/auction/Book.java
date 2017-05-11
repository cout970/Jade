package cout970.auction;

import java.io.Serializable;

/**
 * Created by cout970 on 5/10/17.
 */
public class Book implements Serializable {

    private String ISBN;
    private String name;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }

        Book book = (Book) o;

        return ISBN != null ? ISBN.equals(book.ISBN) : book.ISBN == null;
    }

    @Override
    public int hashCode() {
        return ISBN != null ? ISBN.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
