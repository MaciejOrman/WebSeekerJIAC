package webseeker.collaborativesearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.sercho.masp.space.event.SpaceEvent;
import org.sercho.masp.space.event.SpaceObserver;
import org.sercho.masp.space.event.WriteCallEvent;

import webseeker.faroo.LinkAndGrade;
import webseeker.faroo.ReccomendedLinks;
import webseeker.jiac.Links;
import webseeker.jiac.RankedLink;
import webseeker.rating.RatingBean;
import de.dailab.jiactng.agentcore.action.Action;
import de.dailab.jiactng.agentcore.action.ActionResult;
import de.dailab.jiactng.agentcore.action.scope.ActionScope;
import de.dailab.jiactng.agentcore.comm.ICommunicationBean;
import de.dailab.jiactng.agentcore.comm.IMessageBoxAddress;
import de.dailab.jiactng.agentcore.comm.message.IJiacMessage;
import de.dailab.jiactng.agentcore.comm.message.JiacMessage;
import de.dailab.jiactng.agentcore.knowledge.IFact;
import de.dailab.jiactng.agentcore.ontology.AgentDescription;
import de.dailab.jiactng.agentcore.ontology.IActionDescription;
import de.dailab.jiactng.agentcore.ontology.IAgentDescription;
import de.dailab.jiactng.rsga.beans.AbstractRESTfulAgentBean;

public class CollaborativeSearchBean extends AbstractRESTfulAgentBean {

	private Action sendAction = null;
	
	private ReccomendedLinks reccomendedLinks = new ReccomendedLinks();
	
	@POST
	@Path("/collsearch")
	@Expose(scope = ActionScope.WEBSERVICE)
	public ReccomendedLinks collaborativeSearch(String nick, String query){
		reccomendedLinks.setNick(nick);
		reccomendedLinks.setQuery(query);
		List<LinkAndGrade> linksAndGrades = new ArrayList<LinkAndGrade>(); //map to this,put to reccomendedLinks and return reccomendedLinks
		
		List<RankedLink> receivedLinks = new ArrayList<RankedLink>();
		// Retrieve all Agents beside this
/*		List<IAgentDescription> agentDescriptions = thisAgent
				.searchAllAgents(new AgentDescription());
		String agentName;
		// Send a query to each of the Agents
		for (IAgentDescription agent : agentDescriptions) {
			agentName = agent.getName();
			if (!agentName.equals("CollaborativeSearchAgent") && !agentName.equals("SearchInFarooAgent") && !agentName.equals("RESTfulProviderAgent") ) {
				
				// create the message, get receiver's  message box address
				JiacMessage message = new JiacMessage(new Query(query));
				IMessageBoxAddress receiver = agent.getMessageBoxAddress();
	
				// Invoke sendAction
				log.info("CollaborativeSearchAgent- sending query to: " + receiver.getName());
				invoke(sendAction, new Serializable[] { message, receiver });
			}
		}*/
		
		IActionDescription template = new Action(RatingBean.ACTION_GET_RANKED_LINKS);
		IActionDescription act = memory.read(template);
		List<IActionDescription> actions = new ArrayList<IActionDescription>();
		if(act==null){
			 System.out.println("Szukamy u innych agentów"); 
			 actions = thisAgent.searchAllActions(template); 
		}
		List<RankedLink> rankedLinks = new ArrayList<RankedLink>();
		
		for(IActionDescription action:actions){
			ActionResult actionResult= 
					invokeAndWaitForResult(action, new Serializable[] {query});
			
					 for(int i=0;i<actionResult.getResults().length;i++){
						 Links s = (Links)actionResult.getResults()[i];
						 if(s!=null)
						 rankedLinks.addAll(s.getLinks());
					 }
		}
		
		System.out.println("Received"+rankedLinks);
		/*Links linksTemp;
		linksArr = new LinkAndGrade[linksList.size()*linksList.size()*10];
		LinkAndGrade resultLinkAndGrade;
		for(int i=0;i<linksList.size();i++){
			linksTemp = linksList.get(i);
			RankedLink rankedLinkTemp;
			
			for(int j=0;j<linksTemp.getLinks().size();j++){
				rankedLinkTemp = linksTemp.getLinks().get(j);
				resultLinkAndGrade = new LinkAndGrade(rankedLinkTemp.getTitle(), rankedLinkTemp.getUrl(), rankedLinkTemp.getKwic(), rankedLinkTemp.getGrade());
				linksArr[j] = resultLinkAndGrade;
			}
			reccomendedLinks.setLinksAndGrades(linksArr);
		}*/
		//return reccomendedLinks;
			
		//}
		
		
		LinkAndGrade linkAndGrade;
		double meanGrade;
		for (RankedLink rankedLink : rankedLinks) {
			meanGrade = coutMeanGradeForRankedLink(rankedLinks,
					rankedLink.getUrl());
			linkAndGrade = new LinkAndGrade(rankedLink.getTitle(),
					rankedLink.getUrl(), rankedLink.getKwic());
			linkAndGrade.setMeanGrade(meanGrade);
			if (!urlExists(linksAndGrades, linkAndGrade.getUrl())) {
				linksAndGrades.add(linkAndGrade);
			}
		}
		Collections.sort(linksAndGrades, new MeanGradeComparator());
		reccomendedLinks.setLinksAndGrades(linksAndGrades);
		System.out.println(reccomendedLinks);

		return reccomendedLinks;

}

private boolean urlExists(List<LinkAndGrade> list, String url) {
	for (LinkAndGrade element : list) {
		if (element.getUrl().equals(url)) {
			return true;
		}
	}
	return false;
}

private double coutMeanGradeForRankedLink(List<RankedLink> rankedLinks,
		String url) {
	Iterator<RankedLink> iterator = rankedLinks.iterator();
	int gradesSum = 0;
	int counter = 0;
	while (iterator.hasNext()) {
		RankedLink rankedLink = iterator.next();
		if (rankedLink.getUrl().equals(url) && rankedLink.getGrade() != -1) {
			gradesSum += rankedLink.getGrade();
			counter++;
		}
	}
	return gradesSum / (double) counter;
}
	
	
	
	@Override
	public void doStart() throws Exception {
		super.doStart();
		log.info("CollaborativeSearchBean - starting....");
		log.info("CollaborativeSearchBean - my ID: " + this.thisAgent.getAgentId());
		log.info("CollaborativeSearchBean - my Name: " + this.thisAgent.getAgentName());
		log.info("CollaborativeSearchBean - my Node: "
				+ this.thisAgent.getAgentNode().getName());
		
		// Retrieve send action provided by CommunicationBean
		sendAction = retrieveAction(ICommunicationBean.ACTION_SEND);

		// If no send action is available, check your agent configuration.
		// CommunicationBean is needed
		if (sendAction == null)
			throw new RuntimeException("Send action not found.");
		// listen to memory events, see MessageObserver implementation below
				memory.attach(new MessageObserver(), new JiacMessage(new Links(new ArrayList<RankedLink>())));
		}
	
		@Override
		public void execute() {
			super.execute();
		}

			@SuppressWarnings("serial")
			private class MessageObserver implements SpaceObserver<IFact> {

				@SuppressWarnings("unchecked")
				public void notify(SpaceEvent<? extends IFact> event) {
					if (event instanceof WriteCallEvent<?>) {
						WriteCallEvent<IJiacMessage> wce = (WriteCallEvent<IJiacMessage>) event;
						// a JiacMessage holding a Ping with message 'Ping' has been 
						// written to this agent's memory
						log.info("CollaborativeSearchBean - links received");

						// consume message
						IJiacMessage message = memory.remove(wce.getObject());
						System.out.println("--------------received links"+(Links)message.getPayload());
						// create answer: a JiacMessage holding a Ping with message 'pong'
					/*	JiacMessage pongMessage = new JiacMessage(new Links(null));

						// send Pong to PingAgent (the sender of the original message)
						log.info("PongAgent - sending pong message");
						invoke(sendAction, new Serializable[] { pongMessage,
								message.getSender() });*/
					}
				}
			}


	
}
