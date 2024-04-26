package entity;

public class Player extends Entity
{
	private static String[] pose = {"LT", "RT","atkLT", "atkRT"};
	
	private static int[] count = {6,6,6,6};

	public Player(int x, int y)
	{
		super("Knight", pose, x, y, 38, 37, count, 10, 2);
	}
}
