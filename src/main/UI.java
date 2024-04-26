package main;

import java.awt.Color;
import java.awt.Graphics;

import engine.*;

public class UI {
	
	Graphics pen;
	GameS24 game;
	
	public UI(GameS24 game) {
		this.game = game;
	}
	
	public void drawInventory() {
		pen.setColor(Color.BLACK);
		
		//positioning inventory based on window size
		int height = game.getSize().height;
		pen.fillRect((game.getSize().width /2) - 250, height - Math.round(height / 6), 500, 70);
		
	}
	
	public void draw(Graphics pen) {
		this.pen = pen; //so we can use the pen object in other methods
		
		drawInventory();
	}
}
