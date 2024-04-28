package entity;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Toolkit;

import engine.*;

public class Item extends Rect{
	
	public Image image;
	public Image worldImage;
	public boolean equipped  = false;
	public boolean isVisible = true;

	private String itemType = "";
	private String  subType = "";
	
	private String folder = "";
	public  String name   = "";
	
	public Item(String name, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.name  = name;
	}
	
	public void setConsumable() {
		itemType = "consumable";
	}

	public void setWeapon() {
		itemType = "weapon";
		folder   = "weapons/";
	}
	
	public void setMelee() {
		subType = "melee";
	}
	
	public void setRanged() {
		subType = "ranged";
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public String getSubType() {
		return subType;
	}
	
	public void equip() {
		equipped = true;
	}
	
	public boolean isWeapon()
	{
		return getItemType().equals("weapon");
	}
	
	public boolean isConsumable()
	{
		return getItemType().equals("consumable");
	}
	
	public boolean isEquipped()
	{
		return equipped == true;
	}
	
	public void draw(Graphics pen) {
		if(!equipped) {
			pen.drawImage(image, x - Camera.x, y - Camera.y, w, h, null);
		}
		super.draw(pen);
	}
	
	/*For now, this is just for weapons */
	public void updatePositionRelativeTo(Entity e, String direction)
	{
		isVisible = true;
		
		     if (direction.equals("UP")) setXYWH(e.x + e.halfW, e.y - h + 10, 12, 43);
		else if (direction.equals("DN")) setXYWH(e.x + e.halfW, e.y + h - 10, 12, 43);
		else if (direction.equals("LT")) setXYWH(e.x - w + 10, e.y + e.halfH, 43, 12);
		else if (direction.equals("RT")) setXYWH(e.x + w - 10, e.y + e.halfH, 43, 12);
		
		worldImage = Toolkit.getDefaultToolkit().getImage(folder + name + direction + ".png");
	}
}
