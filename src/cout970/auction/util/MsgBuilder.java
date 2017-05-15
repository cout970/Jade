package cout970.auction.util;

import cout970.ontology.AuctionOntology;
import jade.content.AgentAction;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cout970 on 2017/05/11.
 */
public class MsgBuilder {

    public static final SLCodec CODEC = new SLCodec();

    private AID sender;
    private List<AID> receivers = new ArrayList<>();
    private String conversationId;
    private int performative;
    private String content;
    private AgentAction contentObj;
    private ContentManager contentManager;

    public MsgBuilder setSender(AID sender) {
        this.sender = sender;
        return this;
    }

    public MsgBuilder setSender(Agent sender) {
        this.sender = sender.getAID();
        return this;
    }

    public MsgBuilder setReceivers(List<AID> receivers) {
        this.receivers = receivers;
        return this;
    }

    public MsgBuilder setConversationId(String conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public MsgBuilder setPerformative(int performative) {
        this.performative = performative;
        return this;
    }

    public MsgBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public MsgBuilder setContentObj(AgentAction contentObj) {
        this.contentObj = contentObj;
        return this;
    }

    public MsgBuilder setReceiver(AID seller) {
        receivers.add(seller);
        return this;
    }

    public MsgBuilder setContentManager(ContentManager contentManager) {
        this.contentManager = contentManager;
        return this;
    }

    public ACLMessage build() {
        ACLMessage msg = new ACLMessage(performative);

        if (content != null) {
            msg.setContent(content);
        }

        msg.setLanguage(CODEC.getName());
        msg.setOntology(AuctionOntology.getInstance().getName());

        if (contentObj != null && contentManager != null) {
//            try {
//                msg.setContentObject(contentObj);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                contentManager.fillContent(msg, new Action(sender, contentObj));
            } catch (Codec.CodecException | OntologyException e) {
                e.printStackTrace();
            }
        }

        if (conversationId != null) {
            msg.setConversationId(conversationId);
        }

        msg.setSender(sender);
        for (AID receiver : receivers) {
            msg.addReceiver(receiver);
        }
        return msg;
    }
}
