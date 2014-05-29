package forager.model.object;

import forager.agents.AgentEntity;
import forager.model.Environnement;
import forager.model.FixedObject;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * An harvestable fixed object.
 * 
 * @author Guillaume Desquesnes
 */
public abstract class Harvestable extends FixedObject
{
	/** Quantity of the object. */
	protected int quantity = 0;
	
	/**
	 * Create a new harvestable fixed object.
	 * 
	 * @param env  The environnement where this belongs.
	 * @param hitbox  This hitbox.
	 * @param quantity  This quantity.
	 */
	public Harvestable(Environnement env, Rectangle hitbox, int quantity)
	{
		super(env,hitbox);
		this.quantity = quantity;
	}
	
	/**
	 * Harvest the entity by an agent.
	 * 
	 * @param entity The harvester.
	 * @param type The type of ressource to harvest.
	 * 
	 * @return The quantity harvested.
	 */
	public abstract int harvest(AgentEntity entity, String type);
	
	@Override
	/**
	 * Test if the object is harvestable.
	 * 
	 * @return True.
	 */
	public boolean harvestable() 
	{
		return true;
	}
	
	/**
	 * Test if the harvestable contain the given type.
	 * 
	 * @param type  The ressource type.
	 * 
	 * @return True if this contain that ressource, else false.
	 */
	public abstract boolean hasType(String type);
	
	/**
	 * Get the quantity of the object.
	 * 
	 * @return The quantity of the object.
	 */
	public int quantity() 
	{ 
		return quantity; 
	}
}
