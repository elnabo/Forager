package forager.model.object;

import forager.agents.AgentEntity;
import forager.model.Environnement;
import forager.model.FixedObject;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class Harvestable extends FixedObject
{
	protected int weight = 0;
	public Harvestable(Environnement env, Point pos, Rectangle hitbox)
	{
		super(env,pos,hitbox);
	}
	public Harvestable(Environnement env, Rectangle bounds)
	{
		super(env,bounds);
	}
	
	public int weight() { return weight; }
	public abstract int harvest(AgentEntity entity);
	@Override
	public boolean harvestable() {return true;}
}
