package webseeker.JIAC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.dailab.jiactng.agentcore.AbstractAgentBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.IMessageBoxAddress;
import de.dailab.jiactng.agentcore.comm.message.IJiacMessage;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.ontology.AgentDescription;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;
import de.dailab.jiactng.agentcore.ontology.IAgentDescription;


public class PingBean extends AbstractAgentBean {

	private IActionDescription sendAction = null;
	private static boolean IS_EXECUTED = false;

	@Override
	public void doStart() throws Exception {
		super.doStart();
		log.info("PingAgent - starting....");
		log.info("PingAgent - my ID: " + this.thisAgent.getAgentId());
		log.info("PingAgent - my Name: " + this.thisAgent.getAgentName());
		log.info("PingAgent - my Node: " + this.thisAgent.getAgentNode().getName());

		// Retrieve the send-action provided by CommunicationBean
		IActionDescription template = new Action(ICommunicationBean.ACTION_SEND);
		sendAction = memory.read(template);
		if (sendAction == null) {
			sendAction = thisAgent.searchAction(template);
		}

		// If no send action is available, check your agent configuration.
		// CommunicationBean is needed
		if (sendAction == null){
			throw new RuntimeException("Send action not found.");
		}
	}

	@Override
	public void execute() {
		//RankedLink rankedLink = new RankedLink("maciek", "dupa", "www.dupa.pl", "A dupa", 5, "dupa");
		//List<RankedLink> rankedLinks = new ArrayList<RankedLink>();
		//rankedLinks.add(rankedLink);
		//memory.write(new Links(rankedLinks));
		
		if(!IS_EXECUTED){
			
		Links links= memory.read(new Links(new ArrayList<RankedLink>()));
		System.out.println("-------------------------"+links);

		// check for Messages with Pongs as payload
		IJiacMessage template = new JiacMessage(links);
		Set<IJiacMessage> messages = memory.removeAll(template);
		if (messages.size() > 0) {
			log.info("PingAgent - got pong message!");
		}

		// Retrieve all PongAgents
		List<IAgentDescription> agentDescriptions = thisAgent
				.searchAllAgents(new AgentDescription());

		// Send a 'Ping' to each of the PongAgents
		for (IAgentDescription agent : agentDescriptions) {
			if (agent.getName().equals("PongAgent")) {
				
				// create the message, get receiver's  message box address
				JiacMessage message = new JiacMessage(links);
				IMessageBoxAddress receiver = agent.getMessageBoxAddress();
	
				// Invoke sendAction
				log.info("PingAgent - sending Ping to: " + receiver);
				invoke(sendAction, new Serializable[] { message, receiver });
			}
		}
	}
		IS_EXECUTED = true;
	}
	
	
	
}
