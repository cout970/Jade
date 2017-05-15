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

import javax.swing.*;
import java.util.List;

/**
 * Created by cout970 on 2017/05/11.
 */
public class SellerUpdatePrice extends TickerBehaviour {

    private static final long period = 10 * 1000L;

    private Auction auction;
    private int initialCounter = 0;

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

        // solo uno ha pujado, gana instantaneamente por falta de competencia
        if (auction.getInterestedBuyers().size() == 1) {
            oneWinner();

        } else if (auction.getInterestedBuyers().isEmpty()) {// Nadie ha pujado

            // Nadie ha pujado nunca
            if (auction.getLastInterestedBuyers().isEmpty() && initialCounter < 5) {
                initialCounter++;
                // send new price
                getAgent().announceAuction(auction);
                getAgent().sendPriceToBuyers();
                System.out.println("Esperando...");
            } else {
                // Nadie ha pujado en este precio pero si con el precio anterior
                zeroBids();
            }
        } else {

            // incremento del valor
            auction.increasePrice();

            // Busca nuevos compradores que se incorporen ahora
            getAgent().announceAuction(auction);
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
            getAgent().addEvent(new Event("Fin de la subasta", "Ganador: " + winner.getLocalName() + ", por el precio: "+auction.getLastPrice()));
            informWinner(winner, new Bid(auction.getBook(), auction.getLastPrice()));
        }
        endAuction();
    }

    private void oneWinner() {
        AID winner = auction.getInterestedBuyers().get(0);
        getAgent().addEvent(new Event("Fin de la subasta", "Ganador: " + winner.getLocalName() + ", por el precio: "+auction.getCurrentPrize()));
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

        JOptionPane.showMessageDialog(null, "["+bid.getBook().getTitle()+"] El ganador es "+winner.getLocalName());
    }

    private void endAuction() {
        getAgent().getAuctions().remove(auction.getBook());
        auction.onEnd();
        stop();
    }
}
