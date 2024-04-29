package entity;

import java.awt.Graphics;
import java.awt.Toolkit;

import engine.Camera;
import engine.Sprite;

public class WoodBow extends Weapon
{
	public WoodBow(int x, int y) 
	{
		super("Wood_Bow", x, y, 14, 35);
		setRanged();
		
		image = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Bow_RT_0.png"); //inventory image
		worldImage = image;
	}
}
