package forager.brain;

import forager.agents.message.*;
import forager.model.FixedObject;
import forager.util.Vector2D;

import java.util.List;

public class DummyBrain extends Brain
{
	
	java.util.Random a = new java.util.Random(50);
	java.util.Random b = new java.util.Random(846532);
	
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
			
		int i = Math.round(a.nextFloat()),
			j = Math.round(b.nextFloat());
			
		i = 1;j=1;
			
		Vector2D mvment = new Vector2D(i,j);
		if (mvment != moveBy(mvment))
		{
			harvest("food");
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
