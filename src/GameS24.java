import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class GameS24 extends GameBase {
	
	private SpriteManager spriteManager = new SpriteManager();
	
	//get the screen width and height of the device being used for camera calculations
	static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int SCREEN_WIDTH  = gd.getDisplayMode().getWidth ();
	public static final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();
	
	//actions for characters
	String[] pose = {"LT", "RT","atkLT", "atkRT"};
	String[] poseMonster = {"LT", "RT"};
	int[] count = {6,6,6,6};
	int[] countMonster = {6,6};
	
	Image testMap;
	//Rect player;
	Rect2[] walls, doors;
	
	Sprite player;
	int currentAnimation = 0;
	private Rect attackHitbox; // Variable to hold the attack hitbox
	
	Sprite orc;
	
	public void initialize()
	{	
		testMap = Toolkit.getDefaultToolkit().getImage("preview.png");

		player = new Sprite("Knight", pose       , 1200, 1200, 38, 37, count       , 10,  2, spriteManager);
		orc    = new Sprite("Orc"   , poseMonster,  600,  600, 38, 37, countMonster, 10, 10, spriteManager);
		
		walls = new Rect2[]
		{
				new Rect2( 860,  540,   75,  70),
				new Rect2(1564,  153,   48, 804),
				new Rect2( 630,  155,  923,  32),
				new Rect2( 619,  154,   12, 831),
				new Rect2(  26, 1264, 2519,  40),	
		};
		
		doors = new Rect2[]
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
	
		attackHitbox = null; // Reset the attack hitbox
		
		if(pressing[_A]) {
			player.atkLT();
			attackHitbox = new Rect(player.x - player.w, player.y, player.w, player.h); // Example dimensions   
		}
		if(pressing[_D]) {
			player.atkRT();
			attackHitbox = new Rect(player.x + player.w, player.y, player.w, player.h); // Example dimensions
		}
		if(pressing[_S])  {
			player.atkDN();
			attackHitbox = new Rect(player.x, player.y + player.w, player.w, player.h); // Example dimensions
		}
		if(pressing[_W])  {
			player.atkUP();
			attackHitbox = new Rect(player.x, player.y - player.h, player.w, player.h); // Example dimensions   
		}
		
		if (attackHitbox != null && attackHitbox.overlaps(orc)) 
		{
				boolean alive = orc.takeDamage(1);  //if orc health drops to 0, evals as false but is negated and set to true(b/c its true it executes next code
				if (!alive) spriteManager.removeSprite(orc); // Remove the sprite if it's dead			
		}
		
		//orc chase and evade logic
		int speed = 1;
		
		int orcToPlayerDistance = Math.abs(player.x - orc.x) + Math.abs(player.y - orc.y);
		
		if (orcToPlayerDistance < 180) orc.evade(player, speed * 2);
		
		else orc.chase(player, speed);
		
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
	
	public void controlMovement()
	{
		int moveSpeed = 3;
		if (pressing[SHIFT]) moveSpeed = 5;
		
		if(pressing[LT]) player.goLT(moveSpeed);
		if(pressing[RT]) player.goRT(moveSpeed);
		if(pressing[DN]) player.goDN(moveSpeed);
		if(pressing[UP]) player.goUP(moveSpeed);
	}
	
	public void paint(Graphics pen)
	{
		
		pen.drawImage(testMap, 0 - Camera.x, 0 - Camera.y, 894 * 2, 864 * 2, null); //the image size is 894x864

		//Draws all the sprites in the spriteManager List
		for (Sprite sprite : spriteManager.getSprites()) {
			sprite.draw(pen);	
		}
		
		// Draw attack hitbox if exists
        if (attackHitbox != null) {
            pen.setColor(Color.RED);
            pen.drawRect(attackHitbox.x - Camera.x, attackHitbox.y - Camera.y, attackHitbox.w, attackHitbox.h);
        }
		
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
