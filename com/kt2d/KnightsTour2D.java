package com.kt2d;

import java.applet.Applet;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

public class KnightsTour2D extends Applet implements ActionListener,MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Canvas canvas;
	private Controls controls;
	
	public void init() {
		// Set the background to gray.
		setBackground(new Color(0x999999));
		
		// Initialize the canvas.
		canvas = new Canvas();
		canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Initialize the control panel.
		controls = new Controls(canvas.getMovesLeft());
		controls.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Create a new GridBag layout.
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		// Create the constraints for the canvas.
		GridBagConstraints canvasConstraints = new GridBagConstraints();
		canvasConstraints.fill = GridBagConstraints.BOTH;
		canvasConstraints.gridx = GridBagConstraints.HORIZONTAL;
		canvasConstraints.gridy = 0;
		canvasConstraints.weightx = 1.0;
		canvasConstraints.weighty = 10.0;
		gridbag.setConstraints(canvas, canvasConstraints);
		add(canvas);
		
		// Create the constraints for the control panel.
		GridBagConstraints controlConstraints = new GridBagConstraints();
		controlConstraints.fill = GridBagConstraints.BOTH;
		controlConstraints.gridx = GridBagConstraints.HORIZONTAL;
		controlConstraints.gridy = 1;
		controlConstraints.weightx = 1.0;
		controlConstraints.weighty = 0.5;
		controlConstraints.insets = new Insets(5,0,0,0);
		gridbag.setConstraints(controls, controlConstraints);
		add(controls);
		
		controls.getResetButton().addActionListener(this);
		
		// Set the size of the Applet and make it visible.
		setSize(320,380);
		setVisible(true);
		addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Reset")) {
			canvas.reset();
			controls.setMovesLeftText("Moves Left: " + canvas.getMovesLeft());
		}
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		
		// Check each tile to see if it was clicked.
		for(int row = 0;row < canvas.getBoardSize();row++) {
			for(int col = 0;col < canvas.getBoardSize();col++) {
				Tile current = canvas.getTiles()[row][col]; // Get the current tile.
				if((current.getBounds().contains(mouseX, mouseY))&&(current.getState() == 3)) { // If that tile was clicked...
					moveKnight(row, col, current); // Move the Knight to the current tile.
				}
			}
		}
		
		canvas.repaint();
	}
	
	public void moveKnight(int row, int col, Tile current) {
		canvas.clearValid(); // Clear the currently valid moves so that the user can't make invalid moves.
		current.setState(2); // Set the state of the tile that was clicked to 2 (Knight)
		canvas.getTiles()[canvas.getKnightY()][canvas.getKnightX()].setState(1); // Set the state of the previous Knight tile to used.
		canvas.setKnightX(col); // Set the new KnightX position to the current column.
		canvas.setKnightY(row); // Set the new KnightY position to the current row.
		canvas.markValid(); // Mark the new valid moves.
		controls.setMovesLeftText("Moves Left: " + canvas.getMovesLeft()); // Update the moves made label.
		checkGame(); // Check to see if the game is over.
	}
	
	public void checkGame() {
		int gameOver = canvas.isGameOver();
		
		if(gameOver == 1) { // If the player is out of moves and there are empty spaces left, the player has lost.
			JOptionPane.showMessageDialog(this, "Game Over! You lose!");
		} else if(gameOver == 2) { // If there are no more empty spaces, the player wins.
			JOptionPane.showMessageDialog(this, "Congratulations! You won!");
		}
	}

	@Override
	public void mouseEntered(MouseEvent m) {
	}
	@Override
	public void mouseExited(MouseEvent m) {
	}
	@Override
	public void mousePressed(MouseEvent m) {
	}
	@Override
	public void mouseReleased(MouseEvent m) {
	}
}
