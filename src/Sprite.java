import java.awt.Graphics;

public class Sprite extends Rect2 {
	
	Animation[] animation;
	int[] animationFrames;
	private HealthState health;
	private SpriteManager spriteManager;
		
		//the number indicates the action that we chose to store in that element of the array
		int action = 0;
		
		
		boolean moving = false;
		
		
		//change count to an array when animations have different # of images, to store the amount of images int[] count
		public Sprite(String name, String[] pose, int x, int y, int[] count, int duration, int maxHealth, SpriteManager spriteManager)
		{
			super(x, y, 45, 45);
			
			//length = amount of total poses, movement, attacking etc 
			animation = new Animation[pose.length];
			animationFrames = new int[pose.length];
			
			for(int i = 0; i < animation.length; i++)
			{
				animationFrames[i] = count[i]; 
				animation[i] = new Animation(name + "_" + pose[i], animationFrames[i], duration);
			}
			
			this.health = new HealthState(maxHealth);
			
			// Add this sprite to the sprite manager
			this.spriteManager = spriteManager;
			spriteManager.addSprite(this);
			
			
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
			
			moving = true;
		}
		
		public void atkRT()
		{
			action = 1;
			
			moving = true;
		}
		
		public void atkDN()
		{
			//action = 2;
			
			moving = true;
			
		}
		
		public void atkUP()
		{
			//action = 3;
			
			moving = true;
		}
		
		public boolean takeDamage(int damageAmount) {
			
	        health.takeDamage(damageAmount);
	        
	        if (!health.isAlive()) {
	            // Handle sprite death
	            return false;
	        }
	        else return true;
	    }
		
		

		

		
		
		public void draw(Graphics pen)
		{	
			
			
			//idle character logic
			if(!moving)
			{
				//pen.drawImage(animation[action].stillImage(), x, y, w, h, null);
				pen.drawImage(animation[action].stillImage(), x, y, null);
				
				
				
				
		
			}
			
			
			else
			{
				//pen.drawImage(animation[action].nextImage(), x, y, w, h, null); // this one is scaling the image inside the box
				pen.drawImage(animation[action].nextImage(), x, y, null); // this one is not scaling but setting the x y to the top of the square
				
				
				
				moving = false;
			}
			
			
			
			
			//Draws Sprites hitBox
			pen.drawRect(x, y, w, h);
			
		}

}
