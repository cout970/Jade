package cout970.auction.agents;

import cout970.auction.behaviour.BuyerGetInformed;
import cout970.auction.util.YellowPages;
import jade.core.Agent;

/**
 * Created by cout970 on 5/8/17.
 */
public class Buyer extends Agent {

    @Override
    protected void setup() {
        YellowPages.register(this);
        addBehaviour(new BuyerGetInformed(this));
    }
}
