package forager.agents.message;

import forager.agents.AgentInfo;

/**
 * Reply content for the reply message.
 * 
 * @author Guillaume Desquesnes
 */
public final class Reply extends MessageContent
{
	/** Weither the reply is negative or positive. */
	public final boolean value;
	
	/** The origin of the reply. */
	public final MessageContent src;
	
	/**
	 * Create a reply content of a message.
	 * 
	 * @param sender  The sender of the reply.
	 * @param value  If it is a positive or negative reply.
	 * @param src  The source message.
	 */
	public Reply(AgentInfo sender, boolean value, MessageContent src)
	{
		super(sender);
		this.value = value;
		this.src = src;
	}
	
	@Override
	/** Test if the message is a reply.
	 * 
	 * @return True
	 */
	public boolean isReply()
	{
		return true;
	}
	
	@Override
	/**
	 * Return the type of message preceded by a 'r'.
	 * 
	 * @return r + the origin message simple name.
	 */
	public String type()
	{
		return "r" + src.getClass().getSimpleName();
	}
}
