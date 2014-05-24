package agents;

import agents.message.MessageContent;
import model.FixedObject;
import util.Vector2D;

import java.awt.Rectangle;
import java.util.List;

public interface AgentEntity
{
	String defaultCommunity = "global";
	String defaultGroup = "global";
	String defaultRole = "mobileAgent";
	
	int add(String item, int weight); 
	void die();
	int drop(String item, int quantity);
	void eat(int quantity);
	Vector2D direction();
	void give(AgentInfo ai, String item, int quantity);
	List<FixedObject> getCollision(Rectangle hitbox);
	List<FixedObject> getCollisionAfterMovement(Vector2D mvment);
	List<MessageContent> getMessages();
	List<AgentInfo> getVisibleAgents();
	List<FixedObject> getVisibleObjects();
	int harvest();
	Rectangle hitbox();
	double hunger();
	void increaseHunger();
	AgentInfo info();
	void moveBy(Vector2D direction);
	String team();
	void update();
}
