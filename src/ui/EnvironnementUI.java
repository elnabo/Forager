package ui;

import agents.AgentEntity;
import model.Environnement;
import model.FixedObject;

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

public class EnvironnementUI extends JPanel implements Runnable, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	protected Environnement environnement;
	protected Dimension size = new Dimension(500,500);
	
    private final Timer timer = new Timer(20, this);
	
	public EnvironnementUI(Environnement env)
	{
		super(true);
		environnement = env;
	}
	
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
	
	@Override
    protected void paintComponent(Graphics graphic)
	{
		super.paintComponent(graphic);
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0,0,size.width,size.height);
		
		float scaleX = ((float)size.width) / environnement.size.width,
				scaleY = ((float)size.height) / environnement.size.height;
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
				case "food":
					graphic.setColor(Color.GREEN);
					break;
				case "breakble":
					graphic.setColor(Color.LIGHT_GRAY);
					break;
				default:
					graphic.setColor(Color.BLACK);
			}
			graphic.fillPolygon(new int[]{sx,ex,ex,sx},new int[]{sy,sy,ey,ey},4);
		}
		for(final AgentEntity ae : new ArrayList<AgentEntity>(environnement.agents))
		{
			graphic.setColor(ae.color());
			Rectangle hitbox = ae.hitbox();
			int sx = hitbox.x,
				ex = hitbox.x + hitbox.width,
				sy = hitbox.y,
				ey = hitbox.y + hitbox.height;
			graphic.fillPolygon(new int[]{sx,ex,ex,sx}, new int[]{sy,sy,ey,ey},4);
		}
	}
	
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.repaint();
    }
}
