package webseeker.faroo;

import java.io.Serializable;
import java.util.List;



public class ReccomendedLinks implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick;
	private String query;
	private LinkAndGrade[] linksAndGrades;
	
	public ReccomendedLinks(){

	}

	public String getNick() {
		return nick;
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
	
	


	public LinkAndGrade[] getLinksAndGrades() {
		return linksAndGrades;
	}

	public void setLinksAndGrades(LinkAndGrade[] linksAndGrades) {
		this.linksAndGrades = linksAndGrades;
	}

	@Override
	public String toString() {
		return "ReccomendedLinks [nick=" + nick + ", query=" + query
				+ ", linksAndGrades=" + linksAndGrades + "]";
	}

	


	
}
