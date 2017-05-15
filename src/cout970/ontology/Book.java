package cout970.ontology;


/**
 * Protege name: Book
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2017/05/15, 12:25:40
 */
public class Book implements jade.content.Concept {

    private String ISBN;
    private String title;

    public Book() {
    }

    public Book(String ISBN, String title) {
        this.ISBN = ISBN;
        this.title = title;
    }

    /**
     * Protege name: ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * Protege name: title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (ISBN != null ? !ISBN.equals(book.ISBN) : book.ISBN != null) return false;
        return title != null ? title.equals(book.title) : book.title == null;
    }

    @Override
    public int hashCode() {
        int result = ISBN != null ? ISBN.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
