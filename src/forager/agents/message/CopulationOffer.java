package forager.agents.message;

import forager.agents.AgentInfo;

/**
 * Message of reproduction
 * 
 * @author Guillaume Desquesnes
 */
public class CopulationOffer extends MessageContent
{
	/**
	 * Create a new offer for copulation.
	 * 
	 * @param sender The sender.
	 */
	public CopulationOffer(AgentInfo sender)
	{
		super(sender);
	}
}
