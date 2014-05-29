package forager.quadtree;

import java.awt.Rectangle;

/**
 * Element of the Quadtree must implement this.
 * 
 * @author Guillaume Desquesnes
 */
public interface QuadTreeElement
{
	/** 
	 * Get the hitbox of an object.
	 * 
	 * @return The hitbox of the object.
	 */
	public Rectangle hitbox();
}
