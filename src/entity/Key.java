package entity;

import java.awt.Toolkit;

public class Key extends Item{
	
	public String keyType;
	
	public Key(String kType, int x, int y) {
		super(kType, x, y, 32, 32);
		keyType = kType;
		image = Toolkit.getDefaultToolkit().getImage("items/" + kType + "_Key.png");
	}
	
	public boolean isEquipped() {
		return equipped;
	}
	
}
