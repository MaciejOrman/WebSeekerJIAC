package webseeker.faroo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import de.dailab.jiactng.agentcore.knowledge.IFact;



public class ReccomendedLinks implements IFact{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick;
	private String query;
	private List<LinkAndGrade> linksAndGrades;
	
	public ReccomendedLinks(){

	}
	public String getNick() {
		return nick;
	}
	

	public ReccomendedLinks(String nick, String query,
			List<LinkAndGrade> linksAndGrades) {
		super();
		this.nick = nick;
		this.query = query;
		this.linksAndGrades = linksAndGrades;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<LinkAndGrade> getLinksAndGrades() {
		return linksAndGrades;
	}
	public void setLinksAndGrades(List<LinkAndGrade> linksAndGrades) {
		this.linksAndGrades = linksAndGrades;
	}
	@Override
	public String toString() {
		return "ReccomendedLinks [nick=" + nick + ", query=" + query
				+ ", linksAndGrades=" + linksAndGrades + "]";
	}

	


	
}
