package agents;

import agents.message.MessageContent;
import model.FixedObject;
import model.Inventory;
import util.Vector2D;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

public interface AgentEntity
{
	String defaultCommunity = "global";
	String defaultGroup = "global";
	String defaultRole = "mobileAgent";
	String foodType = "food";
	int childCost = 50;
	int width = 5;
	int height = 5;
	
	int add(String item, int weight); 
	void broadcast(MessageContent message);
	boolean copulate(AgentInfo ai);
	boolean createAgent(Point pos);
	void die();
	int drop(String item, int quantity);
	void eat(int quantity);
	Vector2D direction();
	int id();
	void give(AgentInfo ai, String item, int quantity);
	AgentEntity getAgent(int id);
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
	Inventory inventory();
	Vector2D moveBy(Vector2D direction);
	void sendMessage(MessageContent message, AgentInfo ai);
	String team();
	void update();
}
