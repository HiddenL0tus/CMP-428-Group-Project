package entity;

import java.awt.*;
import engine.*;

public class WoodSword extends Weapon
{
	public WoodSword(int x, int y) 
	{
		super("Wood_Sword", x, y, 12, 43);
		setMelee();
		
		image      = Toolkit.getDefaultToolkit().getImage("weapons/Wood_Sword_UP.png");
		worldImage = image;
	}
}