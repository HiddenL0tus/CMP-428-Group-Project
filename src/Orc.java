
public class Orc extends Sprite
{
	private static String[] poseMonster = {"LT", "RT"};
	
	private static int[] countMonster = {6,6};
	
	public Orc(int x, int y)
	{
		super("Orc", poseMonster, x, y, 38, 37, countMonster, 10, 10);
	}
}
