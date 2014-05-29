package forager;

import forager.agents.MadkitAgent;
import forager.brain.Brain;
import forager.model.Environnement;
import forager.model.object.obstacle.Wall;
import forager.model.object.ressource.Food;
import forager.ui.EnvironnementUI;

import madkit.action.KernelAction;
import madkit.kernel.Madkit;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;

/**
 * Main class of the Forager game.
 * 
 * @author Guillaume Desquesnes.
 */
public class Main
{
	/** Madkit kernel. */
	private static Madkit kernel = null;
	
	/** The environnement. */
	private static Environnement environnement;
	
	/**
	 * Initialize the game.
	 * 
	 * @param size  The size of the environnement.
	 */
	public static void init(Dimension size)
	{
		environnement = new Environnement(size);
		
		// Add obstacles
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
		
		// Add some food.
		for(int i=50; i<=70;i+=5)
		{
			for(int j=50; j<=70; j+=5)
			{
				environnement.add(new Food(environnement, new Rectangle(i,j,5,5)));
			}
		}
		
		// Launch the UI
		EnvironnementUI envUI = new EnvironnementUI(environnement);
		SwingUtilities.invokeLater(envUI);
	}
	
	/**
	 * Launch a new MaDKit agent.
	 * 
	 * @param pos  The agent's position.
	 * @param brain  The agent's brain.
	 */
	public static void launchMadkitAgent(Point pos, Brain brain)
	{
		if (kernel == null)
			kernel = new Madkit("--noAgentConsoleLog true --madkitLogLevel OFF --desktopFrameClass null");
		
		kernel.doAction(KernelAction.LAUNCH_AGENT, new MadkitAgent(environnement,pos, brain));
	}
	
	public static void main(String[] args)
	{
		init(new Dimension(500,500));
		
		String brain = "forager.brain.DummyBrain";
		try
		{
			Class<?> cls = Class.forName(brain);
			launchMadkitAgent(new Point(20,20), (Brain)(cls.newInstance()));
			launchMadkitAgent(new Point(7,7), (Brain)(cls.newInstance()));
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
