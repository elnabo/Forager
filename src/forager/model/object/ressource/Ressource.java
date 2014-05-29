package forager.model.object.ressource;

import forager.model.Environnement;
import forager.model.object.Harvestable;
import forager.agents.AgentEntity;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class Ressource extends  Harvestable
{
	/**
	 * Create a new ressource from its hitbox.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param hitbox  The hitbox.
	 * @param quantity  The initial quantity of ressource.
	 */
	public Ressource(Environnement env, Rectangle hitbox, int quantity)
	{
		super(env,hitbox,quantity);
	}
	
	/**
	 * Create a new ressource of capacity 50 from its hitbox.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param hitbox  The hitbox.
	 */
	public Ressource(Environnement env, Rectangle hitbox)
	{
		super(env,hitbox,50);
	}
	
	@Override
	/** {@inheritdoc} */
	public int harvest(AgentEntity ae, String type) 
	{
		if (!hasType(type))
			return 0;
			
		int harvested = ae.add(type(),quantity);
		quantity -= harvested;
		if (quantity == 0)
			delete();
		return harvested;
	}
	
	@Override
	/** {@inheritdoc} */
	public boolean hasType(String type)
	{
		return type.equals(type());
	}
}
