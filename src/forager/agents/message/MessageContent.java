package forager.agents.message;

import forager.agents.AgentInfo;

/**
 * Content of message, all messages must be send
 * with a subclas of this.
 * 
 * @author Guillaume Desquesnes
 */
public abstract class MessageContent 
{
	/** Info on the sender. */
	public final AgentInfo sender;
	
	/** Date of creation of the message. */
	public final long timestamp = System.currentTimeMillis();
	
	/**
	 * Create a new content and set the sender.
	 * 
	 * @param sender  Info on the sender.
	 */
	public MessageContent(AgentInfo sender) 
	{
		this.sender = sender;
	}
	
	/** 
	 * Test if the message is a reply. 
	 * Default is false.
	 * 
	 * @return True if it's reply, else false.
	 */
	public boolean isReply() 
	{ 
		return false;
	}
	
	/** 
	 * Return the type of message send.
	 * The simple name of the class.
	 * 
	 * @return The simple name of the class.
	 */
	public String type() { return getClass().getSimpleName();}
}
