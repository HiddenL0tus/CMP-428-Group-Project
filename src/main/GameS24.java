package main;
import java.applet.Applet;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import engine.*;
import entity.*;

public class GameS24 extends GameBase
{
	
	 public static final int MAP_WIDTH = 100;   // in tiles
	  public static final int MAP_HEIGHT = 100;  // in tiles
	  public static final int TILE_SIZE = 16;    // in pixels
	 
	  public static final int MAP_PIXEL_WIDTH = MAP_WIDTH * TILE_SIZE*3;    // in pixels
	  public static final int MAP_PIXEL_HEIGHT = MAP_HEIGHT * TILE_SIZE*3;  // in pixels
	  
	    
	//get the screen width and height of the device being used for camera calculations
	public static GraphicsDevice gd;
	public static int toolBarHeight   = 0;
	public static int bottomBarHeight = 0;
			
	public static int SCREEN_WIDTH, SCREEN_HEIGHT;
	
	private ArrayList<Entity> goodies;
	private ArrayList<Entity> baddies;
	private ArrayList<Arrow > playerProjectiles;
	private ArrayList< Item > items;
	
	Image testMap;
	
	Player player;
	Orc[] orcList;
	
	Rect2[] walls;
	
	public void initialize()
	{	 
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		SCREEN_WIDTH    = gd.getDisplayMode().getWidth ();
		SCREEN_HEIGHT   = gd.getDisplayMode().getHeight();
		toolBarHeight   = getInsets().top;
		bottomBarHeight = getInsets().bottom;
		
		testMap = Toolkit.getDefaultToolkit().getImage("map1.png");
		
		goodies           = new ArrayList<>();
		baddies           = new ArrayList<>();
		playerProjectiles = new ArrayList<>();
		items             = new ArrayList<>();
		
		items.add(new Potion("Health", 597, 400));
		items.add(new Potion("Immunity", 630, 400));
		items.add(new Potion("Health", 750, 400));
		items.add(new WoodSword(1300, 1000));
		items.add(new WoodBow(1350, 1000));
		
		player = new Player(2700, 2400);
			
		orcList = new Orc[] //it's easy to add more Orcs!
		{
			new Orc(600, 600),
			new Orc(200, 200),
			new Orc(800, 800),
			new Orc(1000, 1000),
		};
		
		goodies.add(player);
		
		for (Orc orcBaddy : orcList) baddies.add(orcBaddy);
		
		createWalls();
		
		int cameraX = player.x - (SCREEN_WIDTH / 2) + (player.w / 2);
	    int cameraY = player.y - (SCREEN_HEIGHT / 2) + (player.h / 2);
	    Camera.setPosition(cameraX, cameraY);
	}
	
	public void inGameLoop()
	{	
		player.setSize(48,48);
		player.physicsOff();
		 
		
		if (pressing[_1])
		{
			Weapon weapon1 = player.inventory.weapons[0];
			
			player.inventory.setSelectedWeapon(weapon1);
		}
		if (pressing[_2])
		{
			Weapon weapon2 = player.inventory.weapons[1];
			
			player.inventory.setSelectedWeapon(weapon2);
		}
		
		pickupItems(player);
		
		controlMovement();
		
		player.move();
		
		if (player.inventory.selectedWeapon != null) controlAttack(player.inventory.selectedWeapon);
		
		for (Rect projectile : playerProjectiles) projectile.move();
		
		playerDamagesBaddies();
		
		enemiesChaseAndEvadePlayer();
		
		for (Rect2 wall : walls)
		{
			if (player.overlaps(wall)) player.pushedOutOf(wall);
			
			for (Entity baddy : baddies) if (baddy.overlaps(wall)) baddy.pushedOutOf(wall);
		}
		updateCamera();
	}
	
	public void updateCamera()
	{
		//centers the camera on the player
		int targetX = (int)Math.round(player.x + (player.w / 2) - (SCREEN_WIDTH  / 2));
	    int targetY = (int)Math.round(player.y + (player.h / 2) - (SCREEN_HEIGHT / 2));

	    Camera.setPosition(targetX, targetY); 
	}
	
	public void pickupItems(Player player)
	{
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) 
		{
			Item item = iterator.next();
			if (player.overlaps(item))
			{	//attempt to add item to inventory after overlap
				boolean successful = player.inventory.addItem(item);
				if (successful) {
					iterator.remove(); 
				}
			}
		}
	}
	
	public void controlMovement() {
		int moveSpeed = 3;
		if (pressing[SHIFT]) moveSpeed = 5;
		
		if (pressing[UP]) player.goUP(moveSpeed);
		if (pressing[DN]) player.goDN(moveSpeed);
		if (pressing[LT]) player.goLT(moveSpeed);
		if (pressing[RT]) player.goRT(moveSpeed);

	    // Check if the intended position is within the map boundaries
	   
	}
	
	public void controlAttack(Weapon weapon)
	{
		weapon.isVisible = false; //make sword invisible each tick so it doesn't show when you aren't attacking
		
		if (!pressing[_W] && !pressing[_A] && !pressing[_S] && !pressing[_D])
		{
			player.atkEnabled = true;
		}
		else weapon.isVisible = true;
		
		if (weapon.isMelee() && player.atkEnabled);
		{
				 if (pressing[_W]) weapon.updatePositionRelativeTo(player, "UP");
			else if (pressing[_S]) weapon.updatePositionRelativeTo(player, "DN");
			else if (pressing[_A]) weapon.updatePositionRelativeTo(player, "LT");
			else if (pressing[_D]) weapon.updatePositionRelativeTo(player, "RT");	
		}		
		if (weapon.isRanged() && player.atkEnabled)
		{
			int pelletVelocity = 6;
			if (pressing[_W]) 
			{
				weapon.updatePositionRelativeTo(player, "UP");
				playerProjectiles.add(player.shootUP(pelletVelocity));
			}
			if (pressing[_S]) 
			{
				weapon.updatePositionRelativeTo(player, "DN");
				playerProjectiles.add(player.shootDN(pelletVelocity));
			}
			if (pressing[_A])
			{
				weapon.updatePositionRelativeTo(player, "LT");
				playerProjectiles.add(player.shootLT(pelletVelocity));
			}
			if (pressing[_D]) 
			{
				weapon.updatePositionRelativeTo(player, "RT");
				playerProjectiles.add(player.shootRT(pelletVelocity));
			}
		}
	}
	
	public void playerDamagesBaddies()
	{
		/* attempting to modify the list (via removing elements)
		 * while iterating over it requires an iterator for safe removal */
		
		Iterator<Entity> iterator = baddies.iterator();
		while (iterator.hasNext()) 
		{
			Entity baddy = iterator.next();
			
			Weapon weapon = player.inventory.selectedWeapon;
			//melee damage
			if (weapon != null && weapon.isVisible && weapon.isMelee() && weapon.overlaps(baddy))
			{
				baddy.takeDamage(6);
				if (baddy.isDead())
				{
					iterator.remove(); //removes the enemy from the baddies list
					break; //do not continue checking projectiles against the removed baddy
				}
			}
			
			//projectile damage
			Iterator<Arrow> arrowIterator = playerProjectiles.iterator();
			while(arrowIterator.hasNext())
			{
				Rect arrow = arrowIterator.next();
				if (arrow.overlaps(baddy))
				{
					baddy.takeDamage(4); //i think it does piercing damage
					if (baddy.isDead())
					{
						arrowIterator.remove();
						iterator.remove();
						break;
					}
					arrowIterator.remove();
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
		pen.setColor(Color.BLACK);
	    pen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		pen.drawImage(testMap, 0 - Camera.x, 0 - Camera.y, MAP_PIXEL_WIDTH, MAP_PIXEL_HEIGHT, null);
		
		  

		
		for (Sprite goody : goodies) goody.draw(pen); 
			
		for (Sprite baddy : baddies) baddy.draw(pen); 
		
        for (Rect projectile : playerProjectiles) projectile.draw(pen);
        
		//for (Rect2 wall : walls) wall.draw(pen);
		
		for (Item  item : items) item.draw(pen);
		
		
		Weapon weapon = player.inventory.selectedWeapon;
		if (weapon != null && weapon.isVisible) weapon.draw(pen);
		
		player.inventory.draw(pen, this);
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
		int nx = e.getX() + Camera.x;
		int ny = e.getY() + Camera.y;
		
		int dx = nx - mx;
		int dy = ny - my;
		
		for(Rect2 wall : walls) 
		{	
			if(wall.resizer.held) wall.resizeBy(dx,  dy);
			else if(wall.held) wall.moveBy(dx, dy);
		}
		
		mx = nx;
		my = ny;
	}
	
	public void mousePressed(MouseEvent e)
	{
		mx = e.getX() + Camera.x;
		my = e.getY() + Camera.y;
		
		for(Rect2 wall : walls) 
		{
			if(wall.contains(mx,  my)) wall.grabbed();
			if(wall.resizer.contains(mx,  my)) wall.resizer.grabbed();
		}
	}
	
	public void mouseReleased(MouseEvent e) 
	{
		for(Rect2 wall : walls) 
		{
			wall.dropped();
			wall.resizer.dropped();
		}
	}
	//TOOL for RESIZING RECTS ENDS*/
	
	public void createWalls()
	{
		walls   = new Rect2[]
				{
					new Rect2(1920, 3, 960, 144),
					new Rect2(3840, 3, 960, 144),
					new Rect2(48, 336, 96, 1680),
					new Rect2(48, 336, 1968, 144),
					new Rect2(1920, 3, 96, 480),
					new Rect2(2784, 3, 96, 480),
					new Rect2(2784, 336, 240, 144),
					new Rect2(2928, 144, 96, 336),
					new Rect2(2928, 144, 912, 144),
					new Rect2(3744, 96, 96, 432),
					new Rect2(4704, 3, 96, 4752),
					new Rect2(1920, 1824, 432, 240),
					new Rect2(2448, 1824, 432, 240),
					new Rect2(1920, 624, 96, 2640),
					new Rect2(2784, 624, 96, 2640),
					new Rect2(1920, 2208, 240, 144),
					new Rect2(2256, 2208, 624, 144),
					new Rect2(1920, 2784, 960, 96),
					new Rect2(2400, 2208, 96, 432),
					new Rect2(1920, 1344, 720, 192),
					new Rect2(2736, 1344, 48, 192),
					new Rect2(1920, 864, 432, 240),
					new Rect2(2448, 864, 432, 240),
					new Rect2(2304, 384, 192, 144),
					new Rect2(2784, 624, 240, 336),
					new Rect2(2880, 960, 384, 144),
					new Rect2(3168, 960, 96, 912),
					new Rect2(3360, 1056, 96, 192),
					new Rect2(3360, 1392, 96, 192),
					new Rect2(3744, 480, 336, 240),
					new Rect2(3840, 3, 96, 576),
					new Rect2(4176, 480, 624, 144),
					new Rect2(4176, 768, 624, 144),
					new Rect2(4176, 480, 96, 576),
					new Rect2(3552, 960, 720, 144),
					new Rect2(3552, 960, 96, 624),
					new Rect2(3552, 1248, 624, 144),
					new Rect2(4080, 960, 96, 288),
					new Rect2(3168, 1728, 1248, 144),
					new Rect2(4512, 1728, 288, 240),
					new Rect2(3648, 1728, 96, 432),
					new Rect2(4320, 1728, 96, 288),
					new Rect2(2880, 2784, 432, 144),
					new Rect2(3216, 2448, 528, 144),
					new Rect2(3648, 2304, 96, 288),
					new Rect2(3216, 2448, 96, 432),
					new Rect2(3408, 2784, 1404, 144),
					new Rect2(3744, 2496, 240, 48),
					new Rect2(4080, 2496, 624, 48),
					new Rect2(3840, 2784, 96, 1056),
					new Rect2(2784, 3840, 1152, 144),
					new Rect2(2496, 3408, 384, 480),
					new Rect2(1920, 3408, 384, 480),
					new Rect2(2304, 3408, 48, 144),
					new Rect2(2448, 3408, 48, 144),
					new Rect2(2448, 3744, 48, 144),
					new Rect2(2304, 3744, 48, 144),
					new Rect2(2352, 3456, 96, 96),
					new Rect2(2352, 3792, 96, 96),
					new Rect2(2256, 2832, 96, 432),
					new Rect2(2448, 2832, 96, 432),
					new Rect2(1920, 3120, 432, 144),
					new Rect2(2448, 3120, 432, 144),
					new Rect2(864, 3840, 1152, 144),
					new Rect2(1440, 3840, 96, 960),
					new Rect2(1440, 4704, 1920, 96),
					new Rect2(3264, 3840, 96, 432),
					new Rect2(3264, 4368, 96, 432),
					new Rect2(3264, 4032, 768, 192),
					new Rect2(3936, 3888, 864, 144),
					new Rect2(3264, 4368, 768, 192),
					new Rect2(3936, 4560, 864, 96),
					new Rect2(1920, 4128, 192, 144),
					new Rect2(2688, 4128, 192, 144),
					new Rect2(1632, 4368, 192, 144),
					new Rect2(2304, 4368, 192, 144),
					new Rect2(2976, 4368, 192, 144),
					new Rect2(864, 2928, 96, 912),
					new Rect2(864, 2784, 528, 144),
					new Rect2(1488, 2784, 528, 144),
					new Rect2(240, 2928, 672, 96),
					new Rect2(240, 1824, 96, 1200),
					new Rect2(48, 1824, 1344, 144),
					new Rect2(240, 2112, 768, 144),
					new Rect2(1152, 2112, 240, 144),
					new Rect2(768, 1824, 96, 384),
					new Rect2(1296, 1824, 96, 384),
					new Rect2(240, 2496, 240, 144),
					new Rect2(624, 2496, 1296, 144),
					new Rect2(1824, 2112, 96, 480),
					new Rect2(1488, 2112, 432, 144),
					new Rect2(1488, 1824, 336, 432),
					new Rect2(1728, 624, 192, 1296),
					new Rect2(240, 1488, 1392, 144),
					new Rect2(1536, 1488, 96, 336),
					new Rect2(240, 1200, 576, 144),
					new Rect2(912, 1200, 672, 144),
					new Rect2(1488, 1296, 96, 96),
					new Rect2(144, 912, 192, 144),
					new Rect2(432, 912, 768, 144),
					new Rect2(1296, 912, 432, 144),
					new Rect2(1584, 1056, 144, 192),
					new Rect2(240, 624, 480, 144),
					new Rect2(816, 624, 768, 144),
					new Rect2(432, 432, 96, 240),
					new Rect2(1344, 432, 96, 240),
					new Rect2(1344, 432, 96, 240),
				};
	}
	
	public static void main(String[] args) 
	{

	}
}
