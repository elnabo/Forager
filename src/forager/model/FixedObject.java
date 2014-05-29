package forager.model;

import forager.quadtree.QuadTreeElement;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * An object who can't move. A ressource or an obstacle.
 * 
 * @author Guillaume Desquesnes
 */
public abstract class FixedObject implements QuadTreeElement
{
	/** The environnement where this belongs. */
	protected Environnement environnement;
	
	/** The hitbox of this object. */
	protected Rectangle hitbox;
	
	/**
	 * Create a new object.
	 * 
	 * @param env  The environnement where the object belong.
	 * @param hitbox  The hitbox of the object.
	 */
	public FixedObject(Environnement env, Rectangle hitbox)
	{
		environnement = env;
		this.hitbox = new Rectangle(hitbox);
	}
	
	/**
	 * Get the hitbox of this object.
	 * 
	 * @return The hitbox.
	 */
	public Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}
	
	/**
	 * Delete this object.
	 */
	protected void delete()
	{
		environnement.remove(this);
	}
	
	/**
	 * Get this type. It is equivalent to its
	 * simple name.
	 * 
	 * @return The type of this object.
	 */
	public String type()
	{
		return getClass().getSimpleName();
	}
	
	/**
	 * Test if the object is harvestable.
	 * 
	 * @return True if you can harvest this object, else false.
	 */
	public boolean harvestable()
	{
		return false;
	}
}
