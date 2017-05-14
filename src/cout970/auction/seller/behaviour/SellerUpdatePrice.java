package cout970.auction.seller.behaviour;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.Event;
import jade.core.behaviours.TickerBehaviour;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerUpdatePrice extends TickerBehaviour {

    private static final long period = 10 * 1000L;
    private static final float priceIncrease = 1.5f;

    private Auction auction;

    public SellerUpdatePrice(Seller a, Auction auction) {
        super(a, period);
        this.auction = auction;
        block(period);
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    protected void onTick() {
        if (auction.getInterestedBuyers().isEmpty()) {

            getAgent().getAuctions().remove(auction.getBook());
            getAgent().addEvent(new Event("Fin de la subasta", "Nadie interesado"));
            auction.onEnd();
            stop();
        } else {
            if (auction.getInterestedBuyers().size() == 1) {

                getAgent().addEvent(new Event("Fin de la subasta", "1 interesado"));
                getAgent().getAuctions().remove(auction.getBook());
                auction.onEnd();
                stop();
                return;
            }
            System.out.println("[" + getAgent().getLocalName() + "] Interesados: " + auction.getInterestedBuyers().size());

            auction.onIncreasePrice(auction.getCurrentPrize() + priceIncrease);

            getAgent().addEvent(new Event("Subida de precio", "Se ha incrementado la puja a " + String.format("%.2f", auction.getCurrentPrize())));
            // send new price
            getAgent().sendPriceToBuyers();
        }
    }
}
