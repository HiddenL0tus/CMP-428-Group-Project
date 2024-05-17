package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import engine.Camera;
import engine.Rect;

public class Arrow extends Rect
{
	public Image image;
	
	public Arrow(int x, int y, int w, int h, String direction, double vx, double vy)
	{
		super(x, y, w, h, Color.RED, vx, vy);
		image = Toolkit.getDefaultToolkit().getImage("weapons/Arrow_" + direction + "_0.png");
	}
	
	public void draw(Graphics pen)
	{
		pen.drawImage(image, x - Camera.x, y - Camera.y, w, h, null);
		super.draw(pen);
	}
}
