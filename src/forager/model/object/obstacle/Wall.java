package forager.model.object.obstacle;

import forager.model.Environnement;
import forager.model.FixedObject;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * A basic undestructible obstacle.
 * 
 * @author Guillaume Desquesnes
 */
public class Wall extends FixedObject
{
	/**
	 * Create a new 5x5 wall at the given position.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param pos  The position.
	 */
	public Wall(Environnement env, Point pos)
	{
		super(env,new Rectangle(pos.x, pos.y, 5,5));
	}
	
	/**
	 * Create a new wall.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param hitbox  The hitbox of the wall.
	 */
	public Wall(Environnement env, Rectangle hitbox)
	{
		super(env,hitbox);
	}
}
