import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public abstract class GameBase extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	int mx = -1;
	int my = -1;
	
	
	
	boolean UP_Pressed = false;
	boolean DN_Pressed = false;
	boolean LT_Pressed = false;
	boolean RT_Pressed = false;
	
	Image    offScreenImg;
	Graphics offScreenPen;
	

	public void run()
	{
		// Game Loop
		while(true)
		{

			inGameLoop();
			
			
			repaint();
						
			try
			{			
				Thread.sleep(15);
			}
			catch(Exception x) {};
		}
	}
	
	
	
	
	public abstract void inGameLoop();
	
	//draws offscreens and passes it to the screen we see, only clears offscreen
	public void update(Graphics pen)
	{
		offScreenPen.clearRect(0, 0, 2560, 1440);
	
		paint(offScreenPen);
		
		pen.drawImage(offScreenImg, 0, 0, null);
	}
	
	public void paint(Graphics pen)
	{
	}

	
	public void init()
	{	
		//Every image has its own graphics object
		offScreenImg = createImage(2560, 1440);
		offScreenPen = offScreenImg.getGraphics();
		
		addKeyListener(this);
		requestFocus();
		
		addMouseListener(this);
		
		addMouseMotionListener(this);

		
		Thread t = new Thread(this);

		t.start();
	}
	
	
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	
	
	public void keyPressed(KeyEvent e)
	{		
		int code = e.getKeyCode();
		
		if (code == e.VK_UP   )   UP_Pressed = true;  
		if (code == e.VK_DOWN )   DN_Pressed = true;  
		if (code == e.VK_LEFT )   LT_Pressed = true;  
		if (code == e.VK_RIGHT)   RT_Pressed = true; 
		
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();

		if (code == e.VK_UP   )   UP_Pressed = false;  
		if (code == e.VK_DOWN )   DN_Pressed = false;  
		if (code == e.VK_LEFT )   LT_Pressed = false;  
		if (code == e.VK_RIGHT)   RT_Pressed = false;  
		
	}
	
	
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}	
	public void mouseExited(MouseEvent e){}

}
