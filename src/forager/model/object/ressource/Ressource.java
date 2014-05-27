package forager.model.object.ressource;

import forager.model.Environnement;
import forager.model.object.Harvestable;
import forager.agents.AgentEntity;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class Ressource extends  Harvestable
{
	public Ressource(Environnement env, Point pos, Rectangle hitbox, int quantity)
	{
		super(env,pos,hitbox);
		weight = quantity;
	}

	public Ressource(Environnement env, Rectangle bounds, int quantity)
	{
		super(env,bounds);
		weight = quantity;
	}
	
	public Ressource(Environnement env, Point pos, Rectangle hitbox)
	{
		super(env,pos,hitbox);
		weight = 50;
	}
	
	public Ressource(Environnement env, Rectangle bounds)
	{
		super(env,bounds);
		weight = 50;
	}
	
	@Override
	public int harvest(AgentEntity ae) 
	{
		int harvested = ae.add(type(),weight);
		weight -= harvested;
		if (weight == 0)
			delete();
		return harvested;
	}
}
