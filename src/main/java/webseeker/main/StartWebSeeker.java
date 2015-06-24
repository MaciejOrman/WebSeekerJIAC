package webseeker.main;

import webseeker.jiac.NodeStarter;
import webseeker.rating.DynamicAgentsCreation;



public class StartWebSeeker {

	public static void main(String[] args) {
		
		NodeStarter.startNode(
				"webSeeker.xml",
				"WebSeekerNode",
				5000);
		
	}
	
	

}
