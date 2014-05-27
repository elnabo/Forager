package forager.agents.message;

import forager.agents.AgentInfo;

public abstract class MessageContent 
{
	public final AgentInfo sender;
	public final long timestamp = System.currentTimeMillis();
	
	public MessageContent(AgentInfo sender) { this.sender = sender;}
	
	public boolean isReply() { return false;}
	public String type() { return getClass().getSimpleName();}
}
