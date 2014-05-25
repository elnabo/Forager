package brain;

//~ import agents.AgentEntity;
import agents.message.*;
import model.FixedObject;
import util.Vector2D;

import java.util.List;

public class DummyBrain extends Brain
{
	
	boolean hadChild = false;
	
	@Override
	public void update()
	{
		//~ eat(1);
		handleMessage();
		if (hitbox().x == 10){
			//~ if (!hadChild){
				//~ sendMessage(createCopulationMessage(),getVisibleAgents().get(0));hadChild=true;}
			if (hunger() > 0.5)
				eat(10);
			if (getQuantity("food") < 10)
			{
				broadcast(createFoodRequestMessage());
			}
			return;}
			
		java.util.Random a = new java.util.Random();
		Vector2D mvment = new Vector2D(Math.round(a.nextFloat()),Math.round(a.nextFloat()));
		//~ Vector2D mvment = new Vector2D(1,1);
		
		List<FixedObject> possibleCollisions = getCollisionAfterMovement(mvment);
		if (possibleCollisions != null && possibleCollisions.size() != 0)
		{
			harvest();
		}
		else
		{
			moveBy(mvment);
		}
		
		if (hunger() > 0.5)
			eat(10);
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
					give(mc.sender, "food",10);
					break;
			}
		}
	}
}
