package forager.brain;

import forager.agents.AgentEntity;
import forager.agents.AgentInfo;
import forager.agents.message.CopulationOffer;
import forager.agents.message.LocationMessage;
import forager.agents.message.MessageContent;
import forager.agents.message.Reply;
import forager.agents.message.RequireFoodMessage;
import forager.model.FixedObject;
import forager.util.Vector2D;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Brain of a Forager Game agent.
 * 
 * <p>
 * Subclasses of this have to be redeveloped by participant.
 * All the actions have to be taken during update, which increase hunger.
 * </p>
 * 
 * <p> No methods except update can be overriden. </p>
 * 
 * @author Guillaume Desquesnes
 */
public abstract class Brain
{
	/** Entity controled by this brain. */
	private AgentEntity parent;
	
	/**
	 * Initialize a new brain.
	 * 
	 * @param parent  The brain owner.
	 */
	public final Brain init(AgentEntity parent)
	{
		this.parent = parent;
		return this;
	}
	
	/**
	 * Broadcast a message to all the agent in range.
	 * 
	 * @param message  The message.
	 */
	public final void broadcast(MessageContent message)
	{
		if (message instanceof Reply)
			return;
		parent.broadcast(message);
	}
	
	/**
	 * Test if the agent can harvest.
	 * 
	 * @return True if the agent can harvest, else false.
	 */
	public final boolean canHarvest()
	{
		return parent.canHarvest();
	}
	
	/**
	 * Create a message to ask for reproduction.
	 * 
	 * @return A reproduction demand message.
	 */
	public final MessageContent createCopulationMessage()
	{
		return new CopulationOffer(parent.info());
	}
	
	/**
	 * Create a message to ask for food/energy.
	 * 
	 * @return A food request message.
	 */
	public final MessageContent createFoodRequestMessage()
	{
		return new RequireFoodMessage(parent.info());
	}
	
	/**
	 * Create a message to indicate a ressource on the environnement.
	 * 
	 * @param position  The position of the item.
	 * @param item  The type of item.
	 * 
	 * @return A ressource indication message.
	 */
	public final MessageContent createLocationMessage(Rectangle position, String item)
	{
		return new LocationMessage(parent.info(),position,item);
	}
	
	/**
	 * Copulate with an other agent, once he is agree to do.
	 * 
	 * @param reply  The reply message to the demand of copulation.
	 * 
	 * @return True if an agent has been created, else false.
	 */
	public final boolean copulate(MessageContent reply)
	{
		if (!(reply instanceof Reply) || !parent.isMessage(reply))
			return false;
			
		return parent.copulate(reply.sender);		
	}
	
	/**
	 * Suicide command.
	 */
	public final void die()
	{
		parent.die();
	}
	
	
	/**
	 * Get the last movement vector of the agent.
	 * 
	 * @return The last movement vector.
	 */
	public final Vector2D direction()
	{
		return parent.direction();
	}
	
	/**
	 * Get the distance to an other agent.
	 * 
	 * @param other  Info on the other agent.
	 * 
	 * @return The distance to an other agent.
	 */
	public final double distance(AgentInfo other)
	{
		return distance(parent.hitbox(),other.hitbox);
	}
	
	/**
	 * Get the distace between two hitbox.
	 * 
	 * @param a  The first hitbox.
	 * @param b  The other hitbox.
	 * 
	 * @return The distance between the two hitbox.
	 */
	public final double distance(Rectangle a, Rectangle b)
	{
		int x1 = a.x + a.width/2,
			y1 = a.y + a.height/2,
			x2 = b.x + b.width/2,
			y2 = b.y + a.height/2;
			
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	/**
	 * Eat a food.
	 * 
	 * @param quantity  The desired quantity of food to eat.
	 */
	public final void eat(int quantity)
	{
		parent.eat(quantity);
	}
	
	/**
	 * Give ressource to an other agent. The agent must be near you to do
	 * so, distance < 10.
	 * 
	 * @param ai  Info on the reciever.
	 * @param item  The type of item to give.
	 * @param quantity  The quantity of item.
	 * 
	 * @return True if you gave something, else false.
	 */
	public final boolean give(AgentInfo ai, String item, int quantity)
	{
		// Don't give to self.
		if (ai.id == parent.id())
			return false;
		
		// Give only if the agent was near you recently.
		if (((System.currentTimeMillis() - ai.timestamp) < 50) &&
			(distance(ai) < 10))
		{
			parent.give(ai, item, quantity);
			return true;
		}
		return false;
	}
	
	/**
	 * Get the list of object you collide with.
	 * 
	 * @return The list of object who collide with the entity.
	 */
	public final List<FixedObject> getCollision()
	{
		return parent.getCollision(parent.hitbox());
	}
	
	/**
	 * Get the quantity of the item in your inventory.
	 * 
	 * @param item  The item type.
	 * 
	 * @return The quantity of item.
	 */
	public final int getQuantity(String item)
	{
		return parent.inventory().getCapacity(item);
	}
	
	/**
	 * Get the list of messages recieved.
	 * 
	 * @return The list of messages recieved.
	 */
	public final List<MessageContent> getMessages()
	{
		return parent.getMessages();
	}
	
	/**
	 * Get the list of info on agent in your vision range.
	 * 
	 * @return The list of info on agent in your vision range.
	 */
	public final List<AgentInfo> getVisibleAgents()
	{
		return parent.getVisibleAgents();
	}
	
	/**
	 * Get the list of object in your vision range.
	 * 
	 * @return The list of object in your vision range.
	 */
	public final List<FixedObject> getVisibleObjects()
	{
		return parent.getVisibleObjects();
	}
	
	/**
	 * Test if you have unread message.
	 * 
	 * @return True if you have new message, else false.
	 */
	public final boolean haveMessage()
	{
		return parent.haveMessage();
	}
	
	/**
	 * Harvest a ressource.
	 * 
	 * @param item The type of ressource to harvest.
	 * 
	 * @return The quantity of ressource harvested.
	 */
	public final int harvest(String item)
	{
		return parent.harvest(item);
	}
	
	/**
	 * Get the list of harvestable ressource.
	 * 
	 * @return A set of all the harvestable ressource.
	 */
	public final Set<String> harvestable()
	{
		return parent.harvestable();
	}
	
	/**
	 * Get the hitbox of the agent.
	 * 
	 * @return The hitbox of the agent.
	 */
	public final Rectangle hitbox()
	{
		return parent.hitbox();
	}
	
	/**
	 * Get the level of hungriness.
	 * 
	 * @return The level of hungriness.
	 */
	public final double hunger()
	{
		return parent.hunger();
	}
	
	/**
	 * Test if the agent is a teammate.
	 * 
	 * @param ai  Info on the agent.
	 * 
	 * @return True if the agent is a teammate, else false.
	 */
	public final boolean isTeamMate(AgentInfo ai)
	{
		return ai.equals(parent.team());
	}
	
	/**
	 * Move the agent by a vector.
	 * 
	 * <p>
	 * The vector norm will be in [0,1].
	 * </p>
	 * 
	 * @param direction The movement vector, not null.
	 * 
	 * @return The effective movement vector. 
	 */
	public final Vector2D moveBy(Vector2D direction)
	{
		return parent.moveBy(direction);
	}
	
	/**
	 * Send a reply to a message.
	 * 
	 * @param origin  The message to reply.
	 * @param value  Weither you reply positively or negatively.
	 */
	public final void replyTo(MessageContent origin, boolean value)
	{
		if (parent.isMessage(origin) && !(origin instanceof Reply))
		{
			parent.sendMessage(new Reply(parent.info(),value,origin),origin.sender);
		}
	}
	
	/**
	 * Send a message to an other agent.
	 * 
	 * @param message  The message.
	 * @param ai  The agent.
	 */
	public final void sendMessage(MessageContent message, AgentInfo ai)
	{
		if (message instanceof Reply)
			return;
		parent.sendMessage(message,ai);
	}
	
	/**
	 * What the brain plan to do at each step.
	 * 
	 * Has to be overriden by subclasses.
	 */
	public abstract void update();

}
