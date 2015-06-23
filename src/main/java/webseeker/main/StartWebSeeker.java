package webseeker.main;

import webseeker.JIAC.NodeStarter;



public class StartWebSeeker {

	public static void main(String[] args) {
		
		NodeStarter.startNode(
				"webSeeker.xml",
				"WebSeekerNode",
				5000);
	}

}
