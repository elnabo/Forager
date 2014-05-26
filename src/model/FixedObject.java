package model;

import quadtree.QuadTreeElement;

import java.awt.Point;
import java.awt.Rectangle;


public abstract class FixedObject implements QuadTreeElement
{
	protected Rectangle hitbox;
	protected Point position;
	protected Environnement environnement;
	
	public FixedObject(Environnement env, Point pos, Rectangle hitbox)
	{
		environnement = env;
		this.position = new Point(pos);
		this.hitbox = new Rectangle(pos.x, pos.y, hitbox.width, hitbox.height);
	}
	public FixedObject(Environnement env, Rectangle hitbox)
	{
		environnement = env;
		position = new Point(hitbox.x, hitbox.y);
		this.hitbox = new Rectangle(hitbox);
	}
	
	public Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}
	
	public Point getPosition()
	{
		return new Point(position);
	}
	
	protected void delete()
	{
		environnement.remove(this);
	}
	
	public String type()
	{
		return "none";
	}
	
	public boolean harvestable()
	{
		return false;
	}
}
