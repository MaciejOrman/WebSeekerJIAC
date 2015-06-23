package webseeker.JIAC;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.dailab.jiactng.agentcore.SimpleAgentNode;

public class NodeStarter {

	public static SimpleAgentNode node;
	
	/**
	 * Start an agent node, wait a few seconds, and stop the node again.
	 *  
	 * @param configfile	slash-delimeted path to config file (from classpath)
	 * @param nodename		name of the node to start
	 * @param time			time to wait before shutdown
	 */
	
	
	public static void startNode(String configfile, String nodename, int time) {

		// use JIAC's default log4j configuraten
		System.setProperty("log4j.configuration", "jiactng_log4j.properties");
		
		// start node
		node = (SimpleAgentNode)
				new ClassPathXmlApplicationContext(configfile).getBean(nodename);

		// wait a few seconds
/*		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		 //stop node
		try {
			node.shutdown();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}*/

	}

}
