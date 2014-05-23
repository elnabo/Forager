import agents.ApplicationViewer;
import agents.ApplicationScheduler;
import agents.MobileAgent;
import brain.DummyBrain;
import model.Environnement;
import model.object.ressource.Food;
import ui.EnvironnementUI;

import madkit.action.KernelAction;
import madkit.kernel.Agent;
import madkit.kernel.Madkit;
import madkit.kernel.Madkit.Option;

import java.awt.Rectangle;

public class Main
{

	
	public static void main(String[] args)
	{
		Madkit kernel = new Madkit("--noAgentConsoleLog --desktopFrameClass");
		ApplicationViewer viewer = new ApplicationViewer();
		kernel.doAction(KernelAction.LAUNCH_AGENT, new ApplicationScheduler());
		kernel.doAction(KernelAction.LAUNCH_AGENT, viewer);
		try
		{
			while (viewer.size.width == 0 && viewer.size.height == 0)
			{
				Thread.sleep(500);
			}
		}
		catch (Exception e)	{}
		Environnement environnement = new Environnement(viewer.size);
		int a = 0;
		for(int i=0; i<= viewer.size.width; i+=5)
		{
			environnement.add(new Food(environnement, new Rectangle(i,0,4,4)));
			environnement.add(new Food(environnement, new Rectangle(i,viewer.size.height-4,4,4)));
			a+=2;
		}
		for(int i=5; i<= viewer.size.height-5; i+=5)
		{
			environnement.add(new Food(environnement, new Rectangle(0,i,4,4)));
			environnement.add(new Food(environnement, new Rectangle(viewer.size.width-4,i,4,4)));
			a+=2;
		}
		EnvironnementUI envUI = new EnvironnementUI(environnement);
		viewer.init(environnement, envUI);
		
		kernel.doAction(KernelAction.LAUNCH_AGENT, new MobileAgent(environnement, new DummyBrain()));
	}
}
