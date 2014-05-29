package forager.model;

import forager.agents.AgentEntity;
import forager.model.object.ressource.Food;
import forager.quadtree.QuadTree;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Environnement of the Forager game.
 * 
 * @author Guillaume Desquesnes
 */
public class Environnement
{
	/** Agent counter. */
	private static int count = 0;
	
	/** The Quadtree of ressource/obstacle. */
	protected QuadTree<FixedObject> grid;
	
	/** Maximum depth of the Quadtree. */
	protected int gridMaxDepth = 7;
	
	/** Size of the environnement. */
	public final Dimension size;
	
	/** List of all ressources/obstacles in the environnement. */
	public List<FixedObject> entities = new ArrayList<FixedObject>();
	
	/** List of all agents in the environnement. */
	public List<AgentEntity> agents = new ArrayList<AgentEntity>();
	
	/**
	 * Create a new environnement of a given size.
	 * 
	 * @param size  The size of the environnement.
	 */
	public Environnement(Dimension size)
	{
		this.size = new Dimension(size);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,size.width,size.height), gridMaxDepth);
	}
	
	/**
	 * Create a new environnement of a given size.
	 * 
	 * @param width  The width of the environnement.
	 * @param height  The height of the environnement.
	 */
	public Environnement(int width, int height)
	{
		size = new Dimension(width, height);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,width,height), gridMaxDepth);
	}
	
	/**
	 * Create a new environnement of a given size, with ressources/obstacles.
	 * 
	 * @param size  The size of the environnement.
	 * @param entities  The existing entities.
	 */
	public Environnement(Dimension size, List<FixedObject> entities)
	{
		this.size = new Dimension(size);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,size.width,size.height), gridMaxDepth);
		grid.addAll(entities);
		this.entities.addAll(entities);		
	}
	
	/**
	 * Create a new environnement of a given size.
	 * 
	 * @param width  The width of the environnement.
	 * @param height  The height of the environnement.
	 * @param entities  The existing entities.
	 */
	public Environnement(int width, int height, List<FixedObject> entities)
	{
		size = new Dimension(width, height);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,width,height), gridMaxDepth);
		grid.addAll(entities);
		this.entities.addAll(entities);		
	}
	
	/**
	 * Add an object ressource/wall in the environnement.
	 * 
	 * @param obj  The object.
	 */
	public void add(FixedObject obj)
	{
		grid.add(obj);
		entities.add(obj);
	}
	
	/**
	 * Add an agent in the environnement.
	 * 
	 * @param agent  The agent.
	 */
	public void add(AgentEntity agent)
	{
		agents.add(agent);
	}
	
	/**
	 * Add multiple objects ressource/wall in the environnement.
	 * 
	 * @param objs  A list of objects.
	 */
	public void addAll(List<FixedObject> objs)
	{
		grid.addAll(objs);
		entities.addAll(objs);
	}
	
	/**
	 * Get the list of object who collide whith a certain hitbox.
	 * 
	 * @param hitbox  The hitbox, not null.
	 * 
	 * @return The list of object.
	 */
	public List<FixedObject> collide(Rectangle hitbox)
	{
		List<FixedObject> trueCollision = new ArrayList<FixedObject>();
		List<FixedObject> possible = grid.getPossibleCollisions(hitbox);
		for (FixedObject obj : possible)
		{
			if (collide(obj.hitbox(), hitbox))
			{
				trueCollision.add(obj);
			}
		}
		return trueCollision;
	}	
	
	/**
	 * Test if two hitbox collide.
	 * 
	 * @param a The first hitbox.
	 * @param b The second hitbox.
	 * 
	 * @return True if the two hitbox collide.
	 */
	private boolean collide(Rectangle a, Rectangle b)
	{
		return a.x + a.width > b.x &&
				a.y + a.height > b.y &&
				a.x < b.x + b.width &&
				a.y < b.y + b.height;
	}
	
	/**
	 * Create a new ressource in the environnement.
	 * 
	 * @param ressource  The ressource.
	 * @param position  The position of the ressource.
	 */
	public void createRessource(Entry<String, Integer> ressource, Point position)
	{
		switch(ressource.getKey())
		{
			case "food":
				add(new Food(this,position,ressource.getValue()));
				break;
		}
	}
	
	/**
	 * Get the number of agent created in the environnement
	 * and increase the counter.
	 * 
	 * @return The number of agent who have been created.
	 */
	synchronized public static int getCount()
	{
		return count++;
	}
	
	/**
	 * Remove an object from the environnement.
	 * 
	 * @param obj  The object.
	 * 
	 * @return  True if the object has been removed, else false.
	 */
	public boolean remove(FixedObject obj)
	{
		return entities.remove(obj) && grid.remove(obj);
	}
	
	/**
	 * Remove an agent from the environnement.
	 * 
	 * @param agent  The agent.
	 * 
	 * @return  True if the agent has been removed, else false.
	 */
	 public boolean remove(AgentEntity agent)
	{
		return agents.remove(agent);
	}
}
