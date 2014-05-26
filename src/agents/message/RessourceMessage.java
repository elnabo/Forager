package agents.message;

import agents.AgentInfo;

public final class RessourceMessage extends MessageContent
{
	public final String item;
	public final int quantity;
	public final boolean reply;
	
	public RessourceMessage(String item, int quantity, AgentInfo sender)
	{
		super(sender);
		this.item = item;
		this.quantity = quantity;
		this.reply = false;
	}
	
	public RessourceMessage(String item, int quantity, AgentInfo sender, boolean reply)
	{
		super(sender);
		this.item = item;
		this.quantity = quantity;
		this.reply = reply;
	}
}
