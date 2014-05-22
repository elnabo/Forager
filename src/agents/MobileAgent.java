package agents;

import model.Environnement;
import model.Inventory;
import util.Vector2D;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;


import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.AbstractMap.SimpleEntry;

public abstract class MobileAgent extends AbstractAgent
{
	private static final long serialVersionUID = -68366833257439L;
	
	protected double maxSteeringChange = 0.5;
	protected Vector2D direction;
	protected final int maxSpeed = 10;
	protected Inventory inventory;
	protected Point2D.Double position;
	protected Rectangle hitbox;
	protected Environnement environnement;
	
	public MobileAgent(Environnement environnement)
	{
		this.environnement = environnement;
		position = new Point2D.Double(5,5);
		hitbox = new Rectangle(5,5,5,5);
	}
	public MobileAgent(Environnement environnement, Point pos)
	{
		this.environnement = environnement;
		position = new Point2D.Double(pos.x, pos.y);
		hitbox = new Rectangle(pos.x, pos.y,5,5);
	}
	
	@Override
	protected void activate()
	{
		requestRole("global","global","mobileAgent");
	}
	
	public void update() {}
	
	public final Point position() 
	{ 
		return new Point((int)Math.round(position.x), (int)Math.round(position.y));
	}

	public final Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}
	
	/**
	 * Harvest the ressource who collide with the agent.
	 * 
	 * @return True if all the ressource has been harvested, else false.
	 */
	protected final boolean harverst() 
	{
		return false;
	}
	
	protected final int drop(String item, int quantity)
	{
		SimpleEntry<String, Integer> toDrop = inventory.remove(item, quantity);
		environnement.createRessource(toDrop, position());
		return toDrop.getValue();
	}
	
}
