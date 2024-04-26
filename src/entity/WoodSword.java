package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import engine.Camera;
import engine.Rect;

public class WoodSword extends Rect
{
	Image img;
	public boolean isVisible  = true;

	public WoodSword(int x, int y, int w, int h, String direction) {
		super(x, y, w, h);
		img = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Sword_" + direction + ".png"); 
	}
	
	public void updatePositionRelativeTo(int entityX, int entityY, String direction)
	{
		isVisible = true;
		
		     if (direction.equals("UP")) setXYWH(entityX, entityY - h, 12, 43);
		else if (direction.equals("DN")) setXYWH(entityX, entityY + h, 12, 43);
		else if (direction.equals("LT")) setXYWH(entityX - w, entityY, 43, 12);
		else if (direction.equals("RT")) setXYWH(entityX + w, entityY, 43, 12);
		
		img = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Sword_" + direction + ".png");
	}
	
	public void draw(Graphics pen)
	{
		pen.drawImage(img, x - Camera.x, y - Camera.y, w, h, null);
		super.draw(pen); //draws underlying rect
	}
}
