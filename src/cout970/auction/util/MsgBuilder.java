package cout970.auction.util;

import com.google.gson.GsonBuilder;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;
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
    private Serializable contentObj;

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

    public MsgBuilder setContentObj(Serializable contentObj) {
        this.content = new GsonBuilder().create().toJson(contentObj);
//        this.contentObj = contentObj;
        return this;
    }

    public MsgBuilder setReceiver(AID seller) {
        receivers.add(seller);
        return this;
    }

    public ACLMessage build(){
        ACLMessage msg = new ACLMessage(performative);

        if(content != null) {
            msg.setContent(content);
        }

        if(contentObj != null) {
            try {
                msg.setContentObject(contentObj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (conversationId != null){
            msg.setConversationId(conversationId);
        }
        msg.setLanguage(CODEC.getName());

        msg.setSender(sender);
        for (AID receiver : receivers) {
            msg.addReceiver(receiver);
        }
        return msg;
    }
}
