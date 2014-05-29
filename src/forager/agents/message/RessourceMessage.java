package forager.agents.message;

import forager.agents.AgentInfo;

/**
 * Message used to give ressource.
 * 
 * @author Guillaume Desquesnes
 */
public final class RessourceMessage extends MessageContent
{
	/** Ressource item type. */
	public final String item;
	
	/** Quantity of ressource in the message. */
	public final int quantity;
	
	/** Weither the message is a reply. */
	public final boolean reply;
	
	/**
	 * Create a ressource message.
	 * 
	 * @param sender  Info on the sender.
	 * @param item  The item type.
	 * @param quantity  The item quantity.
	 */
	public RessourceMessage(AgentInfo sender,String item, int quantity)
	{
		super(sender);
		this.item = item;
		this.quantity = quantity;
		this.reply = false;
	}
	
	/**
	 * Create a ressource message who may be a give back.
	 * 
	 * @param sender  Info on the sender.
	 * @param item  The item type.
	 * @param quantity  The item quantity.
	 * @param reply  True if the message is a give back.
	 */
	public RessourceMessage(AgentInfo sender, String item, int quantity, boolean reply)
	{
		super(sender);
		this.item = item;
		this.quantity = quantity;
		this.reply = reply;
	}
}
