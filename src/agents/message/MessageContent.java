package agents.message;

import agents.AgentInfo;

public abstract class MessageContent 
{
	public final AgentInfo sender;
	public final long timestamp = System.currentTimeMillis();
	
	public MessageContent(AgentInfo sender) { this.sender = sender;}
	
	//~ Class<MessageContent> getReplyClass();
	//~ Reply reply(boolean value);
	public boolean isReply() { return false;}
	public String type() { return getClass().getSimpleName();}
}
