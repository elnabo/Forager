package agents.message;

import agents.AgentInfo;

public class CopulationOffer extends MessageContent
{
	public CopulationOffer(AgentInfo sender)
	{
		super(sender);
	}
	
	@Override
	public boolean isReply()
	{
		return false;
	}
	
	@Override
	public String type()
	{
		return this.getClass().getSimpleName();
	}
}
