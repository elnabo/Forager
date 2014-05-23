package agents;

import brain.Brain;
import model.Environnement;
import model.FixedObject;
import model.Inventory;
import model.object.Harvestable;
import util.Vector2D;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;


import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class MobileAgent extends AbstractAgent implements AgentEntity
{
	private static final long serialVersionUID = -68366833257439L;
	private final Color col = Color.BLUE;
	
	protected double maxSteeringChange = 0.5;
	protected Vector2D direction;
	protected final int maxSpeed = 10;
	protected Inventory inventory = new Inventory(75);
	protected Rectangle hitbox;
	protected Environnement environnement;
	protected Brain brain;
	
	public MobileAgent(Environnement environnement, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(5,7,5,5);
		this.brain = brain.init(this);
	}
	public MobileAgent(Environnement environnement, Point pos, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(pos.x, pos.y,5,5);
		this.brain = brain.init(this);
	}
	
	@Override
	protected void activate()
	{
		requestRole("global","global","mobileAgent");
	}
	
	public void update() { brain.update();}
	
	public final Vector2D direction()
	{
		return new Vector2D(direction);
	}

	public final Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}
	
	public final void moveBy(Vector2D mvment)
	{
		List<FixedObject> collision = getCollisionAfterMovement(mvment);
		// colliding
		if (collision != null && collision.size() > 0)
		{
			direction = new Vector2D(0,0);
		}
		else
		{
			direction = new Vector2D(mvment);
			hitbox.x += (int)Math.round(mvment.x);
			hitbox.y += (int)Math.round(mvment.y);
		}
	}
	
	public final Color color()
	{
		return col;
	}
	
	public final List<FixedObject> getCollision(Rectangle bounds)
	{
		return environnement.collide(bounds);
	}
	
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		Rectangle box = new Rectangle(hitbox);
		box.x += (int)Math.round(mvment.x);
		box.y += (int)Math.round(mvment.y);
		return getCollision(box);
	}
	
	/**
	 * Harvest the ressource who collide with the agent.
	 * 
	 * @return The quantity harvested.
	 */
	public final int harvest() 
	{
		int harvested = 0;
		List<FixedObject> possibleCollisions = environnement.collide(new Rectangle(hitbox.x-1, hitbox.y-1, hitbox.width+2, hitbox.height+2));
		for (FixedObject i : possibleCollisions)
		{
			if (i.harvestable())
			{
				harvested += ((Harvestable)i).harvest(this);
			}
		}
		return harvested;
	}
	
	public int add(String item, int weight)
	{
		return inventory.add(item,weight);
	}
	
	protected final int drop(String item, int quantity)
	{
		SimpleEntry<String, Integer> toDrop = inventory.remove(item, quantity);
		environnement.createRessource(toDrop, new Point(hitbox.x, hitbox.y));
		return toDrop.getValue();
	}
	
}
