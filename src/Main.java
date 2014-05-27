import agents.MaDKitAgent;
import brain.Brain;
import model.Environnement;
import model.object.obstacle.Wall;
import model.object.ressource.Food;
import ui.EnvironnementUI;

import madkit.action.KernelAction;
import madkit.kernel.Madkit;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;

public class Main
{
	private static Madkit kernel = null;
	private static Environnement environnement;
	
	public static void init(Dimension size)
	{
		environnement = new Environnement(size);
		for(int i=0; i<= size.width; i+=5)
		{
			environnement.add(new Wall(environnement, new Rectangle(i,0,5,5)));
			environnement.add(new Wall(environnement, new Rectangle(i,size.height-5,5,5)));
		}
		for(int i=5; i<= size.height-5; i+=5)
		{
			environnement.add(new Wall(environnement, new Rectangle(0,i,5	,5)));
			environnement.add(new Wall(environnement, new Rectangle(size.width-5,i,5,5)));
		}
		
		for(int i=50; i<=70;i+=5)
		{
			for(int j=50; j<=70; j+=5)
			{
				environnement.add(new Food(environnement, new Rectangle(i,j,5,5)));
			}
		}
		
		EnvironnementUI envUI = new EnvironnementUI(environnement);
		SwingUtilities.invokeLater(envUI);
	}
	
	public static void launchMaDKitAgent(Point pos, Brain brain)
	{
		if (kernel == null)
			kernel = new Madkit("--noAgentConsoleLog true --madkitLogLevel OFF --desktopFrameClass null");
		
		kernel.doAction(KernelAction.LAUNCH_AGENT, new MaDKitAgent(environnement,pos, brain));
	}
	
	public static void main(String[] args)
	{
		init(new Dimension(500,500));
		
		String brain = "brain.DummyBrain";
		try
		{
			Class<?> cls = Class.forName(brain);
			launchMaDKitAgent(new Point(20,20), (Brain)(cls.newInstance()));
			launchMaDKitAgent(new Point(7,7), (Brain)(cls.newInstance()));
		}
		catch (InstantiationException e)
		{
			System.err.println(brain + " is not a brain");
			System.exit(-1);
		}
		catch (IllegalAccessException e)
		{
			System.err.println(brain + " is not accessible");
			System.exit(-1);
		}
		catch (LinkageError | ClassNotFoundException e)
		{
			System.err.println(brain + " not instanciable");
			System.exit(-1);
		}
	}
}
