package forager.model.object.ressource;

import forager.model.Environnement;
import forager.model.object.Harvestable;
import forager.agents.AgentEntity;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map.Entry;
import java.util.HashMap;

public class MultiRessource extends Harvestable
{
	protected HashMap<String,Integer> items = new HashMap<String,Integer>();
	
	public MultiRessource(Environnement env, Point pos, Rectangle hitbox)
	{
		super(env,pos,hitbox);
	}

	public MultiRessource(Environnement env, Rectangle bounds)
	{
		super(env,bounds);
	}
	
	@Override
	public int harvest(AgentEntity ae)
	{
		weight = 0;
		for (Entry<String,Integer> i : items.entrySet())
		{
			String key = i.getKey();
			int remaining = ae.add(key, i.getValue());
			if (remaining != 0)
			{
				weight += remaining;
				items.put(key, remaining);
			}
			else
			{
				items.remove(key);
			}
		}
		
		if (weight == 0)
			delete();
		return weight;
	}
	
	@Override
	public String type()
	{
		return "multi";
	}
	
	
}
