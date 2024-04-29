package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import engine.Animation;
import engine.Camera;
import engine.Sprite;

public class Weapon extends Item
{
	public Image worldImage;
	public boolean isVisible = true;
	
	private String folder = "weapons/";

	//constructor for non-animated weapons
	public Weapon(String name, int x, int y, int w, int h) {
		super(name, x, y, w, h);
		setWeapon();
	}
	
	public void setMelee() 
	{
		setSubType("melee");
	}
	
	public void setRanged() 
	{
		setSubType("ranged");
	}
	
	public boolean isMelee()
	{
		return getSubType().equals("melee");
	}
	
	public boolean isRanged()
	{
		return getSubType().equals("ranged");
	}
	
	/* Not for animated weapons */
	public void updatePositionRelativeTo(Entity e, String direction)
	{	
		     if (direction.equals("UP")) setPosition(e.x + e.halfW - this.halfW, e.y   - h + 10);
		else if (direction.equals("DN")) setPosition(e.x + e.halfW - this.halfW, e.y + e.h - 10);
		else if (direction.equals("LT")) {
			setPosition(e.x   - w + this.halfW, e.y + e.halfH - this.halfH + 3);
			e.faceLT();
		}
		else if (direction.equals("RT")) {
			setPosition(e.x + e.w - this.halfW, e.y + e.halfH - this.halfH + 3);
			e.faceRT();
		}
		
		worldImage = Toolkit.getDefaultToolkit().getImage(folder + getName() + "_" + direction + ".png");
		
		setSize(worldImage.getWidth(null), worldImage.getHeight(null));
	}
	
	public void draw(Graphics pen)
	{
		if (equipped) pen.drawImage(worldImage, x - Camera.x, y - Camera.y, w, h, null);
		
		else super.draw(pen);
	}
}
