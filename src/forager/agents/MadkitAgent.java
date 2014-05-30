package forager.agents;

import forager.agents.message.MessageContent;
import forager.agents.message.RessourceMessage;
import forager.brain.Brain;
import forager.model.Environnement;
import forager.model.FixedObject;
import forager.model.Inventory;
import forager.model.object.Harvestable;
import forager.util.Vector2D;

import madkit.kernel.AbstractAgent;
import madkit.kernel.AbstractAgent.ReturnCode;
import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.ObjectMessage;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * MaDKit implementation of the {@link forager.agents.AgentEntity AgentEntity} for the Forager game.
 * 
 * @author Guillaume Desquesnes
 */
public final class MadkitAgent extends Agent implements AgentEntity
{
	private static final long serialVersionUID = -68366833257439L;
	
	/** Default community in the AGR model. */
	static final String defaultCommunity = "global";
	/** Default group in the AGR model. */
	static final String defaultGroup = "global";
	/** Default role in the AGR model. */
	static final String defaultRole = "mobileAgent";

	/** Environnement of the agent. */
	protected Environnement environnement;
	
	/** Brain of the agent. */
	protected Brain brain;
	
	/** Color of the agent. */
	protected final Color col = Color.BLUE;
	
	/** Last movement vector applied. */
	protected Vector2D direction = new Vector2D(0,0);
	
	/** Agent's inventory. */
	protected Inventory inventory;
	
	/** Agent's hitbox. */
	protected Rectangle hitbox;	
	
	/** Maximum vision distance. */
	protected int visionRange = 250;
	
	/** Current hunger. */
	protected int hunger = 0;
	
	/** Maximum of hunger before dying. */
	protected int maxHunger = 100;
	
	/** Id of the agent. */
	private final int id;
	
	/** The team of the agent*/
	private final String team;
	
	/** All message received since the last checkout. */
	private ArrayDeque<MessageContent> messageBox = new ArrayDeque<MessageContent>();
	
	/** 
	 * Same as message box, not cleared on message box request,
	 * used to verify that message aren't forged by the brain.
	 */
	private ArrayList<MessageContent> messageVerification = new ArrayList<MessageContent>();
	
	
	/**
	 * Create a new MaDKit agent at the (20,20) position.
	 * 
	 * @param environnement  The environnement he belongs on.
	 * @param brain  The brain of the agent.
	 */
	public MadkitAgent(Environnement environnement, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(20,20,width, height);
		id = Environnement.getCount();
		team = "";
		inventory = new Inventory(100);
		inventory.add(foodType,childCost/2);
		this.brain = brain.init(this);
		environnement.add(this);
	}
	
	/**
	 * Create a new MaDKit agent.
	 * 
	 * @param environnement  The environnement he belongs on.
	 * @param pos  The position of the top left corner of the agent.
	 * @param brain  The brain of the agent.
	 */
	public MadkitAgent(Environnement environnement, Point pos, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(pos.x, pos.y,width, height);
		id = Environnement.getCount();
		team = "";
		inventory = new Inventory(100);
		inventory.add(foodType,childCost/2);
		this.brain = brain.init(this);
		environnement.add(this);
	}
	
	/**
	 * First behavior of the agent.
	 * 
	 * Request the default AGR community.
	 * Take the default role and his id role in the default group.
	 * Take the id role in his team group.
	 */
	@Override
	protected void activate()
	{
		createGroup(defaultCommunity,defaultGroup);
		createGroup(defaultCommunity,team);
		requestRole(defaultCommunity, defaultGroup, defaultRole);
		requestRole(defaultCommunity, defaultGroup, id + "");
		requestRole(defaultCommunity, team, id + "");
	}
	
	@Override
	/** {@inheritDoc} */
	public int add(String item, int weight)
	{
		return inventory.add(item,weight);
	}
	
	@Override
	/** {@inheritDoc} */
	public void broadcast(MessageContent message)
	{
		for (AgentInfo ai : getVisibleAgents())
		{
			sendMessage(message,ai);
		}
	}
	
	@Override
	/** {@inheritDoc} */
	public boolean canHarvest()
	{
		for (FixedObject o : environnement.collide(new Rectangle(hitbox.x-1, hitbox.y-1, hitbox.width+2, hitbox.height+2)))
		{
			if (o.harvestable())
				return true;
		}
		return false;
	}
	
	@Override
	/** {@inheritDoc} */
	public boolean collide(AgentEntity ae, Rectangle box)
	{
		Rectangle a = ae.hitbox();
		return a.x + a.width > box.x &&
				a.y + a.height > box.y &&
				a.x < box.x + box.width &&
				a.y < box.y + box.height;
	}
	
	@Override
	/** {@inheritDoc} */
	public final Color color()
	{
		return col;
	}
	
	@Override
	/** {@inheritDoc} */
	public boolean copulate(AgentInfo other)
	{
		// You can't copulate with an other team.
		if (!other.team.equals(team))
			return false;
			
		AgentEntity otherEntity = getAgent(other);
		// You can't copulate with someone who doesn't exist.
		if (otherEntity == null)
			return false;
			
		Inventory otherInventory = otherEntity.inventory();
		int halfChildCost = childCost / 2;
		
		// You can't copulate with some who doesn't have enough food.
		if (inventory.getCapacity(foodType) < halfChildCost &&
			otherInventory.getCapacity(foodType) < halfChildCost)
			return false;
		
		Point futurPos = new Point((hitbox.x + otherEntity.hitbox().x)/2,
			(hitbox.y + otherEntity.hitbox().y)/2);
		if (createAgent(futurPos))
		{
			inventory.remove(foodType,halfChildCost);
			otherInventory.remove(foodType,halfChildCost);
			return true;
		}
		return false;
	}
	
	@Override
	/** {@inheritDoc} */
	public boolean createAgent(Point pos)
	{
		try
		{
			 ReturnCode rc = launchAgent(new MadkitAgent(environnement, pos, brain.getClass().newInstance()));
			 return rc == ReturnCode.SUCCESS;

		}
		catch (InstantiationException | IllegalAccessException e)
		{
			return false;
		}
	}
	
	@Override
	/** {@inheritDoc} */
	public final Vector2D direction()
	{
		return new Vector2D(direction);
	}
	
	@Override
	/** {@inheritDoc} */
	public void die()
	{
		environnement.remove(this);
		killAgent(this);
	}
	
	@Override
	/** {@inheritDoc} */
	public int drop(String item, int quantity)
	{
		/*Entry<String, Integer> toDrop = inventory.remove(item, quantity);
		environnement.createRessource(toDrop, new Point(hitbox.x, hitbox.y));
		return toDrop.getValue();*/
		return 0;
	}
	
	@Override
	/** {@inheritDoc} */
	public void eat(int quantity)
	{
		hunger = Math.max(0,hunger - inventory.remove(foodType,quantity));
	}
	
	@Override
	/** {@inheritDoc} */
	public AgentEntity getAgent(AgentInfo ai)
	{
		if (ai == null)
			return null;
		return ai.agent;
	}

	@Override
	/** {@inheritDoc} */
	public final List<FixedObject> getCollision(Rectangle bounds)
	{
		return environnement.collide(bounds);
	}
	
	@Override
	/** {@inheritDoc} */
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		Rectangle box = new Rectangle(hitbox);
		box.x += (int)Math.round(mvment.x);
		box.y += (int)Math.round(mvment.y);
		return getCollision(box);
	}
	
	@Override
	/** {@inheritDoc} */
	public List<MessageContent> getMessages()
	{
		List<MessageContent> messages = new ArrayList<MessageContent>();
		messageVerification.clear();
		while (!messageBox.isEmpty())
		{
			messages.add(messageBox.poll());
		}
		messageVerification.addAll(messages);
		return messages;
	}
	
	@Override
	/** {@inheritDoc} */
	public List<AgentInfo> getVisibleAgents()
	{
		List<AgentInfo> agents = new ArrayList<AgentInfo>();
		Rectangle visionBox = new Rectangle(hitbox.x - visionRange,
			hitbox.y - visionRange,	hitbox.width + 2 * visionRange,
			hitbox.height + 2 * visionRange);
			
		// Test if an agent is within the vision range.
		for (AgentEntity a : environnement.agents)
		{
			if (collide(a, visionBox) && a!=this)
				agents.add(a.info());
		}
		return agents;
	}
	
	@Override
	/** {@inheritDoc} */
	public List<FixedObject> getVisibleObjects()
	{
		return environnement.collide(new Rectangle(hitbox.x - visionRange,
			hitbox.y - visionRange,	hitbox.width + 2 * visionRange,
			hitbox.height + 2 * visionRange));
	}
	
	@Override
	/** {@inheritDoc} */
	public void give(AgentInfo ai, String item, int quantity)
	{
		if (getAgent(ai) == this)
			return;
			
		sendMessage(new RessourceMessage(info(),
					item, inventory.remove(item,quantity)),
					ai);
	}
	
	@Override
	/** {@inheritDoc} */
	public void handleMessage()
	{
		while (!isMessageBoxEmpty())
		{
			Message m = nextMessage();
			if (m instanceof ObjectMessage)
			{
				Object o = ((ObjectMessage)m).getContent();
				if (o instanceof MessageContent)
				{
					messageBox.add((MessageContent)(o));
					/*
					 * If the message is ressource, add them
					 * to the stock. Send back the remaining
					 * ressources.
					 */
					if (o instanceof RessourceMessage)
					{
						RessourceMessage rm = (RessourceMessage)o;
						int added = add(rm.item, rm.quantity);
						if (!rm.reply && added != rm.quantity)
							give(rm.sender, rm.item, rm.quantity - added);
					}
				}
			}
		}
	}
	
	@Override
	/** {@inheritDoc} */
	public final int harvest(String type) 
	{
		int harvested = 0;
		List<FixedObject> possibleCollisions = environnement.collide(new Rectangle(hitbox.x-1, hitbox.y-1, hitbox.width+2, hitbox.height+2));
		for (FixedObject i : possibleCollisions)
		{
			if (i.harvestable() && i.type().equals(type))
			{
				harvested += ((Harvestable)i).harvest(this,type);
			}
		}
		return harvested;
	}
	
	@Override
	/** {@inheritDoc} */
	public final Set<String> harvestable() 
	{
		HashSet<String> types = new HashSet<String>();
		List<FixedObject> possibleCollisions = environnement.collide(new Rectangle(hitbox.x-1, hitbox.y-1, hitbox.width+2, hitbox.height+2));
		for (FixedObject i : possibleCollisions)
		{
			if (i.harvestable())
			{
				types.add(i.type());
			}
		}
		return types;
	}
	
	@Override
	/** {@inheritDoc} */
	public boolean haveMessage()
	{
		return !messageBox.isEmpty();
	}
	
	@Override
	/** {@inheritDoc} */
	public final Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}

	@Override
	/** {@inheritDoc} */
	public double hunger()
	{
		return hunger / ((double)maxHunger);
	}
	
	@Override
	/** {@inheritDoc} */
	public void increaseHunger()
	{
		hunger++;
		if (hunger == maxHunger)
			die();
	}
	
	@Override
	/** {@inheritDoc} */
	public int id()
	{
		return id;
	}
	
	@Override
	/** {@inheritDoc} */
	public AgentInfo info()
	{
		return new AgentInfo(this);
	}
	
	@Override
	/** {@inheritDoc} */
	public Inventory inventory()
	{
		return inventory;
	}
	
	@Override
	/** {@inheritDoc} */
	public boolean isMessage(MessageContent message)
	{
		if (message == null)
			return false;
		return messageVerification.contains(message);
	}
	
	/**
	 * Main behavior of the agent.
	 * 
	 * Call {@link #update() update} every 6 ms.
	 */
	@Override
	protected void live()
	{
		while (true)
		{
			update();
			pause(6);
		}
	}
	
	@Override
	/** {@inheritDoc} */
	public final Vector2D moveBy(Vector2D mvment)
	{
		if (mvment.norm() > 1)
			mvment = mvment.unitVector();
		if (mvment.norm() <= 0.001)
		{
			direction = new Vector2D(0,0);
			return new Vector2D(0,0);
		}
			
		double i =0.0;
		/*
		 * Test if the agent will collide with 
		 * something during the movement.
		 */
		do
		{
			List<FixedObject> collision = getCollisionAfterMovement(mvment.scalarMul(i));
			if (collision != null && collision.size() > 0)
			{
				break;
			}
			i+=0.1;
		} while (i<=1.0);
		
		// Get the new movement vector.
		direction =  mvment.scalarMul(i-0.1);
		
		// Apply the movement.
		hitbox.x += (int)Math.round(direction.x);
		hitbox.y += (int)Math.round(direction.y);
		return new Vector2D(direction);
	}
	
	@Override
	/** {@inheritDoc} */
	public void recieveMessageFromOtherType(MessageContent message)
	{
		messageBox.add(message);
	}
	
	@Override
	/** {@inheritDoc} */
	public void sendMessage(MessageContent message, AgentInfo ai)
	{
		AgentEntity other = getAgent(ai);
		if (other == this)
			return;
			
		if (other instanceof MadkitAgent)
			sendMessage(defaultCommunity,defaultGroup, ai.id+"",
				new ObjectMessage<MessageContent>(message));
		else
			other.recieveMessageFromOtherType(message);
	}
	
	@Override
	/** {@inheritDoc} */
	public String team()
	{
		return team;
	}
	
	@Override
	/**
	 * Update the agent.
	 * 
	 * Handle the message, update the brain then increase the hunger.
	 */
	public void update() 
	{ 
		handleMessage();
		brain.update();
		increaseHunger();
	}
	
}
