package ui;

import model.Environnement;
import model.FixedObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

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
		for (FixedObject obj : new ArrayList<FixedObject>(environnement.entities))
		{
			Rectangle rect = obj.bounds();
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
		
		//~ graphic.setColor(Color.RED);
	}
}
