package webseeker.JIAC;

import java.util.List;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Links implements IFact {
	
	private static final long serialVersionUID = 3374059561747194801L;
	
	private List<RankedLink> links;

	public List<RankedLink> getLinks() {
		return links;
	}

	public void setLinks(List<RankedLink> links) {
		this.links = links;
	}
	
	public Links(List<RankedLink> payload){
		this.links = payload;
	}
	public Links(){
		
	}

	@Override
	public String toString() {
		return "Links [links=" + links + "]";
	}
	
	


	
}
