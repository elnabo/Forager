package forager.ui;

import forager.agents.AgentEntity;
import forager.model.Environnement;
import forager.model.FixedObject;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The Forager game user interface.
 * 
 * @author Guillaume Desquesnes
 */
public class EnvironnementUI extends JPanel implements Runnable, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	/** The environnement to display. */
	protected Environnement environnement;
	
	/** The size of the panel. */
	protected Dimension size = new Dimension(500,500);
	
	/** A timer to trigger repaint. */
    private final Timer timer = new Timer(5, this);
	
	/**
	 * Create a new ui for one environnement.
	 * 
	 * @param env  The environnement.
	 */
	public EnvironnementUI(Environnement env)
	{
		super(true);
		environnement = env;
	}
	
	/**
	 * Called once to create a new windows.
	 */
	@Override
	public void run()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setSize(size);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		size = getSize();
		timer.start();
	}
	
	/**
	 * Draw everything on the panel.
	 * 
	 * @param graphic  The graphic.
	 */
	@Override
    protected void paintComponent(Graphics graphic)
	{
		super.paintComponent(graphic);
		// Clean 
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0,0,size.width,size.height);
		
		// Scale the items to draw to fit the screen.
		float scaleX = ((float)size.width) / environnement.size.width,
				scaleY = ((float)size.height) / environnement.size.height;
				
		// Draw all objects.
		for (final FixedObject obj : new ArrayList<FixedObject>(environnement.entities))
		{
			if (obj == null)
				continue;
			Rectangle rect = obj.hitbox();
			int sx = Math.round(rect.x * scaleX),
				ex = Math.round((rect.x + rect.width) * scaleX),
				sy = Math.round(rect.y * scaleY),
				ey = Math.round((rect.y + rect.height) * scaleY);
			switch (obj.type())
			{
				case "Food":
					graphic.setColor(Color.GREEN);
					break;
				case "Breakable":
					graphic.setColor(Color.LIGHT_GRAY);
					break;
				default:
					graphic.setColor(Color.BLACK);
			}
			graphic.fillPolygon(new int[]{sx,ex,ex,sx},new int[]{sy,sy,ey,ey},4);
		}
		
		// Draw all agents.
		for(final AgentEntity ae : new ArrayList<AgentEntity>(environnement.agents))
		{
			graphic.setColor(ae.color());
			Rectangle hitbox = ae.hitbox();
			int sx = Math.round(hitbox.x*scaleX),
				ex = Math.round((hitbox.x + hitbox.width)*scaleX),
				sy = Math.round(hitbox.y*scaleY),
				ey = Math.round((hitbox.y + hitbox.height)*scaleY);
			graphic.fillPolygon(new int[]{sx,ex,ex,sx}, new int[]{sy,sy,ey,ey},4);
		}
	}
	
    /**
     * Trigger repainting on trigger.
     * 
     * @param e  The action triggering.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.repaint();
    }
}
