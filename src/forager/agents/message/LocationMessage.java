package forager.agents.message;

import forager.agents.AgentInfo;

import java.awt.Rectangle;

public class LocationMessage extends MessageContent
{
	public final Rectangle position;
	public final String item;
	
	public LocationMessage(AgentInfo sender, Rectangle position, String item)
	{
		super(sender);
		this.position = position;
		this.item = item;
	}
}
