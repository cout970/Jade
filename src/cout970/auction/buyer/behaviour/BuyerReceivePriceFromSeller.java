package cout970.auction.buyer.behaviour;

import cout970.auction.buyer.AuctionRef;
import cout970.auction.buyer.Buyer;
import cout970.auction.util.MsgHelper;
import cout970.ontology.Bid;
import cout970.ontology.IncreasePrice;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Objects;

/**
 * Created by cout970 on 2017/05/11.
 */
public class BuyerReceivePriceFromSeller extends CyclicBehaviour {

    private MessageTemplate template = new MessageTemplate(msg ->
            Objects.equals("cfp-1", msg.getConversationId()));

    public BuyerReceivePriceFromSeller(Buyer a) {
        super(a);
    }

    @Override
    public Buyer getAgent() {
        return (Buyer) super.getAgent();
    }

    @Override
    public void action() {
        try {
            ACLMessage msg = getAgent().receive(template);
            if (msg == null) {
                return;
            }

            IncreasePrice content = MsgHelper.getContentObj(msg, getAgent().getContentManager());
            Bid bid = content.getBid();
            AuctionRef ref = getAgent().getAuctions().get(bid.getBook());

            if (ref == null || !msg.getSender().equals(ref.getSeller())) {
                return; // Ignorado
            }

            ref.setLocalCurrentPrice(bid.getPrice());
            getAgent().bidUp(ref);
            getAgent().getListener().accept("changePrice");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
