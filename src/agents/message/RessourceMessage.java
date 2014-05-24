package agents.message;

import agents.AgentInfo;

public final class RessourceMessage implements MessageContent
{
	public final String item;
	public final int quantity;
	public final AgentInfo sender;
	public final boolean reply;
	
	public RessourceMessage(String item, int quantity, AgentInfo sender)
	{
		this.item = item;
		this.quantity = quantity;
		this.sender = sender;
		this.reply = false;
	}
	
	public RessourceMessage(String item, int quantity, AgentInfo sender, boolean reply)
	{
		this.item = item;
		this.quantity = quantity;
		this.sender = sender;
		this.reply = reply;
	}
	
	public Class<MessageContent> getReplyClass()
	{
		return null;
	}
}
