import java.awt.Graphics;
import java.awt.Image;

public class Item extends Rect{
	
	public Image image;
	public int x;
	public int y;
	public int w;
	public int h;
	
	public String name;
	
	public boolean equipped = false;
	
	public Item(String name, int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(image, x, y, w, h, null);
	}
	

}
