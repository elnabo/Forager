package agents;

import model.Environnement;
import ui.EnvironnementUI;

import madkit.simulation.probe.PropertyProbe;
import madkit.simulation.viewer.SwingViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ApplicationViewer extends SwingViewer
{
	private static final long serialVersionUID = -6999130646300839708L;
	
	private Environnement environnement;
	private EnvironnementUI ui;
	private Dimension frameSize = new Dimension(500,500);
	public Dimension size = new Dimension(0,0);
	private PropertyProbe<MaDKitAgent,Rectangle> mobileProbe;
	
	@Override
	protected void activate()
	{
		requestRole("global","global","viewer");
		mobileProbe = new PropertyProbe<MaDKitAgent, Rectangle>("global","global","mobileAgent","hitbox")
		{
			@Override
			public void adding(MaDKitAgent agent) 
			{
				super.adding(agent);
			}
		};
		addProbe(mobileProbe);
		setSynchronousPainting(true);
	}
	
	public void init(Environnement environnement, EnvironnementUI ui)
	{
		this.environnement = environnement;
		this.ui = ui;
	}
	
	@Override
	protected void render(Graphics g)
	{	
		if (ui != null)
		{
			ui.drawOn(g,size);
		}
	}
	
	@Override
	public void setupFrame(JFrame frame)
	{
		super.setupFrame(frame);
		
		JPanel display = new JPanel()
		{
			private static final long	serialVersionUID	= -6999130646300839608L;
			@Override
			protected void paintComponent(Graphics g) 
			{
				super.paintComponent(g);
				render(g);
			}
		};
		setDisplayPane(display);
		display.setBackground(Color.WHITE);
		frame.add(display);
		
		
		display.addComponentListener(new ComponentAdapter() 
		{
			@Override
			public void componentResized(ComponentEvent e) 
			{
				size = e.getComponent().getSize();
			}
		});
		
		frame.setSize(frameSize);
		frame.setResizable(false);
		
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
