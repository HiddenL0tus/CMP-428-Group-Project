import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GameS24 extends GameBase 
{
	private ArrayList<Sprite> goodies     = new ArrayList<>();
	private ArrayList<Sprite> baddies     = new ArrayList<>();
	private ArrayList<Rect> projectiles   = new ArrayList<>();
	
	//get the screen width and height of the device being used for camera calculations
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int SCREEN_WIDTH  = gd.getDisplayMode().getWidth ();
	public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
	
	Image testMap;
	Rect2[] walls, doors;
	
	Player player;
	Orc[] orcList;
	
	boolean attackEnabled;
	
	
	
	public void initialize()
	{	
		testMap = Toolkit.getDefaultToolkit().getImage("preview.png");
		
		player  = new Player(1200, 1200);
			
		orcList = new Orc[] //it's easy to add more Orcs!
		{
			new Orc(600, 600),
			new Orc(200, 200),
			new Orc(800, 800),
			new Orc(1000, 1000),
		};
		
		attackEnabled = true;
		
		goodies.add(player);
		
		for (Orc orcBaddy : orcList) baddies.add(orcBaddy);
		
		walls   = new Rect2[]
		{
			new Rect2( 860,  540,   75,  70),
			new Rect2(1564,  153,   48, 804),
			new Rect2( 630,  155,  923,  32),
			new Rect2( 619,  154,   12, 831),
			new Rect2(  26, 1264, 2519,  40),	
		};
		
		doors   = new Rect2[]
		{
			new Rect2( 500, 500, 300, 300)
		};
		
		Camera.setPosition(player.x + (player.w / 2) - (SCREEN_WIDTH  / 2),
				   		   player.y + (player.h / 2) - (SCREEN_HEIGHT / 2));
	}
	
	public void inGameLoop()
	{	
		player.physicsOff();
				
		controlMovement();
		
		controlAttack();
		
		playerDamagesBaddies(1);
		
		enemiesChaseAndEvadePlayer();
		
		player.move();
		
		for (Rect projectile : projectiles) projectile.move();
		
		for (Rect2 wall : walls) if (player.overlaps(wall)) player.pushedOutOf(wall);
		
		updateCamera();
	}
	
	public void updateCamera()
	{
		//centers the camera on the player
		int targetX = (int)Math.round(player.x + (player.w / 2) - (SCREEN_WIDTH  / 2));
	    int targetY = (int)Math.round(player.y + (player.h / 2) - (SCREEN_HEIGHT / 2));

	    Camera.setPosition(targetX, targetY); 
	}
	
	public void controlMovement()
	{
		int moveSpeed = 3;
		if (pressing[SHIFT]) moveSpeed = 5;
		
		if(pressing[LT]) player.goLT(moveSpeed);
		if(pressing[RT]) player.goRT(moveSpeed);
		if(pressing[DN]) player.goDN(moveSpeed);
		if(pressing[UP]) player.goUP(moveSpeed);
	}
	
	public void controlAttack()
	{
		if (!pressing[_W] && !pressing[_A] && !pressing[_S] && !pressing[_D])
		{
			attackEnabled = true;
		}
		if(attackEnabled && pressing[_A]) {
			player.atkLT();
			
			Rect pellet = new Rect(player.x - player.w, player.y, player.w, player.h, Color.RED);
			pellet.setVelocity(-4, 0);
			
			projectiles.add(pellet);
			attackEnabled = false;
		}
		if(attackEnabled && pressing[_D]) {
			player.atkRT();
			
			Rect pellet =  new Rect(player.x + player.w, player.y, player.w, player.h, Color.RED);
			pellet.setVelocity(4, 0);
			
			projectiles.add(pellet);
			attackEnabled = false;
		}
		if(attackEnabled && pressing[_S])  {
			player.atkDN();
			
			Rect pellet = new Rect(player.x, player.y + player.w, player.w, player.h, Color.RED);
			pellet.setVelocity(0, 4);
			
			projectiles.add(pellet); 
			attackEnabled = false;
		}
		if(attackEnabled && pressing[_W])  {
			
			player.atkUP();
			
			Rect pellet = new Rect(player.x, player.y - player.h, player.w, player.h, Color.RED);
			pellet.setVelocity(0, -4);
			
			projectiles.add(pellet); 
			attackEnabled = false;
		}
	}
	
	public void playerDamagesBaddies(int amount)
	{
		/* attempting to modify the list (via removing elements)
		 * while iterating over it requires an iterator for safe removal */
		
		Iterator<Sprite> iterator = baddies.iterator();
		while (iterator.hasNext()) 
		{
			Sprite baddy = iterator.next();
			
			for (Rect projectile : projectiles) if (projectile.overlaps(baddy))
			{
				baddy.takeDamage(amount);
				if (baddy.health.isDead())
				{
					iterator.remove(); //removes the enemy from the baddies list
					break; //do not continue checking projectiles against the removed baddy
				}
			}	
		}
	}
	
	public void enemiesChaseAndEvadePlayer()
	{
		for (Sprite enemy : baddies)
		{
			int speed = 1;
			
			int enemyToPlayerDistance = Math.abs(player.x - enemy.x) + Math.abs(player.y - enemy.y);
			
			if (enemyToPlayerDistance < 180) enemy.evade(player, speed * 2);
			
			else enemy.chase(player, speed);
		}
	}
	
	public void paint(Graphics pen)
	{
		pen.drawImage(testMap, 0 - Camera.x, 0 - Camera.y, 894 * 2, 864 * 2, null); //the image size is 894x864
		
		for (Sprite goody : goodies) goody.draw(pen); //Draws all the sprites in the goodies List
			
		for (Sprite baddy : baddies) baddy.draw(pen); //Draws all the sprites in the baddies List
		
        for (Rect projectile : projectiles) projectile.draw(pen);
        
		for (Rect2 wall : walls) wall.draw(pen);
		
		for (Rect2 door : doors) door.draw(pen);	
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
