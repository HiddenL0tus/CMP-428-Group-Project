package engine;
import java.awt.*;

public class Animation {
	
	private Image[] image;
	
	//tracks next image
	private int     next;
	
	//how often to keep the same image until the next one, controls speed of animation
	private int duration;
	//only if delay is 0 do i go to the next image because 60 fps is too fast to switch images immediately
	private int delay;
	
	public Animation(String name, int count, int duration)
	{
		image = new Image[count];
		
		for(int i = 0; i < count; i++)
		{
			image[i] = Toolkit.getDefaultToolkit().getImage(name + "_" + i + ".png");
		}
		
		this.duration = duration;
		
		delay = duration;
	}
	
	public Image stillImage()
	{
		return image[0];
	}
	
	public Image nextImage()
	{
		if(delay == 0)
		{
			next++;
			
			if(next == image.length)  next = 1;
			
			delay = duration;
		}
		
		delay--;
		
		
		return image[next];
	}

}
