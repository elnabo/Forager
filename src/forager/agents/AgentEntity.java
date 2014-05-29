package forager.agents;

import forager.agents.message.MessageContent;
import forager.model.FixedObject;
import forager.model.Inventory;
import forager.util.Vector2D;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;


/**
 * <p>
 * An agent in the Forager Game system.
 * </p><p>
 * The AgentEntity interface has to be redefined for each Multiagent System in order
 * to use, their own implementation correctly.
 * </p><p>
 * This provides method to add and remove the agents from the environnement.
 * </p><p>
 * This can communicate with other agent with broadcast or one to one communication.
 * </p><p>
 * This handles deplacement, harvest, hungerness and droping;
 * </p>
 * 
 * <p>This must contain an instance of {@link forager.brain.Brain Brain}.
 * 
 * @author Guillaume Desquesnes
 */
public interface AgentEntity
{
	/** Item type for food/energy. */
	String foodType = "Food";
	/** 
	 * Cost in food/energy to produce a children.
	 * The cost is shared between all genitor.
	 */
	int childCost = 50;
	
	/** Width of the agent. */
	int width = 5;
	/** Height of the agent. */
	int height = 5;
	
	/** Add the item or a part of it in the inventory
	 * if it fit.
	 * 
	 * @param item  The type of item to add, not null.
	 * @param quantity  The quantity of item.
	 * 
	 * @return The quantity of item added.
	 */
	int add(String item, int quantity); 
	
	/**
	 * Broadcast a message to all the agents in range.
	 * <p>
	 * If possible it should use the multiagents system
	 * way to communicate to handle the communication.
	 * Else the message will be added directly via
	 * {@link #recieveMessageFromOtherType(MessageContent) recieveMessageFromOtherType}
	 * </p>
	 * 
	 * @param message  The message to broadcast, not null.
	 */
	void broadcast(MessageContent message);
	
	/**
	 * Test if the agent is in position to harvest.
	 * Doesn't test if the agent has the capacity or
	 * the space to harvest.
	 * 
	 * @return True if the agent is in positon to harvest, else false.
	 */
	boolean canHarvest();
	
	/**
	 * Test if an agent collide with a given hitbox.
	 * 
	 * @param ae  The agent.
	 * @param box  The hitbox.
	 * 
	 * @return True if the agent collide, else false.
	 */
	boolean collide(AgentEntity ae, Rectangle box);
	
	/**
	 * Return the color of the agent.
	 * Used only by the UI.
	 * 
	 * @return The color of the agent.
	 */
	Color color();
	
	/**
	 * Try to create a child with the help of an other agent.
	 * Creating a child cost food/energy.
	 * The child will recieve half his conception cost of food/energy in 
	 * his inventory.
	 * 
	 * @param ai  The other agent, not null.
	 * 
	 * @return True if a child is born, else false.
	 */
	boolean copulate(AgentInfo ai);
	
	/**
	 * Create an agent on a given location.
	 * The agent is created with half his conception cost of food/energy
	 * in his inventory.
	 * 
	 * @param pos  The location, not null.
	 * 
	 * @return True if the agent has been created, else false.
	 */
	boolean createAgent(Point pos);
	
	/**
	 * Kill the agent.
	 */
	void die();
	
	/**
	 * Drop a certain quantity of item on the ground.
	 * 
	 * <p><strong>Not implemented yet.</strong></p>
	 * 
	 * @return The quantity of item droped.
	 */
	int drop(String item, int quantity);
	
	/**
	 * Eat a certain quantity of food/energy.
	 * Decrease your hunger.
	 * 
	 * @param  quantity The quantity of food/energy to eat.
	 */
	void eat(int quantity);
	
	/**
	 * Return the current direction/orientation vector of the agent.
	 * 
	 * @return The current direction/orientation vector of the agent.
	 */
	Vector2D direction();
	
	/**
	 * Return the id of the agent.
	 * 
	 * @return The id of the agent.
	 */
	int id();
	
	/**
	 * Give a certain quantity of an item to an other agent from
	 * your inventory.
	 * 
	 * Works by sending a {@link forager.agents.message.RessourceMessage} to the
	 * other agent.
	 * 
	 * If you give too much, you'll get back what the other can't store.
	 * 
	 * @param ai  The other agent, not null.
	 * @param item  The item type, not null.
	 * @param quantity  The quantity of item to give.
	 */
	void give(AgentInfo ai, String item, int quantity);
	
	/**
	 * Get an agent from its info.
	 * 
	 * @param ai  The agent's info.
	 * 
	 * @return The agent if it exist in the environnement, else null.
	 */
	AgentEntity getAgent(AgentInfo ai);
	
	/**
	 * Get the list of objects who collide with the given hitbox.
	 * 
	 * @param hitbox  The hitbox.
	 * 
	 * @return The list of objects who collide.
	 */
	List<FixedObject> getCollision(Rectangle hitbox);
	
	/**
	 * Get the list of objects who collide with the entity after
	 * moving from a vector.
	 * 
	 * @param mvment  The vector of movement.
	 * 
	 * @return The list of objects who collide.
	 */
	List<FixedObject> getCollisionAfterMovement(Vector2D mvment);
	
	/**
	 * Return the list of messages recieved.
	 * 
	 * @return The list of messages recieved.
	 */
	List<MessageContent> getMessages();
	
	/**
	 * Get the info on all the visible agents.
	 * 
	 * @return  The list of info on all the visible agents.
	 */
	List<AgentInfo> getVisibleAgents();
	
	/**
	 * Get the list of all the visible objects.
	 * 
	 * @return The list of all the visible objects.
	 */
	List<FixedObject> getVisibleObjects();
	
	
	/**
	 * Preprocess the message recieved.
	 * 
	 * <p> The {@link forager.agents.message.RessourceMessage RessourceMessage}
	 * will be added to the inventory and if there is too much it's automaticaly
	 * send back.</p>
	 */
	void handleMessage();
	
	/**
	 * Harvest the maximum ressource of this type who collide
	 * with the entity.
	 * 
	 * @param type The type of ressource.
	 * 
	 * @return The quantity of ressource harvested.
	 */
	int harvest(String type);
	
	/**
	 * Test if a message has been recieved.
	 * 
	 * @return True if a message has been recieved.
	 */
	boolean haveMessage();
	
	/**
	 * Return the agent's hitbox.
	 * 
	 * @return The hitbox of entity.
	 */
	Rectangle hitbox();
	
	/**
	 * Return the agent's percentage of hungriness.
	 * 
	 * @return The percentage of hungriness.
	 */
	double hunger();
	
	/**
	 * Increase the level of hunger.
	 * 
	 * Kill the agents if it's too high.
	 */
	void increaseHunger();
	
	/**
	 * Return the agent's information.
	 * 
	 * @return The agent's information.
	 */
	AgentInfo info();
	
	/**
	 * Return the agent's inventory.
	 * 
	 * @return The agent's inventory.
	 */
	Inventory inventory();
	
	/**
	 * Test if the message has been recieved.
	 * Used to verify that the brain doesn't forge
	 * fake message.
	 * 
	 * @param message  The message.
	 * 
	 * @return True if the message has been recieved, else false.
	 */
	boolean isMessage(MessageContent message);
	
	/**
	 * Move the agent according to a vector.
	 * 
	 * The vector norm will be reduce to 1, if it
	 * is higher.
	 * 
	 * @param direction  The vector of movement, not null.
	 * 
	 * @return The effective movement vector.
	 */
	Vector2D moveBy(Vector2D direction);
	
	/**
	 * Recieve a message from an incompatible multiagents system.
	 * 
	 * @param message  The message, not null.
	 */
	void recieveMessageFromOtherType(MessageContent message);
	
	/**
	 * Send a message to an other agent.
	 * <p>
	 * If possible it should use the multiagents system
	 * way to communicate to handle the communication.
	 * Else the message will be added directly via
	 * {@link #recieveMessageFromOtherType(MessageContent) recieveMessageFromOtherType}
	 * </p>
	 * 
	 * @param message  The message, not null.
	 * @param ai  The agent information, not null.
	 */
	void sendMessage(MessageContent message, AgentInfo ai);
	
	/**
	 * Return the team of the agent.
	 * <p><strong> Not used yet.</strong></p>
	 * 
	 * @return The team of the agent.
	 */
	String team();
	
	/**
	 * Update the agent.
	 * 
	 * It must call once {@link #increaseHunger() increaseHunger} and
	 * {@link forager.brain.Brain#update()}
	 */
	void update();
}
