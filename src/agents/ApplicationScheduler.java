package agents;

import madkit.action.SchedulingAction;
import madkit.kernel.Message;
import madkit.kernel.Scheduler;
import madkit.message.ObjectMessage;
import madkit.message.SchedulingMessage;
import madkit.simulation.activator.GenericBehaviorActivator;

public class ApplicationScheduler extends Scheduler
{
	private static final long serialVersionUID = -6881984366833257439L;
	
	private GenericBehaviorActivator<ApplicationViewer> viewer;
	private GenericBehaviorActivator<MobileAgent> agents;
	/**
	 * First behavior of the scheduler.
	 * Add default behavior of every agents.
	 */
	public void activate()
	{
		super.activate();
		createGroup("global","global");
		requestRole("global","global","scheduler");
		
		viewer = new GenericBehaviorActivator<ApplicationViewer>("global","global","viewer","observe");
		addActivator(viewer);
		
		agents = new GenericBehaviorActivator<MobileAgent>("global","global","mobileAgent","update");
		addActivator(agents);
		setDelay(5);
		
		receiveMessage(new SchedulingMessage(SchedulingAction.RUN));
	}
}
