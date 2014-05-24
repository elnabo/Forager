package agents;

import model.FixedObject;
import util.Vector2D;

import java.awt.Rectangle;
import java.util.List;

public interface AgentEntity
{
	int add(String item, int weight); 
	void die();
	int drop(String item, int quantity);
	void eat(int quantity);
	Vector2D direction();
	List<FixedObject> getCollision(Rectangle hitbox);
	List<FixedObject> getCollisionAfterMovement(Vector2D mvment);
	//~ List<AgentEntity> getVisibleNeighbors();
	List<FixedObject> getVisibleObjects();
	int harvest();
	Rectangle hitbox();
	double hunger();
	void increaseHunger();
	void moveBy(Vector2D direction);
	void update();
}
