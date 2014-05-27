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


public class Environnement
{
	protected QuadTree<FixedObject> grid;
	public final Dimension size;
	public List<FixedObject> entities = new ArrayList<FixedObject>();
	public List<AgentEntity> agents = new ArrayList<AgentEntity>();
	
	private int gridMaxDepth = 7;
	
	public Environnement(Dimension size)
	{
		this.size = new Dimension(size);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,size.width,size.height), gridMaxDepth);
	}
	
	public Environnement(int width, int height)
	{
		size = new Dimension(width, height);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,width,height), gridMaxDepth);
	}
	
	public Environnement(Dimension size, List<FixedObject> entities)
	{
		this.size = new Dimension(size);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,size.width,size.height), gridMaxDepth);
		grid.addAll(entities);
		this.entities.addAll(entities);		
	}
	
	public Environnement(int width, int height, List<FixedObject> entities)
	{
		size = new Dimension(width, height);
		grid = new QuadTree<FixedObject>(new Rectangle(0,0,width,height), gridMaxDepth);
		grid.addAll(entities);
		this.entities.addAll(entities);		
	}
	
	public void add(FixedObject obj)
	{
		grid.add(obj);
		entities.add(obj);
	}
	
	public void add(AgentEntity agent)
	{
		agents.add(agent);
	}
	
	public void addAll(FixedObject... objs)
	{
		for (FixedObject o : objs)
		{
			grid.add(o);
			entities.add(o);
		}
	}
	
	public void addAll(List<FixedObject> objs)
	{
		grid.addAll(objs);
		entities.addAll(objs);
	}
	
	public boolean remove(FixedObject obj)
	{
		return entities.remove(obj) && grid.remove(obj);
	}
	
	public boolean remove(AgentEntity agent)
	{
		return agents.remove(agent);
	}
	
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
	
	private boolean collide(Rectangle a, Rectangle b)
	{
		return a.x + a.width > b.x &&
				a.y + a.height > b.y &&
				a.x < b.x + b.width &&
				a.y < b.y + b.height;
	}
	
	public void createRessource(Entry<String, Integer> ressource, Point position)
	{
		switch(ressource.getKey())
		{
			case "food":
				add(new Food(this,position,ressource.getValue()));
				break;
		}
	}
}
