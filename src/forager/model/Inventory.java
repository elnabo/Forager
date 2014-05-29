package forager.model;

import java.util.HashMap;
import java.util.Set;

/**
 * Basic inventory.
 * 
 * @author Guillaume Desquesnes
 */
public class Inventory
{
	/** Maximum capacity of the inventory. */
	protected final int maxCapacity;
	
	/** Available capacity. */
	protected int freeCapacity;
	
	/** Map an item type to its quantity. */
	protected HashMap<String,Integer> storage = new HashMap<String,Integer>();
	
	/**
	 * Create an inventory.
	 * 
	 * Same as new Inventory(50);
	 */
	public Inventory()
	{
		maxCapacity = 50;
		freeCapacity = maxCapacity;
	}
	
	/**
	 * Create an inventory of given size.
	 * 
	 * @param maxCapacity  The maximum capacity of the inventory.
	 */
	public Inventory(int maxCapacity)
	{
		this.maxCapacity = maxCapacity;
		freeCapacity = maxCapacity;
	}
	
	/**
	 * Get the remaining space of the inventory.
	 * 
	 * @return The remaining space.
	 */
	public int remainingSpace()
	{
		return freeCapacity;
	}
	
	/**
	 * Return the quantity stored for this item.
	 * 
	 * @param item  The item type, not null.
	 * 
	 * @return The quantity stored.
	 */
	public int getCapacity(String item)
	{
		return (storage.containsKey(item)) ? storage.get(item) : 0;
	}
	
	/**
	 * Return the quantity added
	 * 
	 * @param item  The item type, not null.
	 * @param quantity  The quantity to add.
	 * 
	 * @return The quantity added
	 */
	public int add(String item, int quantity)
	{
		/* If it's too big, only add what
		 * can be added */
		if (quantity > freeCapacity)
			quantity = freeCapacity;
		
		freeCapacity -= quantity;

		if (storage.containsKey(item))
			storage.put(item, storage.get(item) + quantity);
		else
			storage.put(item,quantity);
		
		// Return the quantity added
		return quantity;
	}
	
	/**
	 * Remove a certain quantity of item.
	 * 
	 * @param item  The type of item, not null.
	 * @param weight  The quantity to remove.
	 * 
	 * @return The quantity removed.
	 */
	public int remove(String item, int weight)
	{
		if (storage.containsKey(item))
		{
			int contained = storage.get(item);
			int removed = Math.min(weight, contained);
			storage.put(item, contained-removed);
			freeCapacity += removed;
			return removed;
		}
		
		return 0;
	}
	
	/**
	 * Return a set of all items contained in the inventory.
	 * 
	 * @return The set of all items in the inventory.
	 */
	public Set<String> items()
	{
		return storage.keySet();
	}
}
