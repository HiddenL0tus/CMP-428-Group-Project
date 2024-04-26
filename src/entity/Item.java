package entity;
import java.awt.Graphics;

import java.awt.Image;

import engine.*;

public class Item extends Rect{
	
	public Image image;
	public boolean equipped = false;
	
	public Item(String name, int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void draw(Graphics pen) {
		if(!equipped) {
			pen.drawImage(image, x - Camera.x, y - Camera.y, w, h, null);
		}
		super.draw(pen);
	}

}