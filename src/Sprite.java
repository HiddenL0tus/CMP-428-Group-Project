import java.awt.Graphics;
import java.awt.Image;

public class Sprite extends Rect2 {
	
	Animation[] animation;
	int[] animationFrames;
	public HealthState health;
	public Rect hurtbox;
		
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
			
		public void atkLT()
		{
			action = 0;
		}
		
		public void atkRT()
		{
			action = 1;
		}
		
		public void atkDN()
		{
			//action = 2;	
		}
		
		public void atkUP()
		{
			//action = 3;
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
