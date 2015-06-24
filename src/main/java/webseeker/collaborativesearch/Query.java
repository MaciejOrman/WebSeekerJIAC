package webseeker.collaborativesearch;

import de.dailab.jiactng.agentcore.knowledge.IFact;

public class Query implements IFact {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String query;
	
	public Query(String query){
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String toString() {
		return "Query [query=" + query + "]";
	}
}
