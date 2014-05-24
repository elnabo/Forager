package brain;

import agents.AgentEntity;
import agents.AgentInfo;
import agents.message.MessageContent;
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
	
	public final void die()
	{
		parent.die();
	}
	
	public final Vector2D direction()
	{
		return parent.direction();
	}
	
	public final void eat(int quantity)
	{
		parent.eat(quantity);
	}
	
	public final void give(AgentInfo ai, String item, int quantity)
	{
		parent.give(ai, item, quantity);
	}
	
	public List<FixedObject> getCollision()
	{
		return parent.getCollision(parent.hitbox());
	}
	
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		return parent.getCollisionAfterMovement(mvment);
	}
	
	public List<MessageContent> getMessages()
	{
		return parent.getMessages();
	}
	
	public List<AgentInfo> getVisibleAgents()
	{
		return parent.getVisibleAgents();
	}
	
	public List<FixedObject> getVisibleObjects()
	{
		return parent.getVisibleObjects();
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
	
	public final void moveBy(Vector2D direction)
	{
		parent.moveBy(direction);
	}
	
	public final boolean isTeamMate(AgentInfo ai)
	{
		return ai.equals(parent.team());
	}
	
	public abstract void update();
}
