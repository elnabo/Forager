package brain;

import agents.AgentEntity;
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
	
	public List<FixedObject> getCollision()
	{
		return parent.getCollision(parent.hitbox());
	}
	
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		return parent.getCollisionAfterMovement(mvment);
	}
	
	//~ public List<AgentEntity> getVisibleNeighbors()
	//~ {
		//~ return parent.getVisibleNeighbors();
	//~ }
	
	public List<FixedObject> getVisibleObjects()
	{
		return parent.getVisibleObjects();
	}
	
	public final int harvest()
	{
		return parent.harvest();
	}
	
	public final double hunger()
	{
		return parent.hunger();
	}
	
	public final void moveBy(Vector2D direction)
	{
		parent.moveBy(direction);
	}
	
	public abstract void update();
}
