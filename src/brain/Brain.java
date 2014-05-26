package brain;

import agents.AgentEntity;
import agents.AgentInfo;
import agents.message.CopulationOffer;
import agents.message.MessageContent;
import agents.message.Reply;
import agents.message.RequireFoodMessage;
import model.FixedObject;
import util.Vector2D;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

public abstract class Brain
{
	private AgentEntity parent;
	private Random generator = new Random();
	
	public final Brain init(AgentEntity parent)
	{
		this.parent = parent;
		return this;
	}
	
	public final void broadcast(MessageContent message)
	{
		if (message instanceof Reply)
			return;
		parent.broadcast(message);
	}
	
	public final boolean canHarvest()
	{
		return parent.canHarvest();
	}
	
	public final MessageContent createCopulationMessage()
	{
		return new CopulationOffer(parent.info());
	}
	
	public final MessageContent createFoodRequestMessage()
	{
		return new RequireFoodMessage(parent.info());
	}
	
	
	public final boolean copulate(MessageContent reply)
	{
		if (!(reply instanceof Reply) || !parent.isMessage(reply))
			return false;
			
		return parent.copulate(reply.sender);		
	}
	
	public final void die()
	{
		parent.die();
	}
	
	public final Vector2D direction()
	{
		return parent.direction();
	}
	
	public final double distance(AgentInfo other)
	{
		Rectangle m = parent.hitbox(),
				o = other.hitbox;
		int x1 = m.x + m.width/2,
			y1 = m.y + m.height/2,
			x2 = o.x + o.width/2,
			y2 = o.y + o.height/2;
			
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public final void eat(int quantity)
	{
		parent.eat(quantity);
	}
	
	public final boolean give(AgentInfo ai, String item, int quantity)
	{
		if (((System.currentTimeMillis() - ai.timestamp) < 50) &&
			(distance(ai) < 10))
		{
			parent.give(ai, item, quantity);
			return true;
		}
		return false;
	}
	
	public final List<FixedObject> getCollision()
	{
		return parent.getCollision(parent.hitbox());
	}
	
	public final int getQuantity(String item)
	{
		return parent.inventory().getCapacity(item);
	}
	
	public final List<MessageContent> getMessages()
	{
		return parent.getMessages();
	}
	
	public final List<AgentInfo> getVisibleAgents()
	{
		return parent.getVisibleAgents();
	}
	
	public final List<FixedObject> getVisibleObjects()
	{
		return parent.getVisibleObjects();
	}
	
	public final boolean haveMessage()
	{
		return parent.haveMessage();
	}
	
	public final int harvest()
	{
		return parent.harvest();
	}
	
	public final Rectangle hitbox()
	{
		return parent.hitbox();
	}
	
	public final double hunger()
	{
		return parent.hunger();
	}
	
	public final boolean isTeamMate(AgentInfo ai)
	{
		return ai.equals(parent.team());
	}
	
	public final Vector2D moveBy(Vector2D direction)
	{
		return parent.moveBy(direction);
	}
	
	public final void replyTo(MessageContent origin, boolean value)
	{
		if (parent.isMessage(origin) && !(origin instanceof Reply))
		{
			parent.sendMessage(new Reply(parent.info(),value,origin),origin.sender);
		}
	}
	
	public final void sendMessage(MessageContent message, AgentInfo ai)
	{
		if (message instanceof Reply)
			return;
		parent.sendMessage(message,ai);
	}
	
	public abstract void update();

}
