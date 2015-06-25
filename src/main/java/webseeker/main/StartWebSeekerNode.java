package webseeker.main;

import webseeker.jiac.NodeStarter;
import webseeker.rating.DynamicAgentsCreation;



public class StartWebSeekerNode {

	public static void main(String[] args) {
		
		NodeStarter.startNode(
				"webSeeker.xml",
				"WebSeekerNode",
				5000);
		
	}
	
	

}
