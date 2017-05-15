package cout970.auction.util;

import com.sun.istack.internal.NotNull;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * Created by cout970 on 2017/05/11.
 */
public class MsgHelper {

    @SuppressWarnings("unchecked")
    @NotNull
    public static <T> T getContentObj(ACLMessage msg, ContentManager cm) {
        try {
            Action a = (Action) cm.extractContent(msg);
            return (T) a.getAction();
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }
}
