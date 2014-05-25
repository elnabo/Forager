package agents.message;

import agents.AgentInfo;

public class RequireFoodMessage extends MessageContent
{
	public RequireFoodMessage(AgentInfo sender)
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
		return getClass().getSimpleName();
	}
}
