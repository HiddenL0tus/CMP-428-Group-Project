package entity;

import java.awt.Color;
import engine.*;
import main.Inventory;

public class Entity extends Sprite
{
	public HealthState health;
	public Rect hurtbox;
	public boolean atkEnabled = true;
	
	public Inventory inventory;
	
	public Entity(String name, String[] pose, int x, int y, int w, int h, int[] count, int duration, int maxHealth) {
		super(name, pose, x, y, w, h, count, duration);
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
	
	public void faceLT()
	{
		action = 0;
	}
	
	public void faceRT()
	{
		action = 1;
	}
		
	public Arrow shootLT(double velocity)
	{
		action = 0;
		
		atkEnabled = false;
		
		return new Arrow(x, y + halfH, 19, 11, "LT", -velocity, 0);
	}
	
	public Arrow shootRT(double velocity)
	{
		action = 1;
		
		atkEnabled = false;
		
		return new Arrow(x + w, y + halfH, 19, 11, "RT", velocity, 0);
	}
	
	public Arrow shootUP(double velocity)
	{
		//action = 3;
		
		atkEnabled = false;
		
		return new Arrow(x + halfW - 4, y, 11, 19, "UP", 0, -velocity);
	}
	
	public Arrow shootDN(double velocity)
	{
		//action = 2;	
		
		atkEnabled = false;
		
		return new Arrow(x + halfW - 4, y + h, 11, 19, "DN", 0, velocity);
	}
	
	public void takeDamage(int damageAmount) 
	{	
        health.takeDamage(damageAmount);
    }
	
	public boolean isDead()
	{
	    return health.getCurrentHealth() <= 0;
	}
}
