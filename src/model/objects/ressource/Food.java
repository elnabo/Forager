package model.object.ressource;

import model.Environnement;

import java.awt.Point;
import java.awt.Rectangle;

public class Food extends Ressource
{	
	public Food(Environnement env, Point pos, int quantity)
	{
		super(env,pos,new Rectangle(0,0,5,5),quantity);
	}
	
	public Food(Environnement env, Rectangle bounds, int quantity)
	{
		super(env,bounds,quantity);
	}
	
	public Food(Environnement env, Point pos)
	{
		super(env,pos,new Rectangle(0,0,5,5));
	}
	
	public Food(Environnement env, Rectangle bounds)
	{
		super(env,bounds);
	}
	
	@Override
	public String type()
	{
		return "food";
	}
}
