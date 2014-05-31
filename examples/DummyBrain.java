import forager.agents.message.*;
import forager.brain.Brain;
import forager.model.FixedObject;
import forager.util.Vector2D;

import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
public class DummyBrain extends Brain
{
	
	java.util.Random a = new java.util.Random();
	java.util.Random b = new java.util.Random();
	boolean moved = true;
	boolean wantChild = false;
	boolean replyChild = false;
	
	@Override
	public void update()
	{
		super.update();
		
		handleMessage();/*
		if (hitbox().x == 10){
			//~ if (!hadChild){
				//~ sendMessage(createCopulationMessage(),getVisibleAgents().get(0));hadChild=true;}
			if (hunger() > 0.5)
				eat(10);
			if (getQuantity("Food") < 10)
			{
				broadcast(createFoodRequestMessage());
			}
			return;}
		*/
		FixedObject nearest = getNearestRessource();
		if (nearest == null || !moved)
		{		
			randomMove();		
			moved = true;
		}
		else
		{
			Vector2D dir = new AStar(hitbox(),getVisibleObjects(),visionRange()).solve();
			Vector2D t = moveBy(dir);
			moved = (dir == t);
		}
		
		Set<String> harvestable = harvestable();
		if (harvestable.contains("Food"))
		{
			harvest("Food");
			broadcast(createLocationMessage(hitbox(),"Foood"));
		}
		
		if (hunger() > 0.5)
		{
			eat(10);
		}
		
		int foodQuantity = getQuantity("Food");
		if (foodQuantity < 10)
		{
			broadcast(createFoodRequestMessage());
		}
		else if (foodQuantity > 80)
		{
			wantChild = true;
			broadcast(createCopulationMessage());
		}
		
		
	}
	
	public FixedObject getNearestRessource()
	{
		List<FixedObject> visible = getVisibleObjects();
		Rectangle hitbox = hitbox();
		double minDistance = Double.MAX_VALUE;
		FixedObject nearest = null;
		for (FixedObject obj : visible)
		{
			double dist = distance(obj.hitbox(), hitbox);
			if (dist < minDistance && obj.harvestable())
			{
				minDistance = dist;
				nearest = obj;
			}
		}
		return nearest;
	}
	
	public Vector2D goTo(FixedObject obj)
	{
		Rectangle me = hitbox();
		Rectangle aim = obj.hitbox();
		Point2D.Double m = new Point2D.Double(me.x + me.width/2, me.y + me.height/2);
		Point2D.Double a = new Point2D.Double(aim.x + aim.width/2, aim.y + aim.height/2);
		return new Vector2D(m,a).unitVector();
	}
	
	public void handleMessage()
	{
		for (MessageContent mc : getMessages())
		{
			switch (mc.type())
			{
				case "LocationMessage":
					break;
				
				case "rCopulationOffer":
					if (((Reply)mc).value)
						copulate(mc);
					break;
					
				case "CopulationOffer":
					replyTo(mc,(getQuantity("Food") > 75));
					break;
					
				case "RequireFoodMessage":
					if (getQuantity("Food") > 20)
						give(mc.sender, "Food",10);
					break;
			}
		}
	}
	
	public void randomMove()
	{
		double theta = 2*Math.PI*a.nextDouble();
				
		moveBy(Vector2D.fromPolar(1,theta));
	}
	
}
