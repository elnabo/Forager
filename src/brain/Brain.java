package brain;

import agents.MobileAgent;
import model.FixedObject;
import util.Vector2D;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

public abstract class Brain
{
	private MobileAgent parent;
	private Random generator = new Random();
	
	public final Brain init(MobileAgent parent)
	{
		this.parent = parent;
		return this;
	}
	
	public final Vector2D direction()
	{
		return parent.direction();
	}
	
	public final int harvest()
	{
		return parent.harvest();
	}
	
	public List<FixedObject> getCollision()
	{
		return parent.getCollision(parent.hitbox());
	}
	
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		return parent.getCollisionAfterMovement(mvment);
	}
	
	public final void moveBy(Vector2D direction)
	{
		parent.moveBy(direction);
	}
	
	public abstract void update();
}
