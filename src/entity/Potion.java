package entity;

import java.awt.Toolkit;

public class Potion extends Item{
	
	public String potionType;
	
	public Potion(String pType, int x, int y) {
		super(pType, x, y, 32, 32);
		potionType = pType;
		image = Toolkit.getDefaultToolkit().getImage("items/" + pType + "_Potion.png");
		setConsumable();
	}
	
//	public void equip() {
//		if(potionType.equals("Health")) {
//			equipped = true;
//			System.out.println("Health Potion Obtained");
//		}
//	}
}
