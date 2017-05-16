package cout970.auction.seller.behaviour;

import cout970.auction.seller.Auction;
import cout970.auction.seller.AuctionState;
import cout970.auction.seller.Seller;
import cout970.auction.util.Event;
import jade.core.behaviours.TickerBehaviour;

/**
 * Created by cout970 on 2017/05/16.
 */
public class SellerStartAuction extends TickerBehaviour {

    private static final long PERIOD = 2500;
    private int counter = 0;
    private Auction auction;

    public SellerStartAuction(Seller a, Auction auction) {
        super(a, PERIOD);
        this.auction = auction;
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    protected void onTick() {
        getAgent().sendAuctionAnnouncement(auction);
        if(counter < 4){
            counter++;
        }else{
            //Actualiza el estado
            auction.setState(AuctionState.RUNNING);
            //Actualiza la interfaz de usuario
            getAgent().addEvent(new Event("Inicio de la subasta", "Libro: " + auction.getBook()));

            // Envia el nuevo precio a los asistentes de la subasta
            getAgent().sendPriceToBuyers(auction);

            // Inicia el proceso de puja
            getAgent().startAuction(auction);
            // Para este behaviour
            stop();
        }
    }
}
