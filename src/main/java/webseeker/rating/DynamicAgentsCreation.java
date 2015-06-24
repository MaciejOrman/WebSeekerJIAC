package webseeker.rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import webseeker.jiac.NodeStarter;
import de.dailab.jiactng.agentcore.Agent;
import de.dailab.jiactng.agentcore.IAgent;
import de.dailab.jiactng.agentcore.IAgentBean;
import de.dailab.jiactng.agentcore.comm.CommunicationBean;
import de.dailab.jiactng.agentcore.comm.broker.ConnectionFactoryProxy;
import de.dailab.jiactng.agentcore.comm.transport.MessageTransport;
import de.dailab.jiactng.agentcore.comm.transport.jms.JMSMessageTransport;
import de.dailab.jiactng.agentcore.execution.NonBlockingExecutionCycle;
import de.dailab.jiactng.agentcore.execution.SimpleExecutionCycle;
import de.dailab.jiactng.agentcore.knowledge.Memory;
import de.dailab.jiactng.agentcore.lifecycle.LifecycleException;

public class DynamicAgentsCreation {

	
	 public void addAgent(String name, int executeInterval, IAgentBean... beans) throws LifecycleException, Exception { 
	        // create agent and add to node 
	        IAgent agent = prepareAgent(name, executeInterval, beans); 
	        NodeStarter.node.addAgent(agent); 
	        // start agent 
	        agent.init(); 
	        agent.start(); 
	    }

	    private IAgent prepareAgent(String agentName, int executeInterval, IAgentBean... agentBeans) throws Exception{ 
	        // Prepare communication bean 
	        ConnectionFactoryProxy factoryProxy = new ConnectionFactoryProxy(); 
	        JMSMessageTransport transport = new JMSMessageTransport(); 
	        transport.setConnectionFactory(factoryProxy); 
	        Set<MessageTransport> newTransports = new HashSet<MessageTransport>(); 
	        newTransports.add(transport); 
	        CommunicationBean commBean = new CommunicationBean(); 
	        commBean.setTransports(newTransports); 
	         
	        //Prepare the agent and set properties 
	        Agent agent = new Agent(); 
	        agent.setMemory(new Memory()); 
	        agent.setExecution(new NonBlockingExecutionCycle());  //??
	        agent.setCommunication(commBean); 
	        agent.setOwner(System.getProperty("user.name")); 
	        agent.setAgentName(agentName);
	       /* List<IAgentBean> iAgentBeans= new ArrayList<IAgentBean>();
	        iAgentBeans.add(new RatingBean());
	        agent.setAgentBeans(iAgentBeans);*/

	        // prepare beans 
	        for (IAgentBean agentBean : agentBeans) { 
	            agentBean.setExecutionInterval(executeInterval); 
	            agentBean.setBeanName(agentBean.getClass().getName()); 
	        } 
	         
	        //add the beans to the agent 
	        agent.setAgentBeans(Arrays.asList(agentBeans)); 
	        return agent; 
	    }
}
