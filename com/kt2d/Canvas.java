package com.kt2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Canvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int PWIDTH = 320;
	private static final int PHEIGHT = 320;
	private static final int boardSize = 8;
	public int getBoardSize() { return Canvas.boardSize; }
	
	private Tile [][] tiles;
	public Tile[][] getTiles() { return this.tiles; }
	
	private int knightX;
	public int getKnightX() { return this.knightX; }
	public void setKnightX(int knightX) { this.knightX = knightX; }
	private int knightY;
	public int getKnightY() { return this.knightY; }
	public void setKnightY(int knightY) { this.knightY = knightY; } 
	
	private int movesLeft;
	public int getMovesLeft() { return this.movesLeft; }
	public void setMovesLeft(int movesLeft) { this.movesLeft = movesLeft; }

	public Canvas() {
		// Initialize the tiles grid.
		tiles = new Tile[boardSize][boardSize];
		
		// Initialize each individual tile, and set its location.
		for(int row = 0;row < boardSize;row++) {
			for(int col = 0;col < boardSize;col++) {
				tiles[row][col] = new Tile(new Rectangle2D.Double((col * (PWIDTH / boardSize)), // The X position on the grid.
																	(row * (PHEIGHT / boardSize)), // The Y position on the grid.
																	(PWIDTH / boardSize), // The width of the tile.
																	(PHEIGHT / boardSize))); // The height of the tile.
			}
		}
		
		// Place the Knight at the approximate middle of the board.
		try {
			knightX = ((boardSize / 2) - 1);
			knightY = ((boardSize / 2) - 1);
			
			tiles[knightY][knightX].setState(2);
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		movesLeft = 0;
		
		markValid();
		
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		// Draw each tile to the screen with the appropriate color.
		for(int row = 0;row < boardSize;row++) {
			for(int col = 0;col < boardSize;col++) {
				Tile current = tiles[row][col];
				g2d.setPaint(current.getColor());
				g2d.fill(current.getBounds());
			}
		}
		
		// Draw the vertical grid lines.
		g2d.setPaint(Color.BLACK);
		for(int col = 1;col < boardSize;col++) {
			int x1, x2;
			x1 = x2 = (col * (PWIDTH / boardSize));
			int y1 = 0;
			int y2 = PHEIGHT;
			g2d.drawLine(x1, y1, x2, y2);
		}
		
		// Draw the horizontal grid lines.
		g2d.setPaint(Color.BLACK);
		for(int row = 1;row < boardSize;row++) {
			int x1 = 0;
			int x2 = PWIDTH;
			int y1, y2;
			y1 = y2 = (row * (PHEIGHT / boardSize));
			g2d.drawLine(x1, y1, x2, y2);
		}
	}
	
	/*
	 * Returns 0 if the game is not over.
	 * Returns 1 if the player has lost the game.
	 * Returns 2 if the player has won.
	 */
	public int isGameOver() {
		int emptyTiles = 0;
		
		// Count how many inactive tiles are left.
		for(int row = 0;row < boardSize;row++) {
			for(int col = 0;col < boardSize;col++) {
				Tile current = tiles[row][col];
				emptyTiles += ((current.getState() == 0)?1:0);
			}
		}
		
		/*
		 * Return 0 (not over) if there is 1 or more empty tiles and the number of valid moves is greater than 0.
		 * Return 1 (game over, lose) if there are 1 or more empty tiles and the number of valid moves is 0.
		 * Return 2 (game over, win) if there are no more empty tiles.
		 * Returns 3 if none of these conditions are met. (should never happen)
		 */
		return (((emptyTiles > 0)&&(movesLeft > 0))?0:(((emptyTiles > 0)&&(movesLeft == 0))?1:((emptyTiles == 0)?2:3)));
	}
	
	public void markValid() {
		// Check the Top-Left (-2,-1)
		if(((!((knightY - 2) < 0))&&(!((knightX - 1) < 0)))&&(tiles[knightY - 2][knightX - 1].getState() != 1)) {
			tiles[knightY - 2][knightX - 1].setState(3);
			movesLeft++;
		}
		// Check the Top-Right (-2,+1)
		if(((!((knightY - 2) < 0))&&(!((knightX + 1) > (boardSize - 1))))&&(tiles[knightY - 2][knightX + 1].getState() != 1)) {
			tiles[knightY - 2][knightX + 1].setState(3);
			movesLeft++;
		}
		// Check the Right-Top (-1,+2)
		if(((!((knightY - 1) < 0))&&(!((knightX + 2) > (boardSize - 1))))&&(tiles[knightY - 1][knightX + 2].getState() != 1)) {
			tiles[knightY - 1][knightX + 2].setState(3);
			movesLeft++;
		}
		// Check the Right-Bottom (+1,+2)
		if(((!((knightY + 1) > (boardSize - 1)))&&(!((knightX + 2) > (boardSize - 1))))&&(tiles[knightY + 1][knightX + 2].getState() != 1)) {
			tiles[knightY + 1][knightX + 2].setState(3);
			movesLeft++;
		}
		// Check the Bottom-Right (+2,+1)
		if(((!((knightY + 2) > (boardSize - 1)))&&(!((knightX + 1) > (boardSize - 1))))&&(tiles[knightY + 2][knightX + 1].getState() != 1)) {
			tiles[knightY + 2][knightX + 1].setState(3);
			movesLeft++;
		}
		// Check the Bottom-Left (+2,-1)
		if(((!((knightY + 2) > (boardSize - 1)))&&(!((knightX - 1) < 0)))&&(tiles[knightY + 2][knightX - 1].getState() != 1)) {
			tiles[knightY + 2][knightX - 1].setState(3);
			movesLeft++;
		}
		// Check the Left-Bottom (-1,-2)
		if(((!((knightY - 1) < 0))&&(!((knightX - 2) < 0)))&&(tiles[knightY - 1][knightX - 2].getState() != 1)) {
			tiles[knightY - 1][knightX - 2].setState(3);
			movesLeft++;
		}
		// Check the Left-Top (+1,-2)
		if(((!((knightY + 1) > (boardSize - 1)))&&(!((knightX - 2) < 0)))&&(tiles[knightY + 1][knightX - 2].getState() != 1)) {
			tiles[knightY + 1][knightX - 2].setState(3);
			movesLeft++;
		}
	}
	
	public void clearValid() {
		// Go through each tile. If a tile is set to state 3, clear it.
		for(int row = 0;row < boardSize;row++) {
			for(int col = 0;col < boardSize;col++) {
				Tile current = tiles[row][col];
				if(current.getState() == 3) current.setState(0);
			}
		}
		
		movesLeft = 0;
	}
	
	public void reset() {
		// Reset each tile to the inactive state.
		for(int row = 0;row < boardSize;row++) {
			for(int col = 0;col < boardSize;col++) {
				Tile current = tiles[row][col];
				current.setState(0);
			}
		}
		
		// Reset the Knight's position to its original coordinates.
		try {
			knightX = ((boardSize / 2) - 1);
			knightY = ((boardSize / 2) - 1);
			
			tiles[knightY][knightX].setState(2);
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		movesLeft = 0;
		
		markValid();
		
		repaint();
	}
}
