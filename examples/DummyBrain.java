import forager.agents.message.*;
import forager.brain.Brain;
import forager.model.FixedObject;
import forager.util.Vector2D;

import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.util.List;
import java.util.Set;

public class DummyBrain extends Brain
{
	
	java.util.Random a = new java.util.Random(50);
	java.util.Random b = new java.util.Random(846532);
	
	@Override
	public void update()
	{
		//~ eat(1);
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
		if (nearest == null)
		{				
			int i = Math.round(a.nextFloat()),
				j = Math.round(b.nextFloat());
				
			moveBy(new Vector2D(i,j));
		}
		else
		{
			//~ System.out.println(nearest.harvestable()+ " " + nearest.type());
			//~ System.out.println(hunger() + " " +getQuantity("Food"));
			moveBy(goTo(nearest));
		}
		
		Set<String> harvestable = harvestable();
		if (harvestable.contains("Food"))
		{
			harvest("Food");
		}
		
		if (hunger() > 0.5)
		{
			eat(10);
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
		return new Vector2D(m,a);
	}
	
	public void handleMessage()
	{
		for (MessageContent mc : getMessages())
		{
			switch (mc.type())
			{
				case "rCopulationOffer":
					if (((Reply)mc).value)
						copulate(mc);
					break;
					
				case "CopulationOffer":
					replyTo(mc,true);
					break;
					
				case "RequireFoodMessage":
					give(mc.sender, "Food",10);
					break;
			}
		}
	}
}
