package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Inventory
{
	protected final int maxCapacity;
	protected int usedCapacity = 0;
	protected int freeCapacity;
	
	protected HashMap<String,Integer> storage = new HashMap<String,Integer>();
	
	public Inventory()
	{
		maxCapacity = 50;
		freeCapacity = maxCapacity;
	}
	
	public Inventory(int maxCapacity)
	{
		this.maxCapacity = maxCapacity;
		freeCapacity = maxCapacity;
	}
	
	public int remainingSpace()
	{
		return freeCapacity;
	}
	
	public int getCapacity(String item)
	{
		return (storage.containsKey(item)) ? storage.get(item) : 0;
	}
	
	public int add(String item, int weight)
	{
		if (weight > freeCapacity)
		{
			weight = freeCapacity;
		}
		
		freeCapacity -= weight;
		if (storage.containsKey(item))
			storage.put(item, storage.get(item) + weight);
		else
			storage.put(item,weight);
		return weight;
	}
	
	public Entry<String,Integer> remove(String item, int weight)
	{
		if (storage.containsKey(item))
		{
			int contained = storage.get(item);
			int removed = Math.min(weight, contained);
			storage.put(item, contained-removed);
			freeCapacity += removed;
			usedCapacity -= removed;
			return new SimpleEntry<String,Integer>(item, removed);
		}
		
		return new SimpleEntry<String,Integer>(item, 0);
	}
	
	public Set<String> items()
	{
		return storage.keySet();
	}
}
