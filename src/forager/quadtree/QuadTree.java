package forager.quadtree;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Quadtree datastructure.
 * 
 * @author Guillaume Desquesnes
 */
public class QuadTree<T extends QuadTreeElement>
{
	/** List of element contained directly in this cell. */
	protected List<T> entities = new ArrayList<T>();
	
	/** List of element contained in this cell and all subcell. */
	protected List<T> allEntities = new ArrayList<T>();
	
	/** List of this node childre, NW, NE, SE, SW */
	protected List<QuadTree<T>> children = null;
	
	/** This node boundaries. */
	protected Rectangle boundaries;
	
	/** Max depth of the tree. */
	protected final int maxDepth;
	
	/** Current depth of the tree. */
	protected final int depth;
	
	/** If the node is a leaf. */
	protected final boolean isLeaf;
	
	/** If the node has been splited. */
	protected boolean split=false;
	
	/**
	 * Create a new QuadTree of a given size.
	 * 
	 * @param boundaries  The hitbox of the Quadtree.
	 * @param maxDepth  The maximum depth of the tree.
	 */
	public QuadTree(Rectangle boundaries,int maxDepth)
	{
		this.boundaries = new Rectangle(boundaries);
		this.maxDepth = maxDepth;
		depth = 0;
		isLeaf = false;
	}
	
	/**
	 * Create a node of QuadTree of a given size.
	 * 
	 * @param boundaries  The hitbox of the Quadtree.
	 * @param maxDepth  The maximum depth of the tree.
	 * @param depth  The current depth of the node.
	 */
	private QuadTree(Rectangle boundaries, int maxDepth, int depth)
	{
		this.boundaries = new Rectangle(boundaries);
		this.maxDepth = maxDepth;
		this.depth = depth;
		isLeaf = depth == maxDepth;
	}
	
	/**
	 * Add a new element to the node.
	 * 
	 * @param element  The element.
	 * 
	 * @return True if the element has been added, 
	 * false if it is outside of the boundaries.
	 */
	public boolean add(T element)
	{
		if (!insideBoundaries(element.hitbox()))
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
	
	/**
	 * Add multiple elements to the tree.
	 * 
	 * @param elements  The list of elements.
	 */
	public void addAll(List<T> elements) 
	{
		for (T e : elements)
			add(e);
	}
	
	/**
	 * Remove an element from the tree;
	 * 
	 * @param element  The element.
	 * 
	 * @return True if an element has been removed, else false.
	 */
	public boolean remove(T element)
	{
		allEntities.remove(element);
		return entities.remove(element) || children.get(getIndex(element.hitbox())).remove(element);
	}
	
	/**
	 * Get the list of object who are in the same node
	 * as the given hitbox.
	 * 
	 * @param hitbox  The hitbox.
	 * 
	 * @return The list of object who are in the same node.
	 */
	public List<T> getPossibleCollisions(Rectangle hitbox)
	{
		List<T> temp = new ArrayList<T>();
		
		/*
		 * If it's not totally in the tree 
		 * return all the elements.
		 */
		if (!insideBoundaries(hitbox))
		{
			temp.addAll(allEntities);
			return temp;
		}
		
		// If it's a leaf return all elements.
		if (isLeaf)
		{
			temp.addAll(entities);
			return temp;
		}
			
		int index = getIndex(hitbox);
		// If doesn't fit in one node.
		if (index == -1)
		{
			// Grap all entities in this node
			temp.addAll(entities);
			if (split)
			{
				// And those in the node who collide with the hitbox.
				for (QuadTree<T> child : children)
				{
					List<T> res = child.getPossibleCollisions(clamp(hitbox, child.boundaries));
					if (res != null)
					{
						temp.addAll(res);
					}
				}
			}
		}
		// If it fit in one node look further.
		else
		{
			temp.addAll(children.get(index).getPossibleCollisions(hitbox));
		}
		return temp;
		
	}
	
	/**
	 * Test if a given rectangle is in the tree.
	 * 
	 * @param bounds  The rectangle.
	 * 
	 * @return True if the rectangle is contained in the tree, else false.
	 */
	private boolean insideBoundaries(Rectangle bounds)
	{
		return ((bounds.x >= boundaries.x) && (bounds.x + bounds.width <= boundaries.x + boundaries.width) &&
			(bounds.y >= boundaries.y) && (bounds.y + bounds.height <= boundaries.y + boundaries.height) &&
			bounds.width > 0 && bounds.height > 0);
	}
	
	/**
	 * Get the index of the node who contains the rectangle.
	 * 
	 * @return The index of the node who contains the rectangle or -1
	 * if no node contains all the rectangle.
	 */
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
	
	/**
	 * Split the current node in four.
	 */
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
	
	/**
	 * Clamp a rectangle to an other one.
	 * 
	 * @param original  The rectangle to clamp.
	 * @param aim  The other one.
	 * 
	 * @return The clamped rectangle.
	 */
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
}
