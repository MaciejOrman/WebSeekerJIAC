package webseeker.faroo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import de.dailab.jiactng.agentcore.action.scope.ActionScope;
import de.dailab.jiactng.rsga.beans.AbstractRESTfulAgentBean;

public class SearchInFarooBean extends AbstractRESTfulAgentBean {

	@POST
	@Path("/search")
	@Expose(scope = ActionScope.WEBSERVICE)
	public ReccomendedLinks search(String nick, String query)
			throws UnirestException {
			if (nick != null && query != null) {
				return searchInFaroo(query, nick);
			}
		return null;

	}

	private ReccomendedLinks searchInFaroo(String query, String nick)
			throws UnirestException {
		HttpResponse<String> response;
		String queryModified = query.replace(" ", "+");

		response = Unirest
				.get("https://faroo-faroo-web-search.p.mashape.com/api?q="
						+ queryModified)
				.header("X-Mashape-Key",
						"EQ0BgAHsmxmsh6FylZTqSqqkTF7op1ov4BzjsntBXusNcAgWbU")
				.header("Accept", "application/json").asString();

		ObjectMapper mapper = new ObjectMapper();
		try {
			FarooResults farooLinks = mapper.readValue(response.getBody(),
					FarooResults.class);
			ReccomendedLinks reccomendedLinks = new ReccomendedLinks();
			reccomendedLinks.setNick(nick);
			LinkAndGrade linkAndGrade;
			LinkAndGrade[] linksAndGrades = new LinkAndGrade[farooLinks.getLength()];

			//for (Link farooLink : farooLinks.getResults()) {
			for(int i =0; i<farooLinks.getLength();i++){
				FarooLink farooLink = farooLinks.getResults().get(i);
				linkAndGrade = new LinkAndGrade(farooLink.getTitle(),
						farooLink.getUrl(), farooLink.getKwic());
				linksAndGrades[i]= linkAndGrade;
			}
			reccomendedLinks.setLinksAndGrades(linksAndGrades);
			reccomendedLinks.setQuery(query);
			//System.out.println(reccomendedLinks);

			return reccomendedLinks;

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void doStart() throws Exception {
		super.doStart();
		log.info("TestAgentBean - starting....");
		log.info("TestAgentBean - my ID: " + this.thisAgent.getAgentId());
		log.info("TestAgentBean - my Name: " + this.thisAgent.getAgentName());
		log.info("TestAgentBean - my Node: "
				+ this.thisAgent.getAgentNode().getName());
	}

	@Override
	public void execute() {
		super.execute();
	}

}
