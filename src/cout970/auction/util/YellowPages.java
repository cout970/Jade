package cout970.auction.util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cout970 on 5/8/17.
 */
public class YellowPages {

    private static ServiceDescription serviceDescription = new ServiceDescription();

    static {
        serviceDescription.setType("subasta-de-libros");
        serviceDescription.setName("Subasta de libros en Jade");
    }

    public static void register(Agent agent) {
        try {
            DFAgentDescription description = new DFAgentDescription();

            description.setName(agent.getAID());
            description.addServices(serviceDescription);

            DFService.register(agent, description);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    public static List<AID> search(Agent agent) {
        DFAgentDescription filter = new DFAgentDescription();
        filter.addServices(serviceDescription);

        try {
            DFAgentDescription[] array = DFService.search(agent, filter);
            List<AID> ids = new ArrayList<>();

            for (DFAgentDescription agentDescription : array) {
                ids.add(agentDescription.getName());
            }

            return ids;
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
