package agents;

import agents.message.MessageContent;
import agents.message.RessourceMessage;
import brain.Brain;
import model.Environnement;
import model.FixedObject;
import model.Inventory;
import model.object.Harvestable;
import util.Vector2D;

import madkit.kernel.AbstractAgent;
import madkit.kernel.Agent;
import madkit.kernel.Message;
import madkit.message.ObjectMessage;


import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayDeque;

public final class MaDKitAgent extends AbstractAgent implements AgentEntity
{
	private static final long serialVersionUID = -68366833257439L;
	private static int count = 0;
	
	private final Color col = Color.BLUE;
	
	protected double maxSteeringChange = 0.5;
	protected Vector2D direction;
	protected final int maxSpeed = 1;
	protected Inventory inventory;// = new Inventory(75);
	protected Rectangle hitbox;
	protected Environnement environnement;
	protected Brain brain;
	
	protected int visionRange = 50;
	protected int hunger = 0;
	protected int maxHunger = 100;
	
	private int id;
	private String team;
	private ArrayDeque<MessageContent> messageBox = new ArrayDeque<MessageContent>();
	
	public MaDKitAgent(Environnement environnement, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(20,20,5,5);
		id = count++;
		team = "";
		this.brain = brain;
		//~ this.brain = brain.init(this);
	}
	
	public MaDKitAgent(Environnement environnement, Point pos, Brain brain)
	{
		this.environnement = environnement;
		hitbox = new Rectangle(pos.x, pos.y,5,5);
		id = count++;
		team = "";
		this.brain = brain;
		//~ this.brain = brain.init(this);
	}
	
	@Override
	protected void activate()
	{
		requestRole(defaultCommunity, defaultGroup, defaultRole);
		requestRole(defaultCommunity, defaultGroup, id + "");
		requestRole(defaultCommunity, team, id + "");
		inventory = new Inventory(100);
		inventory.add("food",50);
		brain.init(this);
		environnement.add(this);
	}
	
	@Override
	public int add(String item, int weight)
	{
		return inventory.add(item,weight);
	}
	
	private boolean collide(AgentEntity ae, Rectangle b)
	{
		Rectangle a = ae.hitbox();
		return a.x + a.width > b.x &&
				a.y + a.height > b.y &&
				a.x < b.x + b.width &&
				a.y < b.y + b.height;
	}
	
	public final Color color()
	{
		return col;
	}
	
	@Override
	public final Vector2D direction()
	{
		return new Vector2D(direction);
	}
	
	@Override
	public void die()
	{
		environnement.remove(this);
		killAgent(this);
	}
	
	@Override
	public int drop(String item, int quantity)
	{
		Entry<String, Integer> toDrop = inventory.remove(item, quantity);
		environnement.createRessource(toDrop, new Point(hitbox.x, hitbox.y));
		return toDrop.getValue();
	}
	
	@Override
	public void eat(int quantity)
	{
		hunger = Math.max(0,hunger - inventory.remove("food",quantity).getValue());
	}
	
	@Override
	public void give(AgentInfo ai, String item, int quantity)
	{
		sendMessage(defaultCommunity,defaultGroup,ai.id + "",
				new ObjectMessage<RessourceMessage>(new RessourceMessage(
				item, inventory.remove(item,quantity).getValue(), info()))
				);
	}

	@Override
	public final List<FixedObject> getCollision(Rectangle bounds)
	{
		return environnement.collide(bounds);
	}
	
	@Override
	public List<FixedObject> getCollisionAfterMovement(Vector2D mvment)
	{
		Rectangle box = new Rectangle(hitbox);
		box.x += (int)Math.round(mvment.x);
		box.y += (int)Math.round(mvment.y);
		return getCollision(box);
	}
	
	@Override
	public List<MessageContent> getMessages()
	{
		List<MessageContent> messages = new ArrayList<MessageContent>();
		while (!messageBox.isEmpty())
		{
			messages.add(messageBox.poll());
		}
		return messages;
	}
	
	@Override
	public List<AgentInfo> getVisibleAgents()
	{
		List<AgentInfo> agents = new ArrayList<AgentInfo>();
		Rectangle visionBox = new Rectangle(hitbox.x - visionRange,
			hitbox.y - visionRange,	hitbox.width + 2 * visionRange,
			hitbox.height + 2 * visionRange);
		for (AgentEntity a : environnement.agents)
		{
			if (collide(a, visionBox))
				agents.add(a.info());
		}
		return agents;
	}
	
	@Override
	public List<FixedObject> getVisibleObjects()
	{
		return environnement.collide(new Rectangle(hitbox.x - visionRange,
			hitbox.y - visionRange,	hitbox.width + 2 * visionRange,
			hitbox.height + 2 * visionRange));
	}
	
	private void handleMessage()
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
	
	/**
	 * Harvest the ressource who collide with the agent.
	 * 
	 * @return The quantity harvested.
	 */
	@Override
	public final int harvest() 
	{
		int harvested = 0;
		List<FixedObject> possibleCollisions = environnement.collide(new Rectangle(hitbox.x-1, hitbox.y-1, hitbox.width+2, hitbox.height+2));
		for (FixedObject i : possibleCollisions)
		{
			if (i.harvestable())
			{
				harvested += ((Harvestable)i).harvest(this);
			}
		}
		//~ give(new AgentInfo(new Rectangle(0,0),"",1), "food",harvested);
		return harvested;
	}
	
	@Override
	public final Rectangle hitbox()
	{
		return new Rectangle(hitbox);
	}
	
	/**
	 * 0 = no need to eat.
	 * 1 = dead of hunger.
	 */
	@Override
	public double hunger()
	{
		return hunger / ((double)maxHunger);
	}
	
	@Override
	public void increaseHunger()
	{
		hunger++;
		if (hunger == maxHunger)
			die();
	}
	
	@Override
	public AgentInfo info()
	{
		return new AgentInfo(hitbox, team(), id);
	}
	
	@Override
	public final void moveBy(Vector2D mvment)
	{
		if (mvment.norm() > 1)
			mvment = mvment.unitVector();//.scalarMul(maxSpeed);
		if (mvment.norm() < 0)
			return;// new Vector2D(0,0);
			
		List<FixedObject> collision = getCollisionAfterMovement(mvment);
		// colliding
		if (collision != null && collision.size() > 0)
		{
			direction = new Vector2D(0,0);
		}
		else
		{
			direction = new Vector2D(mvment);
			hitbox.x += (int)Math.round(mvment.x);
			hitbox.y += (int)Math.round(mvment.y);
		}
	}
	
	@Override
	public String team()
	{
		return team;
	}
	
	@Override
	public void update() 
	{ 
		increaseHunger();
		handleMessage();
		brain.update();
	}
	
}
