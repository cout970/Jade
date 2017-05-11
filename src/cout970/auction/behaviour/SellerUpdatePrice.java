package cout970.auction.behaviour;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import jade.core.behaviours.TickerBehaviour;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerUpdatePrice extends TickerBehaviour {

    private static final long period = 10 * 1000L;
    private static final float priceIncrease = 1.5f;

    public SellerUpdatePrice(Seller a) {
        super(a, period);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    protected void onTick() {
        Auction auction = getAgent().getAuction();
        if (auction.getInterestedBuyers().isEmpty() && !auction.getBuyers().isEmpty()) {
            System.out.println("[" + getAgent().getLocalName() + "] Nadie interesado");
            stop();
        } else {
            if (auction.getInterestedBuyers().size() == 1) {
                System.out.println("Fin de la subasta");
                stop();
                return;
            }
            System.out.println("[" + getAgent().getLocalName() + "] Interesados: " + auction.getInterestedBuyers().size());
            auction.getInterestedBuyers().clear();
            //increment prize
            auction.setCurrentPrize(auction.getCurrentPrize() + priceIncrease);
            // send new price
            getAgent().sendPriceToBuyers();
        }
    }
}
