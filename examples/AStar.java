import forager.brain.Brain;
import forager.model.FixedObject;
import forager.util.Vector2D;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class AStar implements Comparator<Rectangle>
{
	PriorityQueue<Rectangle> openSet = new PriorityQueue<Rectangle>(10,this);
	List<Rectangle> closedSet = new ArrayList<Rectangle>();
	HashMap<Rectangle,Rectangle> came_from = new HashMap<Rectangle,Rectangle>();
	
	HashMap<Rectangle, Double> f_score = new HashMap<Rectangle, Double>();
	HashMap<Rectangle, Double> g_score = new HashMap<Rectangle, Double>();
	
	List<FixedObject> goals = new ArrayList<FixedObject>();
	List<FixedObject> obstacles = new ArrayList<FixedObject>();
	
	Rectangle start;
	int max;
	
	public AStar(Rectangle start, List<FixedObject> objects, int max)
	{
		this.start = start;
		this.max = max;
		
		for (FixedObject f : objects)
			((f.harvestable()) ? goals : obstacles).add(f);
			
		openSet.add(start);
		g_score.put(start,0.0);
		f_score.put(start,distance(start,goals));
	}
	
	public boolean collide(Rectangle r, List<FixedObject> fo)
	{
		for (FixedObject obj : fo)
		{
			Rectangle box = obj.hitbox();
			if (r.x + r.width > box.x &&
				r.y + r.height > box.y &&
				r.x < box.x + box.width &&
				r.y < box.y + box.height)
				return true;
		}
		return false;
	}
	
	public int compare(Rectangle a, Rectangle b)
		{
			boolean ca = f_score.containsKey(a),
					cb = f_score.containsKey(b);
			
			if (!ca && !cb)
				return 0;
			if (!ca)
				return -1;
			if (!cb)
				return 1;
			
			double fa = f_score.get(a),
					fb = f_score.get(b);
			
			if (Math.abs(fa-fb) < 0.001)
				return 0;
			if (fa < fb)
				return -1;
			return 1;
		}
	
	public double distance(Rectangle r, List<FixedObject> fo)
	{
		double min = max;
		for (FixedObject obj : fo)
		{
			double dist = Brain.distance(r,obj.hitbox());
			if (dist < min)
			{
				min = dist;
				if (dist < 0.01)
					return 0;
			}
		}
		return min;
		
	}
	
	public Stack<Vector2D> getDirection(Rectangle curr, Rectangle prev)
	{
		Stack<Vector2D> res = new Stack<Vector2D>();
		
		while (came_from.containsKey(curr))
		{
			Rectangle from = came_from.get(curr);
			res.push(new Vector2D(curr.x - from.x, curr.y - from.y));
			curr = from;
		}
		
		return res;
		/*
		if (came_from.containsKey(curr))
			return getDirection(came_from.get(curr),curr);
		
		return new Vector2D(prev.x - curr.x, prev.y - curr.y);
		*/
	}
	
	public List<Rectangle> getNeighbors(Rectangle current)
	{
		List<Rectangle> res = new ArrayList<Rectangle>();
		int w = current.width,
			h = current.height,
			x = current.x,
			y = current.y;
		res.add(new Rectangle(x-1,y,w,h));
		res.add(new Rectangle(x-1,y-1,w,h));
		res.add(new Rectangle(x-1,y+1,w,h));
		
		res.add(new Rectangle(x+1,y,w,h));
		res.add(new Rectangle(x+1,y-1,w,h));
		res.add(new Rectangle(x+1,y+1,w,h));
		
		res.add(new Rectangle(x,y-1,w,h));
		res.add(new Rectangle(x,y+1,w,h));
		
		return res;
	}
		
	public Stack<Vector2D> solve()
	{
		while (!openSet.isEmpty())
		{
			Rectangle current = openSet.poll();
			if (collide(current,goals))
			{
				return getDirection(current,current);
			}
			
			closedSet.add(current);
			for (Rectangle neighbor : getNeighbors(current))
			{
				if (collide(neighbor,obstacles) || closedSet.contains(neighbor))
					continue;
				
				double tentative_g_score = g_score.get(current) + Brain.distance(current,neighbor);
				if (tentative_g_score > max)
					continue;
				if (!openSet.contains(neighbor) || tentative_g_score < g_score.get(neighbor))
				{
					came_from.put(neighbor,current);
					g_score.put(neighbor,tentative_g_score);
					f_score.put(neighbor,tentative_g_score + distance(neighbor,goals));
					if (!openSet.contains(neighbor))
						openSet.add(neighbor);
				}
			}
		}
		return null;
	}
}
