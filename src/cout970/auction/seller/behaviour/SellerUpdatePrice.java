package cout970.auction.seller.behaviour;

import cout970.auction.seller.Auction;
import cout970.auction.seller.Seller;
import cout970.auction.util.Event;
import cout970.auction.util.MsgBuilder;
import cout970.ontology.Bid;
import cout970.ontology.InformWinner;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerUpdatePrice extends TickerBehaviour {

    private static final long period = 10 * 1000L;

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
        if (auction.getInterestedBuyers().size() == 1) {
            oneWinner();
        } else if (auction.getInterestedBuyers().isEmpty()) {
            zeroBids();
        } else {
            System.out.println("[" + getAgent().getLocalName() + "] Interesados: " + auction.getInterestedBuyers().size());

            // incremento del valor
            auction.increasePrice();

            getAgent().addEvent(new Event("Subida de precio", "Se ha incrementado la puja a " + String.format("%.2f", auction.getCurrentPrize())));
            // send new price
            getAgent().sendPriceToBuyers();
        }
    }

    private void zeroBids() {
        List<AID> buyers = auction.getLastInterestedBuyers();
        if (buyers.isEmpty()) {
            getAgent().addEvent(new Event("Fin de la subasta", "Nadie interesado"));
        } else {
            AID winner = buyers.get(0);
            getAgent().addEvent(new Event("Fin de la subasta", "Ganador: " + winner.getLocalName()));
            informWinner(winner, new Bid(auction.getBook(), auction.getLastPrice()));
        }
        endAuction();
    }

    private void oneWinner() {
        AID winner = auction.getInterestedBuyers().get(0);
        informWinner(winner, new Bid(auction.getBook(), auction.getCurrentPrize()));
        endAuction();
    }

    private void informWinner(AID winner, Bid bid) {
        ACLMessage msg = new MsgBuilder()
                .setPerformative(ACLMessage.INFORM)
                .setSender(getAgent())
                .setReceivers(auction.getBuyers())
                .setConversationId("inform-end-of-auction")
                .setContentObj(new InformWinner(winner, bid))
                .setContentManager(getAgent().getContentManager())
                .build();

        getAgent().send(msg);
    }

    private void endAuction() {
        getAgent().getAuctions().remove(auction.getBook());
        auction.onEnd();
        stop();
    }
}
