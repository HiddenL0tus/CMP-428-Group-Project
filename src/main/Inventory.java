package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import entity.*;

public class Inventory {
	
	public Weapon[] weapons;
	public Item[] items;
	
	public Weapon selectedWeapon = null;
	
	final int slotSize = 64;
	final int inventoryW = slotSize * 4;
	final int inventoryH = slotSize * 1;
	final int halfInvWidth = inventoryW / 2;
	
	public int[] amount;
	
	public Inventory() {	
		//only allowed to carry 2 weapons and 2 items
		weapons = new Weapon[2];
		items   = new Item[2];
		amount  = new int[2];
	}
	
	/* This method returns false if the item was not able to be added to the inventory.
	 * The item's type is first checked so it can be added to the appropriate slot. */
	public boolean addItem(Item item) {
		if(item.isConsumable()) {
			for(int i = 0; i < items.length; i++) {
				if(items[i] == null) { 
					items[i] = item;
					amount[i]++;
					item.equip();
					return true;
				}
				if(items[i] != null && items[i].getSubType().equals(item.getSubType())) {
					if(item.stackable) {
						amount[i]++;
						item.equip();
						return true;
					}
				}
			}
		}
		else if(item.isWeapon()) {
			for(int i = 0; i < weapons.length; i++) {
				if(weapons[i] == null) { 
					weapons[i] = (Weapon)item;
					item.equip();
					System.out.println("Weapon Obtained");
					return true;
				}
			}
		}
		return false;
	}

	public void removeItem(int slot) { //sets the item to null, removing it from the inventory bar
		if(amount[slot] > 1) {
			amount[slot]--;
		}
		else { //when you have only one if that item
			amount[slot]--;
			items[slot] = null;
		}
	}
	
	public void setSelectedWeapon(Weapon weapon)
	{
		selectedWeapon = weapon;
	}

	
	public void draw(Graphics pen, GameS24 game) {
		
		pen.setColor(Color.BLACK);
		
		int slotSize = 64;
		int inventoryW = slotSize * 4;
		int inventoryH = slotSize * 1;
		//positioning inventory based on window size
		int inventoryX = (game.getSize().width / 2) - halfInvWidth;
		int inventoryY = (game.getSize().height) - (game.getSize().height / 7);
		pen.fillRoundRect(inventoryX, inventoryY, inventoryW, inventoryH, 10, 10); //draws inventory container
		
		pen.setColor(Color.WHITE);
		pen.drawRoundRect(inventoryX, inventoryY, inventoryW, inventoryH, 10, 10);
		
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
		
		
		//draw items with their respective amount
		Font num = new Font("SansSerif", Font.PLAIN, 12);
		pen.setFont(num);
		pen.setColor(Color.WHITE);
		if(items[0] != null) {
			pen.drawImage(items[0].image, item0SlotX, inventoryY, slotSize, slotSize, null);
			if(amount[0] > 0) {
				pen.drawString("" + amount[0], item1SlotX - 8, inventoryY + 12); //draw amount top left of item
			}
		}
		
		if(items[1] != null) {
			pen.drawImage(items[1].image, item1SlotX, inventoryY, slotSize, slotSize, null);
			if(amount[1] > 0) {
				pen.drawString("" + amount[1], (inventoryX + inventoryW) - 8, inventoryY + 12);
			}
		}	
		
		
		//keys for accessing items
		int kY = inventoryY + slotSize + 4;
		pen.setColor(Color.gray);
		pen.fillRoundRect((weapon0SlotX + slotSize/2) - 8, kY, 16, 16, 10, 10);
		pen.fillRoundRect((weapon1SlotX + slotSize/2) - 8, kY, 16, 16, 10, 10);
		pen.fillRoundRect((item0SlotX   + slotSize/2) - 8, kY, 16, 16, 10, 10);
		pen.fillRoundRect((item1SlotX   + slotSize/2) - 8, kY, 16, 16, 10, 10);
		
		pen.setColor(Color.BLACK); //outline 
		pen.drawRoundRect((weapon0SlotX + slotSize/2) - 8, kY, 16, 16, 10, 10);
		pen.drawRoundRect((weapon1SlotX + slotSize/2) - 8, kY, 16, 16, 10, 10);
		pen.drawRoundRect((item0SlotX   + slotSize/2) - 8, kY, 16, 16, 10, 10);
		pen.drawRoundRect((item1SlotX   + slotSize/2) - 8, kY, 16, 16, 10, 10);
		
		Font f = new Font("Arial", Font.BOLD, 16);
		pen.setFont(f);
		pen.drawString("1", (weapon0SlotX + slotSize/2) - 4, kY + 15);
		pen.drawString("2", (weapon1SlotX + slotSize/2) - 4, kY + 15);
		pen.drawString("Q", (item0SlotX   + slotSize/2) - 5, kY + 14);
		pen.drawString("E", (item1SlotX   + slotSize/2) - 5, kY + 14);
	}
}
