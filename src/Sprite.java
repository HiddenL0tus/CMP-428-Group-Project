import java.awt.Graphics;

public class Sprite extends Rect2 {
	
	Animation[] animation;
	
	//the number indicates the action that we chose to store in that element of the array
		int action = 0;
		
		boolean moving = false;
		
		
		//change count to an array when animations have different # of images, to store the amount of images int[] count
		public Sprite(String name, String[] pose, int x, int y, int count, int duration)
		{
			super(x, y, 75, 70);
			
			//length = amount of total poses, movement, attacking etc 
			animation = new Animation[pose.length];
			
			for(int i = 0; i < animation.length; i++)
			{
				animation[i] = new Animation(name + "_" + pose[i], count, duration);
			}
			
		}
		
		public void goLT(int dx)
		{
			super.goLT(dx);
			
			action = 2;
			
			moving = true;
		}
		
		public void goRT(int dx)
		{
			super.goRT(dx);

			action = 3;

			moving = true;
		}
		
		public void goUP(int dy)
		{
			super.goUP(dy);

			action = 1;
			
			moving = true;
		}
		public void goDN(int dy)
		{
			super.goDN(dy);

			action = 0;

			moving = true;
		}
		
		
		
		
		public void draw(Graphics pen)
		{	
			//idle character logic
			if(!moving)
			{
				pen.drawImage(animation[action].stillImage(), x - Camera.x, y - Camera.y, w, h, null);
			}
			else
			{
				pen.drawImage(animation[action].nextImage(), x - Camera.x, y - Camera.y, w, h, null);
				
				moving = false;
			}
			
			//Draws Sprites hitBox
			//pen.drawRect(x, y, w, h);
		}

}
