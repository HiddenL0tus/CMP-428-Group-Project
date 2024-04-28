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
}
