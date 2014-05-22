package model;

import quadtree.QuadTreeElement;

import java.awt.Point;
import java.awt.Rectangle;


public abstract class FixedObject implements QuadTreeElement
{
	protected Rectangle bounds;
	protected Point position;
	protected Environnement environnement;
	
	public FixedObject(Environnement env, Point pos, Rectangle hitbox)
	{
		environnement = env;
		this.position = new Point(pos);
		bounds = new Rectangle(pos.x, pos.y, hitbox.width, hitbox.height);
	}
	public FixedObject(Environnement env, Rectangle bounds)
	{
		environnement = env;
		position = new Point(bounds.x, bounds.y);
		this.bounds = new Rectangle(bounds);
	}
	
	public Rectangle bounds()
	{
		return new Rectangle(bounds);
	}
	
	public Point getPosition()
	{
		return new Point(position);
	}
	
	public void delete()
	{
		environnement.remove(this);
	}
	
	public String type()
	{
		return "none";
	}
}
