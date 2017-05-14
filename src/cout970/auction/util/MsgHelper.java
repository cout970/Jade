package cout970.auction.util;

import com.sun.istack.internal.NotNull;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * Created by cout970 on 2017/05/11.
 */
public class MsgHelper {

    @NotNull
    public static <T> T getContentObj(ACLMessage msg){
        try {
            return (T) msg.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }
}
