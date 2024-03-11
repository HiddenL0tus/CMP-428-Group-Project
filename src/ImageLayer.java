import java.awt.*;

public class ImageLayer {
	
	public Image image;

	int x;
	int y;
	
	int z;
	
	
	public ImageLayer(String filename, int x, int y, int z)
	{
		image = Toolkit.getDefaultToolkit().getImage(filename);
	
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public void draw(Graphics pen)
	{
		for(int i = 0; i < 10; i++)
		
			pen.drawImage(image, x + i * 1600 - Camera.x / z, y - Camera.y / z, 1600, 800, null);
	}

}
