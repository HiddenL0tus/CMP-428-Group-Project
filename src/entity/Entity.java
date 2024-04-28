package entity;

import java.awt.Color;
import engine.*;
import main.Inventory;

public class Entity extends Sprite
{
	public HealthState health;
	public Rect hurtbox;
	public boolean atkEnabled = true;
	public int halfW, halfH;
	public Inventory inventory;
	
	public Entity(String name, String[] pose, int x, int y, int w, int h, int[] count, int duration, int maxHealth) {
		super(name, pose, x, y, w, h, count, duration);
		halfW = w/2;
		halfH = h/2;
		this.health = new HealthState(maxHealth);
		inventory   = new Inventory();
	}
	
	public void goLT(int dx)
	{
		super.goLT(dx);
		
		action = 0;
		
		moving = true;
	}
	
	public void goRT(int dx)
	{
		super.goRT(dx);

		action = 1;

		moving = true;
	}
	
	public void goDN(int dy)
	{
		super.goDN(dy);

		moving = true;
	}
	
	
	public void goUP(int dy)
	{
		super.goUP(dy);
	
		moving = true;
	}
	
	//melee attacks need no cooldown
	public void atkLT()
	{
		action = 0;
	}
	
	public void atkRT()
	{
		action = 1;
	}
	
	public void atkUP()
	{
		//action = 0;
	}
	
	public void atkDN()
	{
		//action = 0;
	}
		
	public Rect shootLT(double velocity)
	{
		action = 0;
		
		atkEnabled = false;
		
		return new Rect(x - w, y, w, h, Color.RED, -velocity, 0);
	}
	
	public Rect shootRT(double velocity)
	{
		action = 1;
		
		atkEnabled = false;
		
		return new Rect(x + w, y, w, h, Color.RED, velocity, 0);
	}
	
	public Rect shootUP(double velocity)
	{
		//action = 3;
		
		atkEnabled = false;
		
		return new Rect(x, y - h, w, h, Color.RED, 0, -velocity);
	}
	
	public Rect shootDN(double velocity)
	{
		//action = 2;	
		
		atkEnabled = false;
		
		return new Rect(x, y + w, w, h, Color.RED, 0, velocity);
	}
	
	public void takeDamage(int damageAmount) 
	{	
        health.takeDamage(damageAmount);
    }
}
