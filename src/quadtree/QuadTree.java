package quadtree;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class QuadTree<T extends QuadTreeElement>
{
	protected List<T> entities = new ArrayList<T>();
	protected List<T> allEntities = new ArrayList<T>();
	/** NW, NE, SE, SW */
	protected List<QuadTree<T>> children = null;
	
	protected Rectangle boundaries;
	protected final int maxDepth;
	protected final int depth;
	protected final boolean isLeaf;
	protected boolean split=false;
	
	public QuadTree(Rectangle boundaries,int maxDepth)
	{
		this.boundaries = new Rectangle(boundaries);
		this.maxDepth = maxDepth;
		depth = 0;
		isLeaf = false;
	}
	
	private QuadTree(Rectangle boundaries, int maxDepth, int depth)
	{
		this.boundaries = new Rectangle(boundaries);
		this.maxDepth = maxDepth;
		this.depth = depth;
		isLeaf = depth == maxDepth;
	}
	
	public boolean add(T element)
	{
		if (!insideBoundaries(element.bounds()))
			return false;
		
		allEntities.add(element);
		if (!isLeaf)
		{
			if (!split)
				split();
			
			if (children.get(0).add(element)) {return true;}
			if (children.get(1).add(element)) {return true;}
			if (children.get(2).add(element)) {return true;}
			if (children.get(3).add(element)) {return true;}
		}
		
		entities.add(element);
		return true;
	}
	
	public void addAll(List<T> elements) 
	{
		for (T e : elements)
			add(e);
	}
	
	public boolean remove(T element)
	{
		allEntities.remove(element);
		return entities.remove(element) || children.get(getIndex(element.bounds())).remove(element);
	}
	
	public List<T> getPossibleCollisions(Rectangle bounds)
	{
		List<T> temp = new ArrayList<T>();
		if (!insideBoundaries(bounds))
		{
			temp.addAll(allEntities);
			return temp;
		}
		
		if (isLeaf)
		{
			temp.addAll(entities);
			return temp;
		}
			
		int index = getIndex(bounds);
		if (index == -1)
		{
			temp.addAll(entities);
			if (split)
			{
				for (QuadTree<T> child : children)
				{
					List<T> res = child.getPossibleCollisions(clamp(bounds, child.boundaries));
					if (res != null)
					{
						temp.addAll(res);
					}
				}
			}
		}
		else
		{
			temp.addAll(children.get(index).getPossibleCollisions(bounds));
		}
		return temp;
		
	}
	
	private boolean insideBoundaries(Rectangle bounds)
	{
		return ((bounds.x >= boundaries.x) && (bounds.x + bounds.width <= boundaries.x + boundaries.width) &&
			(bounds.y >= boundaries.y) && (bounds.y + bounds.height <= boundaries.y + boundaries.height) &&
			bounds.width > 0 && bounds.height > 0);
	}
	
	private int getIndex(Rectangle bounds)
	{
		if (isLeaf || !split)
			return -1;
		int midX = boundaries.x + (boundaries.width/2),
			midY = boundaries.y + (boundaries.height/2);
			
		int boundsEndX = bounds.x + bounds.width,
			boundsEndY = bounds.y + bounds.height;
			
		if ((boundsEndX <= midX) && (boundsEndY <= midY))
			return 0;
		if ((bounds.x > midX) && (boundsEndY <= midY))
			return 1;
		if ((bounds.x > midX) && (bounds.y > midY))
			return 2;
		if ((boundsEndX <= midX) && (bounds.y > midY))
			return 3;
		// Doesn't fit in any children
		return -1;
	}
	
	private void split()
	{
		int leftWidth = boundaries.width/2,
			topHeight = boundaries.height/2;
		
		int rightStartX = boundaries.x + leftWidth + 1,
			bottomStartY = boundaries.y + topHeight +1,
			rightWidth = boundaries.width - (leftWidth +1),
			bottomHeight = boundaries.height - (topHeight +1);
			
		children = new ArrayList<QuadTree<T>>();
		children.add(new QuadTree<T>(new Rectangle(boundaries.x, boundaries.y, leftWidth, topHeight), maxDepth, depth+1));
		children.add(new QuadTree<T>(new Rectangle(rightStartX, boundaries.y, rightWidth, topHeight), maxDepth, depth+1));
		children.add(new QuadTree<T>(new Rectangle(rightStartX, bottomStartY, rightWidth, bottomHeight), maxDepth, depth+1));
		children.add(new QuadTree<T>(new Rectangle(boundaries.x, bottomStartY, leftWidth, bottomHeight), maxDepth, depth+1));
		split = true;
	}
	
	private Rectangle clamp(Rectangle original, Rectangle aim)
	{
		if ((original.y < aim.y + aim.height) || (original.y + original.height > aim.y) ||
			(original.x < aim.x + aim.width) || (original.x + original.width > aim.x))
			return new Rectangle(0,0,0,0);
		
		
		int x = (original.x < aim.x) ? aim.x : Math.max(original.x,aim.x),
			y = (original.y < aim.y) ? aim.y : Math.max(original.y,aim.y),
			endX = (original.x + original.width  > aim.x+aim.width) ? aim.x + aim.width : Math.max(aim.x, original.x + original.width),
			endY = (original.y + original.height  > aim.y+aim.height) ? aim.y + aim.height : Math.max(aim.y, original.y + original.height);
		return new Rectangle(x,y,endX - x, endY - y);
		
	}
	
	public void draw(Graphics g)
	{
		g.drawRect(boundaries.x, boundaries.y, boundaries.width, boundaries.height);
		if (!split)
			return ;
		for (QuadTree<T> child: children)
		{
			child.draw(g);
		}
	}
}
