package entity;

import java.awt.*;
import engine.*;

public class WoodSword extends Item
{
	public boolean isVisible  = true;

	public WoodSword(int x, int y, int w, int h, String direction) 
	{
		super("Wood_Sword", x, y, w, h);
		
		image = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Sword_" + direction + ".png");
	}
	
	public void updatePositionRelativeTo(Entity e, String direction)
	{
		isVisible = true;
		
		     if (direction.equals("UP")) setXYWH(e.x + e.halfW, e.y - h + 10, 12, 43);
		else if (direction.equals("DN")) setXYWH(e.x + e.halfW, e.y + h - 10, 12, 43);
		else if (direction.equals("LT")) setXYWH(e.x - w + 10, e.y + e.halfH, 43, 12);
		else if (direction.equals("RT")) setXYWH(e.x + w - 10, e.y + e.halfH, 43, 12);
		
		image = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Sword_" + direction + ".png");
	}
	
	public void draw(Graphics pen)
	{
		if (equipped) pen.drawImage(image, x - Camera.x, y - Camera.y, w, h, null);
		
		else super.draw(pen);
	}
}
