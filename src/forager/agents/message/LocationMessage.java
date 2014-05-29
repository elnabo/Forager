package forager.agents.message;

import forager.agents.AgentInfo;

import java.awt.Rectangle;

/**
 * Message used to tell the location of ressource.
 * 
 * @author Guillaume Desquesnes
 */
public class LocationMessage extends MessageContent
{
	/** Hitbox of the ressource. */
	public final Rectangle position;
	/** Type of ressource. */
	public final String item;
	
	/**
	 * Create a message informing on the position of
	 * a certain ressource.
	 * 
	 * @param sender  Info on the sender.
	 * @param position  Position of the ressource.
	 * @param item  Type of the ressource.
	 */
	public LocationMessage(AgentInfo sender, Rectangle position, String item)
	{
		super(sender);
		this.position = position;
		this.item = item;
	}
}
