package forager.agents.message;

import forager.agents.AgentInfo;

public final class Reply extends MessageContent
{
	public final boolean value;
	public final MessageContent src;
	
	public Reply(AgentInfo sender, boolean value, MessageContent src)
	{
		super(sender);
		this.value = value;
		this.src = src;
	}
	
	@Override
	public boolean isReply()
	{
		return true;
	}
	
	@Override
	public String type()
	{
		return "r" + src.getClass().getSimpleName();
	}
}
