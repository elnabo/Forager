package forager.model.object.obstacle;

import forager.model.Environnement;
import forager.model.FixedObject;

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
