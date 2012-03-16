package com.kt2d;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Tile {
	private Rectangle2D bounds;
	public Rectangle2D getBounds() { return this.bounds; }
	public void setBounds(Rectangle2D bounds) { this.bounds = bounds; }
	
	/* Specifies the state of the tile.
	 * 0 - Inactive
	 * 1 - Visited / Active
	 * 2 - Knight
	 * 3 - Valid Move
	 */
	private int state;
	public int getState() { return this.state; }
	/*
	 * Sets the state of the tile on the board.
	 * 0 - Inactive
	 * 1 - Visited / Active
	 * 2 - Knight
	 * 3 - Valid Move
	 * 
	 * @param state the value to set the current state to.
	 */
	public void setState(int state) { 
		this.state = state;
		this.tileColor = ((state == 1)?Color.RED:((state == 2)?Color.BLUE:((state == 3)?Color.GREEN:((state == 0)?Color.WHITE:Color.BLACK))));
	}
	
	private Color tileColor;
	public Color getColor() { return this.tileColor; }
	
	public Tile() {
		bounds = new Rectangle2D.Double(0,0,40,40);
		setState(0);
	}
	
	public Tile(Rectangle2D bounds) {
		this.bounds = bounds;
		setState(0);
	}
}
