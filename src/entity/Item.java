package entity;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Toolkit;

import engine.*;

public class Item extends Rect{
	
	public Image image;
	
	public boolean equipped  = false;
	
	public boolean stackable = false;

	private String itemType = "";
	private String  subType = "";
	private String     name = "";
	
	public Item(String name, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.name  = name;
	}
	
	public void setConsumable() {
		itemType = "consumable";
	}

	public void setWeapon() {
		itemType = "weapon";
	}
	
	public void setSubType(String type) {
		subType = type;
	}
	
	public String getItemType() {
		return itemType;
	}
	
	public String getSubType() {
		return subType;
	}
	
	public String getName() {
		return name;
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
}
