package cout970.auction.agents;

import cout970.auction.Auction;
import cout970.auction.behaviour.InformBuyers;
import jade.core.Agent;

/**
 * Created by cout970 on 5/8/17.
 */
public class Seller extends Agent {

    private Auction auction;

    @Override
    protected void setup() {
        auction = new Auction();

        addBehaviour(new InformBuyers(this));
    }

    public Auction getAuction() {
        return auction;
    }
}
