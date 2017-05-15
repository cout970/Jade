package cout970.ontology;


/**
 * Protege name: IncreasePrice
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2017/05/15, 12:25:40
 */
public class IncreasePrice implements jade.content.AgentAction {

    private Bid bid;

    public IncreasePrice() {
    }

    public IncreasePrice(Bid bid) {
        this.bid = bid;
    }

    /**
     * Protege name: bid
     */
    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }
}
