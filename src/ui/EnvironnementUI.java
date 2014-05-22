package ui;

import model.Environnement;
import model.FixedObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EnvironnementUI
{
	protected Environnement environnement;
	
	public EnvironnementUI(Environnement env)
	{
			environnement = env;
	}
	
	public void drawOn(Graphics graphic, Dimension size)
	{
		float scaleX = ((float)size.width) / environnement.size.width,
				scaleY = ((float)size.height) / environnement.size.height;
		for (FixedObject obj : environnement.entities)
		{
			int sx = (int)Math.round(obj.bounds().getMinX() * scaleX),
				ex = (int)Math.round(obj.bounds().getMaxX() * scaleX),
				sy = (int)Math.round(obj.bounds().getMinY() * scaleY),
				ey = (int)Math.round(obj.bounds().getMaxY() * scaleY);
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
		
		graphic.setColor(Color.RED);
	}
}
