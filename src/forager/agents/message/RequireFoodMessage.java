package forager.agents.message;

import forager.agents.AgentInfo;

/**
 * Message used to require food.
 * 
 * @author Guillaume Desquesnes
 */
public class RequireFoodMessage extends MessageContent
{
	/**
	 * Create a require food message.
	 * 
	 * @param sender  Info on the sender.
	 */
	public RequireFoodMessage(AgentInfo sender)
	{
		super(sender);
	}
}
