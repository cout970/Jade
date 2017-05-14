package cout970.auction.util;

/**
 * Created by cout970 on 2017/05/13.
 */
public class Event {

    private String title;
    private String description;

    public Event(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
