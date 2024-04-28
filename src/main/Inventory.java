package main;

import java.awt.Color;

import java.awt.Graphics;
import entity.*;

public class Inventory {
	
	public Item[] weapons;
	public Item[] items;
	
	public Item selectedWeapon = null;
	
	final int slotSize = 64;
	final int inventoryW = slotSize * 4;
	final int inventoryH = slotSize * 1;
	final int halfInvWidth = inventoryW / 2;
	
	public Inventory() {	
		//only allowed to carry 2 weapons and 2 items
		weapons = new Item[2];
		items   = new Item[2];
	}
	
	/* This method returns false if the item was not able to be added to the inventory.
	 * The item's type is first checked so it can be added to the appropriate slot. */
	public boolean addItem(Item item) {
		if(item.isConsumable()) {
			for(int i = 0; i < items.length; i++) {
				if(items[i] == null) { 
					items[i] = item;
					item.equip();
					System.out.println("Item Obtained");
					return true;
				}
			}
		}
		else if(item.isWeapon()) {
			for(int i = 0; i < weapons.length; i++) {
				if(weapons[i] == null) { 
					weapons[i] = item;
					item.equip();
					System.out.println("Weapon Obtained");
					return true;
				}
			}
		}
		return false;
	}
	
	public void setSelectedWeapon(Item item)
	{
		if (item.isWeapon()) selectedWeapon = item;
	}
	
	public void draw(Graphics pen, GameS24 game) {
		
		pen.setColor(Color.BLACK);
		
		int slotSize = 64;
		int inventoryW = slotSize * 4;
		int inventoryH = slotSize * 1;
		//positioning inventory based on window size
		int inventoryX = (game.getSize().width / 2) - halfInvWidth;
		int inventoryY = (game.getSize().height) - (game.getSize().height / 7);
		pen.fillRect(inventoryX, inventoryY, inventoryW, inventoryH); //draws inventory container
		
		//drawing items
		final int weaponSlotStartX = inventoryX;
		final int weapon0SlotX = weaponSlotStartX;
		final int weapon1SlotX = weaponSlotStartX + slotSize;
		
		final int itemSlotStartX = inventoryX + halfInvWidth;
		final int item0SlotX = itemSlotStartX;
		final int item1SlotX = itemSlotStartX + slotSize;
		
		if(weapons[0] != null) {
			pen.drawImage(weapons[0].image, weapon0SlotX, inventoryY, slotSize, slotSize, null);
		}
		
		if(weapons[1] != null) {
			pen.drawImage(weapons[1].image, weapon1SlotX, inventoryY, slotSize, slotSize, null);
		}
		
		if(items[0] != null) {
			pen.drawImage(items[0].image, item0SlotX, inventoryY, slotSize, slotSize, null);
		}
		
		if(items[1] != null) {
			pen.drawImage(items[1].image, item1SlotX, inventoryY, slotSize, slotSize, null);
		}	
	}
}
