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
import java.util.Map.Entry;

public class MobileAgent extends AbstractAgent implements AgentEntity
{
	private static final long serialVersionUID = -68366833257439L;
	private final Color col = Color.BLUE;
	
	protected double maxSteeringChange = 0.5;
	protected Vector2D direction;
	protected final int maxSpeed = 1;
	protected Inventory inventory;// = new Inventory(75);
	protected Rectangle hitbox;
	protected Environnement environnement;
	protected Brain brain;
	
	protected int visionRange = 50;
	protected int hunger = 0;
	protected int maxHunger = 100;
	
	
	public MobileAgent(Environnement environnement, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(20,20,5,5);
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
		inventory = new Inventory(100);
		inventory.add("food",50);
	}
	
	@Override
	public int add(String item, int weight)
	{
		return inventory.add(item,weight);
	}
	
	public final Color color()
	{
		return col;
	}
	
	@Override
	public final Vector2D direction()
	{
		return new Vector2D(direction);
	}
	
	@Override
	public void die()
	{
		killAgent(this);
	}
	
	@Override
	public int drop(String item, int quantity)
	{
		Entry<String, Integer> toDrop = inventory.remove(item, quantity);
		environnement.createRessource(toDrop, new Point(hitbox.x, hitbox.y));
		return toDrop.getValue();
	}
	
	@Override
	public void eat(int quantity)
	{
		hunger = Math.max(0,hunger - inventory.remove("food",quantity).getValue());
	}

	@Override
	public final List<FixedObject> getCollision(Rectangle bounds)
	{
		return environnement.collide(bounds);
	}
	
	@Override
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		Rectangle box = new Rectangle(hitbox);
		box.x += (int)Math.round(mvment.x);
		box.y += (int)Math.round(mvment.y);
		return getCollision(box);
	}
	
	//~ @Override
	//~ public List<AgentEntity> getVisibleNeighbors()
	//~ {
		//~ return null;
	//~ }
	
	@Override
	public List<FixedObject> getVisibleObjects()
	{
		return environnement.collide(new Rectangle(hitbox.x - visionRange,
			hitbox.y - visionRange,	hitbox.width + 2 * visionRange,
			hitbox.height + 2 * visionRange));
	}
	
	/**
	 * Harvest the ressource who collide with the agent.
	 * 
	 * @return The quantity harvested.
	 */
	@Override
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
	
	@Override
	public final Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}
	
	/**
	 * 0 = no need to eat.
	 * 1 = dead of hunger.
	 */
	@Override
	public double hunger()
	{
		return hunger / ((double)maxHunger);
	}
	
	@Override
	public void increaseHunger()
	{
		hunger++;
		if (hunger == maxHunger)
			die();
	}
	
	@Override
	public final void moveBy(Vector2D mvment)
	{
		if (mvment.norm() > 1)
			mvment = mvment.unitVector();//.scalarMul(maxSpeed);
		if (mvment.norm() < 0)
			return;// new Vector2D(0,0);
			
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
	
	@Override
	public void update() 
	{ 
		increaseHunger();
		brain.update();
	}
	
}
