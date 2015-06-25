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
	private List<LinkAndGrade> linksAndGrades;
	
	public ReccomendedLinks(){

	}
	

	public ReccomendedLinks(String nick, String query,
			List<LinkAndGrade> linksAndGrades) {
		super();
		this.linksAndGrades = linksAndGrades;
	}

	public List<LinkAndGrade> getLinksAndGrades() {
		return linksAndGrades;
	}
	public void setLinksAndGrades(List<LinkAndGrade> linksAndGrades) {
		this.linksAndGrades = linksAndGrades;
	}
	@Override
	public String toString() {
		return "ReccomendedLinks ["
				+ ", linksAndGrades=" + linksAndGrades + "]";
	}

	


	
}
