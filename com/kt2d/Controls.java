package com.kt2d;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Controls extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel movesLeft;
	public void setMovesLeftText(String movesLeft) { this.movesLeft.setText(movesLeft); }
	
	private JButton reset;
	public JButton getResetButton() { return this.reset; }

	public Controls(int moves) {
		movesLeft = new JLabel("Moves Left: " + moves);
		reset = new JButton("Reset");
		
		add(reset);
		add(movesLeft);
	}
}
