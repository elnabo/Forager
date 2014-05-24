import agents.ApplicationViewer;
import agents.ApplicationScheduler;
import agents.MaDKitAgent;
import brain.DummyBrain;
import model.Environnement;
import model.object.obstacle.Wall;
import model.object.ressource.Food;
import ui.EnvironnementUI;

import madkit.action.KernelAction;
import madkit.kernel.Agent;
import madkit.kernel.Madkit;
import madkit.kernel.Madkit.Option;

import java.awt.Point;
import java.awt.Rectangle;

public class Main
{

	
	public static void main(String[] args)
	{
		Madkit kernel = new Madkit("--desktopFrameClass");
		//~ Madkit kernel = new Madkit("--noAgentConsoleLog --desktopFrameClass");
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
		
		for(int i=0; i<= viewer.size.width; i+=5)
		{
			environnement.add(new Wall(environnement, new Rectangle(i,0,5,5)));
			environnement.add(new Wall(environnement, new Rectangle(i,viewer.size.height-5,5,5)));
		}
		for(int i=5; i<= viewer.size.height-5; i+=5)
		{
			environnement.add(new Wall(environnement, new Rectangle(0,i,5	,5)));
			environnement.add(new Wall(environnement, new Rectangle(viewer.size.width-5,i,5,5)));
		}
		
		for(int i=50; i<=70;i+=5)
		{
			for(int j=50; j<=70; j+=5)
			{
				environnement.add(new Food(environnement, new Rectangle(i,j,5,5)));
			}
		}
		
		EnvironnementUI envUI = new EnvironnementUI(environnement);
		viewer.init(environnement, envUI);
		
		kernel.doAction(KernelAction.LAUNCH_AGENT, new MaDKitAgent(environnement, new DummyBrain()));
		kernel.doAction(KernelAction.LAUNCH_AGENT, new MaDKitAgent(environnement,new Point(7,7),new DummyBrain()));
	}
}
