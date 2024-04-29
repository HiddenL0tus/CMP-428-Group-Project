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
	
	Rect2[] walls, doors;
	
	public void initialize()
	{	
		gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		SCREEN_WIDTH    = gd.getDisplayMode().getWidth ();
		SCREEN_HEIGHT   = gd.getDisplayMode().getHeight();
		toolBarHeight   = getInsets().top;
		bottomBarHeight = getInsets().bottom;
		
		testMap = Toolkit.getDefaultToolkit().getImage("preview.png");
		
		goodies           = new ArrayList<>();
		baddies           = new ArrayList<>();
		playerProjectiles = new ArrayList<>();
		items             = new ArrayList<>();
		
		items.add(new Potion("Health", 597, 400));
		items.add(new Potion("Immunity", 630, 400));
		items.add(new Potion("Health", 750, 400));
		items.add(new WoodSword(1300, 1000));
		items.add(new WoodBow(1350, 1000));
		
		player  = new Player(1300, 1200);
			
		orcList = new Orc[] //it's easy to add more Orcs!
		{
			new Orc(600, 600),
			new Orc(200, 200),
			new Orc(800, 800),
			new Orc(1000, 1000),
		};
		
		goodies.add(player);
		
		for (Orc orcBaddy : orcList) baddies.add(orcBaddy);
		
		walls   = new Rect2[]
		{
				new Rect2(597, 310, 118, 56),
				new Rect2(435, 707, 159, 146),
				new Rect2(1359, 565, 64, 122),
				new Rect2(659, 711, 413, 144),
				new Rect2(943, 211, 129, 419),
				new Rect2(533, 1014, 115, 50),
				new Rect2(945, 962, 261, 150),
				new Rect2(1059, 963, 151, 343),
		};
		
		doors   = new Rect2[]
		{
			//new Rect2( 500, 500, 300, 300)
		};
		
		Camera.setPosition(player.x + (player.w / 2) - (SCREEN_WIDTH  / 2),
				   		   player.y + (player.h / 2) - (SCREEN_HEIGHT / 2));
	}
	
	public void inGameLoop()
	{	
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
	
	public void controlMovement()
	{
		int moveSpeed = 3;
		if (pressing[SHIFT]) moveSpeed = 5;
		
		if (pressing[UP]) player.goUP(moveSpeed);
		if (pressing[DN]) player.goDN(moveSpeed);
		if (pressing[LT]) player.goLT(moveSpeed);
		if (pressing[RT]) player.goRT(moveSpeed);
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
		pen.drawImage(testMap, 0 - Camera.x, 0 - Camera.y, 894 * 2, 864 * 2, null); //the image size is 894x864
		
		for (Sprite goody : goodies) goody.draw(pen); //Draws all the sprites in the goodies List
			
		for (Sprite baddy : baddies) baddy.draw(pen); //Draws all the sprites in the baddies List
		
        for (Rect projectile : playerProjectiles) projectile.draw(pen);
        
		for (Rect2 wall : walls) wall.draw(pen);
		
		for (Rect2 door : doors) door.draw(pen);
		
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
		mx = e.getX() + Camera.x;
		my = e.getY() + Camera.y;
		
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
	//TOOL for RESIZING RECTS ENDS*/
	
	public static void main(String[] args) 
	{

	}
}
