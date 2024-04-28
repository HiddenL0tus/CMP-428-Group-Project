package entity;

import java.awt.*;
import engine.*;

public class WoodSword extends Item
{
	public WoodSword(int x, int y, int w, int h, String direction) 
	{
		super("Wood_Sword_", x, y, w, h);
		setWeapon();
		setMelee();
		
		image = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Sword_" + direction + ".png");
		worldImage = image;
	}
	
	public void draw(Graphics pen)
	{
		if (equipped) pen.drawImage(worldImage, x - Camera.x, y - Camera.y, w, h, null);
		
		else super.draw(pen);
	}
}
