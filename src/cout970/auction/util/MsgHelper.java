package cout970.auction.util;

import com.google.gson.GsonBuilder;
import com.sun.istack.internal.NotNull;
import cout970.auction.Bid;
import jade.lang.acl.ACLMessage;

/**
 * Created by cout970 on 2017/05/11.
 */
public class MsgHelper {

    @NotNull
    public static <T> T getContentObj(ACLMessage msg){
//        try {
            return (T) (new GsonBuilder().create().fromJson(msg.getContent(), Bid.class));
//            return (T) msg.getContentObject();
//        } catch (UnreadableException e) {
//            e.printStackTrace();
//            throw new NullPointerException();
//        }
    }
}
