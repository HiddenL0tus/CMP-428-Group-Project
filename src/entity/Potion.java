package entity;

import java.awt.Toolkit;

public class Potion extends Item{
	
	public String potionType;
	
	public Potion(String pType, int x, int y) {
		super(pType, x, y, 32, 32);
		potionType = pType;
		image = Toolkit.getDefaultToolkit().getImage("items/" + pType + "_Potion.png");
		stackable = true;
		setConsumable();
		setSubType(potionType);
	}
	

}
