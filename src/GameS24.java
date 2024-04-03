import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GameS24 extends GameBase {
	
	//get the screen width and height of the device being used for camera calculations
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int SCREEN_WIDTH  = gd.getDisplayMode().getWidth ();
	public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
	
	//actions for characters
	String[] pose = {"dn", "up", "lt", "rt"};
	
	Image testMap;
	Rect player;
	Rect2[] walls, doors;
	
	public void initialize()
	{	
		testMap = Toolkit.getDefaultToolkit().getImage("preview.png");
		
		player = new Rect(500, 600, 100, 100);
		
		walls = new Rect2[]
				{
					new Rect2(860, 540, 75, 70),
					new Rect2(1564, 153, 48, 804),
					new Rect2(630, 155, 923, 32),
					new Rect2(619, 154, 12, 831),
					new Rect2(26, 1264, 2519, 40),	
				};
		doors = new Rect2[]
				{
					new Rect2(500,500,300,300)
				};
		
		Camera.setPosition(player.x + (player.w / 2) - (SCREEN_WIDTH  / 2),
				   		   player.y + (player.h / 2) - (SCREEN_HEIGHT / 2));
	}
	
	public void inGameLoop()
	{
		
		player.physicsOff();
				
		if(pressing[UP]) player.goUP(5);
		if(pressing[DN]) player.goDN(5);
		if(pressing[LT]) player.goLT(5);
		if(pressing[RT]) player.goRT(5);					
				
		player.move();
		

		for(Rect2 wall : walls) if(player.overlaps(wall)) player.pushedOutOf(wall);
		
		updateCamera();
	}
	
	public void updateCamera()
	{
		//centers the camera on the player
		int targetX = (int)Math.round(player.x + (player.w / 2) - (SCREEN_WIDTH  / 2));
	    int targetY = (int)Math.round(player.y + (player.h / 2) - (SCREEN_HEIGHT / 2));

	    Camera.setPosition(targetX, targetY); 
	}
	
	public void paint(Graphics pen)
	{
		
		pen.drawImage(testMap, 0 - Camera.x, 0 - Camera.y, 894 * 2, 864 * 2, null); //the image size is 894x864

		player.draw(pen);
		
		for(Rect2 wall : walls) wall.draw(pen);
		
		for(Rect2 door : doors) door.draw(pen);	
	}
	
	//TOOL FOR RESIZING RECTS BEGINS//
	public void keyTyped(KeyEvent e)
	{		
		char keyChar = Character.toLowerCase(e.getKeyChar());
		
		if (keyChar == 'p') 
		{	
			System.out.println(player); 
			
			for (Rect wall : walls) System.out.println(wall);
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		int nx = e.getX();
		int ny = e.getY();
		
		int dx = nx - mx;
		int dy = ny - my;
		
		for(Rect2 wall : walls) 
		{	
			if(wall.resizer.held) wall.resizeBy(dx,  dy);
			else if(wall.held) wall.moveBy(dx, dy);
		}
		
		for(Rect2 door : doors) 
		{
			if(door.resizer.held) door.resizeBy(dx,  dy);
			else if(door.held) door.moveBy(dx, dy);
		}
		
		mx = nx;
		my = ny;
	}
	
	public void mousePressed(MouseEvent e)
	{
		mx = e.getX();
		my = e.getY();
		
		for(Rect2 wall : walls) 
		{
			if(wall.contains(mx,  my)) wall.grabbed();	
			if(wall.resizer.contains(mx,  my)) wall.resizer.grabbed();
		}
		
		for(Rect2 door : doors) 
		{
			if(door.contains(mx,  my)) door.grabbed();
			if(door.resizer.contains(mx,  my))  door.resizer.grabbed();
		}
		
	}
	
	public void mouseReleased(MouseEvent e) 
	{
		for(Rect2 wall : walls) 
		{
			wall.dropped();
			wall.resizer.dropped();
		}
		
		for(Rect2 door: doors) 
		{
			door.dropped();
			door.resizer.dropped();
		}
	}
	//TOOL for RESIZING RECTS ENDS//
	
	public static void main(String[] args) 
	{
		
	}

}
