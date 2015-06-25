package webseeker.rating;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.sercho.masp.space.event.SpaceEvent;
import org.sercho.masp.space.event.SpaceObserver;
import org.sercho.masp.space.event.WriteCallEvent;

import webseeker.collaborativesearch.Query;
import webseeker.faroo.LinkAndGrade;
import webseeker.faroo.ReccomendedLinks;
import webseeker.jiac.Links;
import webseeker.jiac.RankedLink;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.message.IJiacMessage;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.knowledge.IFact;
import de.dailab.jiactng.rsga.beans.AbstractRESTfulAgentBean;

//this bean is created for each user in the system
public class RatingBean extends AbstractRESTfulAgentBean {

	private Action sendAction = null;
	
	@POST
	@Path("/grades")
	@Expose(scope = ActionScope.WEBSERVICE)
	public void receiveGrades(ReccomendedLinks reccomendedLinks){
		Links links = new Links();
		RankedLink rankedLink;
		List<RankedLink> rankedLinks = new ArrayList<RankedLink>();
		for(LinkAndGrade linkAndGrade: reccomendedLinks.getLinksAndGrades()) {
			int grade =linkAndGrade.getGrade();
			if(grade!=-1) {
				rankedLink = new RankedLink(linkAndGrade.getNick(), linkAndGrade.getTitle(), linkAndGrade.getUrl(), linkAndGrade.getKwic(), grade, linkAndGrade.getQuery());
				rankedLinks.add(rankedLink);
			}
		}
		links.setLinks(rankedLinks);
		
		memory.write(links); 
	}
	
	public static final String ACTION_GET_RANKED_LINKS = "webseeker.rating.RatingBean#getRankedLinks";
	
	@Expose(name=ACTION_GET_RANKED_LINKS, scope=ActionScope.NODE)
	public Links getRankedLinks(String query){
		System.out.println("Invoked");
		return searchLinksInMemory(query);
	}
	
	@Override
	public void doStart() throws Exception {
		super.doStart();
		log.info("RatingBean - starting....");
		log.info("RatingBean - my ID: " + this.thisAgent.getAgentId());
		log.info("RatingBean - my Name: " + this.thisAgent.getAgentName());
		log.info("RatingBean - my Node: "
				+ this.thisAgent.getAgentNode().getName());
		
		// Retrieve send action provided by CommunicationBean
	/*	sendAction = retrieveAction(ICommunicationBean.ACTION_SEND);

		// If no send action is available, check your agent configuration.
		// CommunicationBean is needed
		if (sendAction == null)
			throw new RuntimeException("Send action not found.");

		// listen to memory events, see MessageObserver implementation below
		memory.attach(new MessageObserver(), new JiacMessage(new Query(null)));*/
	}

/*	@SuppressWarnings("serial")
	private class MessageObserver implements SpaceObserver<IFact> {

		@SuppressWarnings("unchecked")
		public void notify(SpaceEvent<? extends IFact> event) {
			if (event instanceof WriteCallEvent<?>) {
				WriteCallEvent<IJiacMessage> wce = (WriteCallEvent<IJiacMessage>) event;
				// a JiacMessage holding a Ping with message 'Ping' has been 
				// written to this agent's memory
				log.info("RatingAgent - query received");

				// consume message
				IJiacMessage message = memory.remove(wce.getObject());
				
				String queryReceived = ((Query)(message).getPayload()).getQuery();
				System.out.println("received query "+queryReceived);
				// create answer: a JiacMessage holding a Ping with message 'pong'
				Links matchingLinks = searchLinksInMemory(queryReceived);
				JiacMessage matchingLinksMessage = new JiacMessage(matchingLinks);

				// send Pong to PingAgent (the sender of the original message)
				log.info("PongAgent - sending pong message");
				
				invoke(sendAction, new Serializable[] { matchingLinksMessage,
						message.getSender() }); //to receive in collaborativeSearchBean
			}
		}
	}*/
	
	private Links searchLinksInMemory(String query){
		System.out.println(thisAgent.getAgentName());
		
		Links links= memory.read(new Links(new ArrayList<RankedLink>()));
		//System.out.println("Read links:"+links);
		List<RankedLink> matchingRankedLinks = new ArrayList<RankedLink>();
		Links matchingLinks = null;
		if(links!=null){
			for(RankedLink link:links.getLinks()){
				if(link.getQuery().toUpperCase().contains(query.toUpperCase()) ||
						query.toUpperCase().contains(link.getQuery().toUpperCase())){
					matchingRankedLinks.add(link);
					System.out.println("Matching link: "+link);
				}
			}	
			matchingLinks = new Links(matchingRankedLinks);
		}
		return matchingLinks;
		
	}

	
}
