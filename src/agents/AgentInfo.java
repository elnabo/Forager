package agents;

import java.awt.Rectangle;

public final class AgentInfo
{
	public final Rectangle hitbox;
	public final String team;
	public final int id;
	
	protected AgentInfo(Rectangle hitbox, String team, int id)
	{
		this.hitbox = new Rectangle(hitbox);
		this.team = team;
		this.id = id;
	}
}
