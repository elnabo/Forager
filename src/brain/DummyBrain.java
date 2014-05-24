package brain;

//~ import agents.AgentEntity;
import model.FixedObject;
import util.Vector2D;

import java.util.List;

public class DummyBrain extends Brain
{
	
	@Override
	public void update()
	{
		Vector2D mvment = new Vector2D(1,1);
		
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
}
