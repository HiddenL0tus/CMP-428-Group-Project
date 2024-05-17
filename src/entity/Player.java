package entity;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity
{
	private static String[] pose = {"LT", "RT"};
	
	private static int[] count = {7,7};

	public Player(int x, int y)
	{
		super("heroes/Knight", pose, x, y, 38, 37, count, 5, 2);
	}
	
	public void useItem(int slot) {
		Item item = inventory.items[slot];
		
		if(item == null) {
			return;
		}
		
		if(item.getName().equals("Health")) { //health potion
			health.restoreHealth();
			inventory.removeItem(slot); //take it out of your inventory
		}

		if(item.getName().equals("Immunity")) {
			health.increaseMaxHealthBy(2);   
			inventory.removeItem(slot);
		}

	}
	

}
