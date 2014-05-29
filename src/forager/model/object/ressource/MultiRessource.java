package forager.model.object.ressource;

import forager.model.Environnement;
import forager.model.Inventory;
import forager.model.object.Harvestable;
import forager.agents.AgentEntity;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * A version of object who contained multiple ressource
 * 
 * <p><strong> Not used at the moment</strong></p>
 */
public class MultiRessource extends Harvestable
{
	/** The inventory of this ressource. */
	protected Inventory inventory = new Inventory(Integer.MAX_VALUE);

	/**
	 * Create a new multi ressource object.
	 * 
	 * @param env  The environnement where it belongs.
	 * @param hitbox  This hitbox.
	 * @param items  The initial items.
	 */
	public MultiRessource(Environnement env, Rectangle hitbox, HashMap<String,Integer> items)
	{
		super(env,hitbox,MultiRessource.sum(items));
		for (String s : items.keySet())
		{
			inventory.add(s, items.get(s));
		}
	}
	
	/**
	 * Add a ressource to this.
	 * 
	 * @param item  The ressource type.
	 * @param quantity  The ressource quantity.
	 */
	public void add(String item, int quantity)
	{
		inventory.add(item,quantity);
	}
	
	@Override
	/** {@inheritdoc} */
	public int harvest(AgentEntity ae, String type)
	{
		if (!hasType(type))
			return 0;
			
		int contained = inventory.getCapacity(type);
		int harvested = ae.add(type,contained);
		inventory.remove(type,harvested);
		
		quantity -= harvested;
		return harvested;
	}
	
	@Override
	/** {@inheritdoc} */
	public boolean hasType(String type)
	{
		return inventory.getCapacity(type) != 0;
	}
	
	/**
	 * Sum the integer values of each element in the map.
	 * 
	 * @param map  The map.
	 * 
	 * @return The sum of all the value.
	 */
	public static int sum(Map<?,Integer> map)
	{
		if (map == null)
			return 0;
			
		int res = 0;
		for (Integer i : map.values())
		{
			res += i;
		}
		return res;
	}
	
	@Override
	/**
	 * Get the type of ressource available.
	 * 
	 * @return A concatenation of all the ressource available coma separated
	 * preceded by a 'm'.
	 */
	public String type()
	{
		StringBuilder res = new StringBuilder("m");
		for (String s : inventory.items())
		{
			res.append(s);
			res.append(",");
		}
		return res.toString();
	}
	
	
}
