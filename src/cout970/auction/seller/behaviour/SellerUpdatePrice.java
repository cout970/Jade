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

    public SellerUpdatePrice(Seller a, Auction auction) {
        super(a, period);
        this.auction = auction;
    }

    @Override
    public Seller getAgent() {
        return (Seller) super.getAgent();
    }

    @Override
    protected void onTick() {

        if (auction.getInterestedBuyers().size() > 1) {
            // Busca nuevos compradores que se incorporen ahora
            getAgent().sendAuctionAnnouncement(auction);

            // incremento del valor
            auction.increasePrice();

            // Envia el nuevo precio a los asistentes de la subasta
            getAgent().sendPriceToBuyers(auction);

        } else if (auction.getInterestedBuyers().size() == 1) {
            // solo uno ha pujado, gana instantaneamente por falta de competencia
            onlyOneBid();

        } else if (auction.getInterestedBuyers().isEmpty()) {
            // Nadie ha pujado en este precio pero si con el precio anterior
            zeroBids();

        } else {
            throw new IllegalStateException("Pujas: " + auction.getInterestedBuyers().size());
        }
    }

    private void zeroBids() {
        List<AID> buyers = auction.getLastInterestedBuyers();
        if (buyers.isEmpty()) {
            getAgent().addEvent(new Event("Fin de la subasta", "Nadie interesado"));
        } else {
            AID winner = buyers.get(0);
            getAgent().addEvent(new Event("Fin de la subasta", "Ganador: " + winner.getLocalName() + ", por el precio: " + auction.getLastPrice()));
            informWinner(winner, new Bid(auction.getBook(), auction.getLastPrice()));
        }
        endAuction();
    }

    private void onlyOneBid() {
        AID winner = auction.getInterestedBuyers().get(0);
        getAgent().addEvent(new Event("Fin de la subasta", "Ganador: " + winner.getLocalName() + ", por el precio: " + auction.getCurrentPrize()));
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

        String end = "";
        if (bid.getPrice() >= auction.getReservationPrize()) {
            end = "\nSe ha vendido el libro por " + String.format("%.2f", bid.getPrice());
        } else {
            end = "\nPero no se ha alcanzado el precio minimo: " + String.format("%.2f", auction.getReservationPrize()) +
                    ", precio pujado: " + String.format("%.2f", bid.getPrice());
        }
        JOptionPane.showMessageDialog(null, "[" + bid.getBook().getTitle() + "]" +
                " El ganador es " + winner.getLocalName() + end);

    }

    private void endAuction() {
        auction.onEnd();
        stop();
    }
}
