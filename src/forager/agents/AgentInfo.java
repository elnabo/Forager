package forager.agents;

import java.awt.Rectangle;

/**
 * Contains info on an agent, accessible from the outside of the package.
 * You cannot derivate from this class.
 * 
 * <p>
 * This class is timestamped.
 * </p>
 * 
 * @author Guillaume Desquesnes
 */
public final class AgentInfo
{
	/** Hitbox of the agent at the creation. */
	public final Rectangle hitbox;
	
	/** The team of the agent. */
	public final String team;
	
	/** Id of the agent. */
	public final int id;
	
	/** Direct access to the agent. */
	protected final AgentEntity agent;
	
	/** Date of creation of the info. */
	public final long timestamp = System.currentTimeMillis();
	
	/**
	 * Create information on the agent.
	 * 
	 * @param agent  The agent.
	 */
	protected AgentInfo(AgentEntity agent)
	{
		this.agent = agent;
		this.hitbox = agent.hitbox();
		this.team = agent.team();
		this.id = agent.id();
	}
}
