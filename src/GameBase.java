import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public abstract class GameBase extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	int mx = -1;
	int my = -1;
	
	public static boolean[] pressing = new boolean[1024];
	public static boolean[] typed    = new boolean[1024];
	
	public static final int
	UP          = KeyEvent.VK_UP,
	DN          = KeyEvent.VK_DOWN,
	LT          = KeyEvent.VK_LEFT,
	RT          = KeyEvent.VK_RIGHT,
	
	_A          = KeyEvent.VK_A,
	_B          = KeyEvent.VK_B,
	_C          = KeyEvent.VK_C,
	_D          = KeyEvent.VK_D,
	_E          = KeyEvent.VK_E,
	_F          = KeyEvent.VK_F,
	_G          = KeyEvent.VK_G,
	_H          = KeyEvent.VK_H,
	_I          = KeyEvent.VK_I,
	_J          = KeyEvent.VK_J,
	_K          = KeyEvent.VK_K,
	_L          = KeyEvent.VK_L,
	_M          = KeyEvent.VK_M,
	_N          = KeyEvent.VK_N,
	_O          = KeyEvent.VK_O,
	_P          = KeyEvent.VK_P,
	_Q          = KeyEvent.VK_Q,
	_R          = KeyEvent.VK_R,
	_S          = KeyEvent.VK_S,
	_T          = KeyEvent.VK_T,
	_U          = KeyEvent.VK_U,
	_V          = KeyEvent.VK_V,
	_W          = KeyEvent.VK_W,
	_X          = KeyEvent.VK_X,
	_Y          = KeyEvent.VK_Y,
	_Z          = KeyEvent.VK_Z,

	_1          = KeyEvent.VK_1,
	_2          = KeyEvent.VK_2,
	_3          = KeyEvent.VK_3,
	_4          = KeyEvent.VK_4,
	_5          = KeyEvent.VK_5,
	_6          = KeyEvent.VK_6,
	_7          = KeyEvent.VK_7,
	_8          = KeyEvent.VK_8,
	_9          = KeyEvent.VK_9,
	_0          = KeyEvent.VK_0,
	
	CTRL        = KeyEvent.VK_CONTROL,
	SHIFT       = KeyEvent.VK_SHIFT,
	ALT         = KeyEvent.VK_ALT,
	
	SPACE       = KeyEvent.VK_SPACE,
	
	COMMA       = KeyEvent.VK_COMMA,
	PERIOD      = KeyEvent.VK_PERIOD,
	SLASH       = KeyEvent.VK_SLASH,
	SEMICOLON   = KeyEvent.VK_SEMICOLON,
	COLON       = KeyEvent.VK_COLON,
	QUOTE       = KeyEvent.VK_QUOTE,
	MINUS		= KeyEvent.VK_MINUS,
	EQUALS		= KeyEvent.VK_EQUALS,
	
	F1          = KeyEvent.VK_F1,
	F2          = KeyEvent.VK_F2,
	F3          = KeyEvent.VK_F3,
	F4          = KeyEvent.VK_F4,
	F5          = KeyEvent.VK_F5,
	F6          = KeyEvent.VK_F6,
	F7          = KeyEvent.VK_F7,
	F8          = KeyEvent.VK_F8,
	F9          = KeyEvent.VK_F9,
	F10         = KeyEvent.VK_F10,
	F11         = KeyEvent.VK_F11,
	F12         = KeyEvent.VK_F12,
	
	ESC			= KeyEvent.VK_ESCAPE,
	ENTER		= KeyEvent.VK_ENTER;

	public void keyPressed (KeyEvent e)
	{
		pressing[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e)
	{
		pressing[e.getKeyCode()] = false;
	}
	
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
	public abstract void initialize();
	
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
		addMouseListener(this);
		addMouseMotionListener(this);
		requestFocus();
		
		initialize();
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	
	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}	
	public void mouseExited(MouseEvent e){}

}
