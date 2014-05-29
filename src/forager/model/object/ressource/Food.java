package forager.model.object.ressource;

import forager.model.Environnement;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * A food ressource.
 * 
 * @author Guillaume Desquesnes
 */
public class Food extends Ressource
{	
	/**
	 * Create a new 5x5 food ressource of capacity 50 at the given position.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param pos  The position.
	 */
	public Food(Environnement env, Point pos)
	{
		super(env,new Rectangle(pos.x,pos.y,5,5));
	}
	
	/**
	 * Create a new food ressource of capacity 50 from its hitbox.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param hitbox  The hitbox.
	 */
	public Food(Environnement env, Rectangle hitbox)
	{
		super(env,hitbox);
	}
	
	/**
	 * Create a new 5x5 food ressource at the given position.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param pos  The position.
	 * @param quantity  The initial quantity of ressource.
	 */
	public Food(Environnement env, Point pos, int quantity)
	{
		super(env,new Rectangle(pos.x,pos.y,5,5),quantity);
	}
	
	/**
	 * Create a new food ressource of capacity 50 from its hitbox.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param hitbox  The hitbox.
	 * @param quantity  The initial quantity of food.
	 */
	public Food(Environnement env, Rectangle hitbox, int quantity)
	{
		super(env,hitbox,quantity);
	}
}
