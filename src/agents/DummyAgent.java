package agents;

import model.Environnement;
import model.FixedObject;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Random;

public class DummyAgent extends MobileAgent
{ 
	private static final long serialVersionUID = -68366833257439L;
	private Random generator = new Random();
	
	public int dir = 1;
	
	public DummyAgent(Environnement environnement)
	{
		super(environnement, new Point(50,50));
	}
	
	public DummyAgent(Environnement environnement, Point pos)
	{
		super(environnement,pos);
	}
	
	@Override
	public void update()
	{
		Point mvment = new Point((int)(generator.nextFloat()*3)-1,(int)(generator.nextFloat()*3)-1);
		mvment = new Point(dir,0);
		hitbox.x += mvment.x;
		hitbox.y += mvment.y;
		
		List<FixedObject> possibleCollisions = environnement.collide(hitbox());
		if (possibleCollisions != null && possibleCollisions.size() != 0)
		{
			hitbox.x -= mvment.x;
			hitbox.y -= mvment.y;
			dir *= -1;
		}
	}

}
