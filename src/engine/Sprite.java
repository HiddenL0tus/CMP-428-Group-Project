package engine;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Sprite extends Rect 
{
	protected Animation[] animation;
	int[] animationFrames;
	
	public int     action = 0; //the number indicates the action that we chose to store in that element of the array
	public boolean moving = false;			
	
		
	//change count to an array when animations have different # of images, to store the amount of images int[] count
	public Sprite(String name, String[] pose, int x, int y, int w, int h, int[] count, int duration)
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
