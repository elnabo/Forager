package model.object.obstacle;

import model.Environnement;
import model.FixedObject;

import java.awt.Point;
import java.awt.Rectangle;

public class Wall extends FixedObject
{
	public Wall(Environnement env, Point pos, Rectangle hitbox)
	{
		super(env,pos,hitbox);
	}
	
	public Wall(Environnement env, Rectangle bounds)
	{
		super(env,bounds);
	}
	
	@Override
	public String type()
	{
		return "wall";
	}
}
