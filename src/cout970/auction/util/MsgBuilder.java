package cout970.auction.util;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cout970 on 2017/05/11.
 */
public class MsgBuilder {

    private static final SLCodec CODEC = new SLCodec();

    private AID sender;
    private List<AID> receivers = new ArrayList<>();
    private String conversationId;
    private int performative;
    private String content;
    private ContentElement contentObj;
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

    public MsgBuilder setContentObj(ContentElement contentObj) {
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
        if (contentObj != null && contentManager != null) {
            try {
                msg.setContentObject(contentObj);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                contentManager.fillContent(msg, contentObj);
//            } catch (Codec.CodecException | OntologyException e) {
//                e.printStackTrace();
//            }
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
