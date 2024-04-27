package entity;

public class Orc extends Entity
{
	private static String[] poseMonster = {"LT", "RT"};
	
	private static int[] countMonster = {6,6};
	
	public Orc(int x, int y)
	{
		super("enemies/Orc", poseMonster, x, y, 38, 37, countMonster, 4, 10);
	}
}
