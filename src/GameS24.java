import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GameS24 extends GameBase {
	
	ImageLayer m = new ImageLayer("mountains.gif", 0, 0, 10);
	ImageLayer h = new ImageLayer("houses.gif", 0, 0, 3);	
	ImageLayer t = new ImageLayer("trees.gif", 0, 0, 1);
	
	Image testMap = Toolkit.getDefaultToolkit().getImage("preview.png");
	
	//actions for characters
	String[] pose = {"dn", "up", "lt", "rt"};
	
	Rect2 g = new Rect2(860, 540, 100, 100);
	
	Rect2[] wall =
		{
			new Rect2(860, 540, 75, 70),
			new Rect2(1564, 153, 48, 804),
			new Rect2(630, 155, 923, 32),
			new Rect2(619, 154, 12, 831),
			new Rect2(26, 1264, 2519, 40),
		

		};
	
	Rect2[] door = {
			new Rect2(500,500,300,300)
	};

	
	
	public void inGameLoop()
	{
		
		g.physicsOff();
				
		//if(UP_Pressed) soldier.jump(25);
		if(UP_Pressed) g.goUP(5);
		if(DN_Pressed) g.goDN(5);
		if(LT_Pressed) g.goLT(5);
		if(RT_Pressed) g.goRT(5);
										
		//if(UP_Pressed) chris.goUP(5);
		//if(DN_Pressed) chris.goDN(5);
				
		g.move();
		
		
		//if(LT_Pressed)			Camera.moveLT(10);
		//if(RT_Pressed)			Camera.moveRT(10);
		
		//CODE THAT ITERATES THROUGH wall Array AND CHECKS IF CHRIS IS OVERLAPS, THEN PUSHES OUT//
		for( int i = 0; i < wall.length; i++) {
			if(g.overlaps(wall[i])){
				
				g.pushedOutOf(wall[i]);
				}
		}
			
	}
	
	
	
	public void paint(Graphics pen)
	{
		
		pen.drawImage(testMap, 0, 0, 2560, 1440, null);
		
		m.draw(pen);
		h.draw(pen);
		t.draw(pen);

		g.draw(pen);
		
		//draws wall hitBox
		for(int i = 0; i < wall.length; i++)
		{	
			wall[i].draw(pen);
		}
		
		for(int i = 0; i < door.length; i++)
		{	
			door[i].draw(pen);
		}
		
	}
	
	
	
	
	
	//TOOL FOR SIZING RECTS//
	public void keyPressed(KeyEvent e)
	{		
		int code = e.getKeyCode();
		
		if (code == e.VK_UP   )   UP_Pressed = true;  
		if (code == e.VK_DOWN )   DN_Pressed = true;  
		if (code == e.VK_LEFT )   LT_Pressed = true;  
		if (code == e.VK_RIGHT)   RT_Pressed = true; 
		
		if (code == e.VK_P) {
			
			System.out.println(g); 
			for(int i = 0; i < wall.length; i++) {
				System.out.println(wall[i]);
			}
		}
	}
	
	//TOOL for RESIZING RECTS//
	public void mouseDragged(MouseEvent e)
	{
		int nx = e.getX();
		int ny = e.getY();
		
		int dx = nx - mx;
		int dy = ny - my;
		
		for(int i = 0; i < wall.length; i++) {
			if(wall[i].resizer.held)  wall[i].resizeBy(dx,  dy);
		else
		if(wall[i].held)  wall[i].moveBy(dx, dy);
		}
		
		for(int i = 0; i < door.length; i++) {
			if(door[i].resizer.held)  door[i].resizeBy(dx,  dy);
		else
		if(door[i].held)  door[i].moveBy(dx, dy);
		}
		
		mx = nx;
		my = ny;
	}
	
	public void mousePressed(MouseEvent e)
	{
		mx = e.getX();
		my = e.getY();
		
		for(int i = 0; i <wall.length; i++) {
		if(wall[i].contains(mx,  my))  wall[i].grabbed();
		if(wall[i].resizer.contains(mx,  my))  wall[i].resizer.grabbed();
		}
		
		for(int i = 0; i <door.length; i++) {
		if(door[i].contains(mx,  my))  door[i].grabbed();
		if(door[i].resizer.contains(mx,  my))  door[i].resizer.grabbed();
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
		for(int i= 0; i < wall.length; i++) {
		wall[i].dropped();
		wall[i].resizer.dropped();
		}
		
		for(int i= 0; i < door.length; i++) {
		door[i].dropped();
		door[i].resizer.dropped();
		}
	}
	//TOOL for RESIZING RECTS//
	
	public static void main(String[] args) {
		
	}

}
