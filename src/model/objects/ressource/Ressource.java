package model.object.ressource;

import model.Environnement;
import model.FixedObject;

//~ import se.afsa.quadtree.Bounds;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class Ressource extends FixedObject
{
	protected int weight = 50;
	
	public Ressource(Environnement env, Point pos, Rectangle hitbox, int quantity)
	{
		super(env,pos,hitbox);
		weight = quantity;
	}
	
	//~ public Ressource(Environnement env, Bounds bounds, int quantity)
	public Ressource(Environnement env, Rectangle bounds, int quantity)
	{
		super(env,bounds);
		weight = quantity;
	}
	
	public Ressource(Environnement env, Point pos, Rectangle hitbox)
	{
		super(env,pos,hitbox);
	}
	
	//~ public Ressource(Environnement env, Bounds bounds)
	public Ressource(Environnement env, Rectangle bounds)
	{
		super(env,bounds);
	}
}
