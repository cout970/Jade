package cout970.ontology;


import jade.core.AID;

/**
 * Protege name: InformWinner
 *
 * @author OntologyBeanGenerator v4.1
 * @version 2017/05/15, 12:25:40
 */
public class InformWinner implements jade.content.AgentAction {

    private AID winner;
    private Bid bid;

    public InformWinner() {
    }

    public InformWinner(AID winner, Bid bid) {
        this.winner = winner;
        this.bid = bid;
    }

    /**
     * Protege name: winner
     */
    public AID getWinner() {
        return winner;
    }

    public void setWinner(AID winner) {
        this.winner = winner;
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
