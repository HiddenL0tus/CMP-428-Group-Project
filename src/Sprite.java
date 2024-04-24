import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Sprite extends Rect2 {
	
	Animation[] animation;
	int[] animationFrames;
	public HealthState health;
	public Rect hurtbox;
	public Rect meleeHitbox   = null; //each sprite can have one melee hitbox
	public boolean atkEnabled = true;
		
		//the number indicates the action that we chose to store in that element of the array
		int action = 0;
		
		boolean moving = false;
		
		//change count to an array when animations have different # of images, to store the amount of images int[] count
		public Sprite(String name, String[] pose, int x, int y, int w, int h, int[] count, int duration, int maxHealth)
		{
			super(x, y, w, h);
			
			//length = amount of total poses, movement, attacking etc 
			animation = new Animation[pose.length];
			animationFrames = new int[pose.length];
			
			for(int i = 0; i < animation.length; i++)
			{
				animationFrames[i] = count[i]; 
				animation[i] = new Animation(name + "_" + pose[i], animationFrames[i], duration);
			}
			
			this.health = new HealthState(maxHealth);
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
			
			meleeHitbox = new Rect(x - w, y, w, h, Color.RED);
		}
		
		public void atkRT()
		{
			action = 1;
			
			meleeHitbox = new Rect(x + w, y, w, h, Color.RED);
		}
		
		public void atkUP()
		{
			//action = 0;
			
			meleeHitbox = new Rect(x, y - h, w, h, Color.RED);
		}
		
		public void atkDN()
		{
			//action = 0;
			
			meleeHitbox = new Rect(x, y + w, w, h, Color.RED);
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
		
		public void draw(Graphics pen)
		{		
			if(!moving) //idle character logic
			{		
				pen.drawImage(animation[action].stillImage(), x - Camera.x, y - Camera.y, w, h, null);
			}	
			else
			{
				pen.drawImage(animation[action].nextImage(), x - Camera.x, y - Camera.y, w, h, null);				
				moving = false;
			}
			super.draw(pen); //Draws Sprites hurtbox
		}
}
