package cout970.auction.behaviour;

import cout970.auction.AuctionRef;
import cout970.auction.Bid;
import cout970.auction.agents.Buyer;
import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by cout970 on 2017/05/11.
 */
public class ReceivePriceFromSeller extends CyclicBehaviour {

    private static final SLCodec CODEC = new SLCodec();
    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("cfp-1", msg.getConversationId()));

    public ReceivePriceFromSeller(Buyer a) {
        super(a);
    }

    @Override
    public Buyer getAgent() {
        return (Buyer) super.getAgent();
    }

    @Override
    public void action() {
        try {
            ACLMessage msg = getAgent().blockingReceive(template);
            Bid bid = (Bid) msg.getContentObject();
            AuctionRef ref = getAgent().getAuctions().get(bid.getBook());
            if (ref == null || !msg.getSender().equals(ref.getSeller())) {
                return;
            }

            // TODO https://cv.usc.es/pluginfile.php/227179/mod_resource/content/2/XC00031F.pdf

            //Debug
            if (Math.random() > 0.5) { // puja
                sendMsg(ref, bid, ACLMessage.PROPOSE);
            } else { // no puja
                sendMsg(ref, bid, ACLMessage.NOT_UNDERSTOOD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sendMsg(AuctionRef ref, Bid bid, int type) {
        ACLMessage msg = new ACLMessage(type);

        msg.addReceiver(ref.getSeller());
        msg.setSender(getAgent().getAID());
        msg.setLanguage(CODEC.getName());
        msg.setConversationId("auction-bid");
        try {
            msg.setContentObject(bid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getAgent().send(msg);
    }
}
