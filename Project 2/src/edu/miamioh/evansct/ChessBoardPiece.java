
package edu.miamioh.evansct;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 * @author Colin Evans
 * 2017/05/04
 * 
 * CSE 271, Section G
 * Professor: Dr. Angel D. Bravo-Salgado
 * TA: Rob Koch
 *
 * This class creates the Pieces that are added to the GUI so that it looks like a chess board.
 *
 */
public class ChessBoardPiece extends JPanel {

	private BufferedImage img;
	private boolean hasQueen;
	private int row, column;

	/**
	 * Constructor for the type of CBP that will be put on the actual GUI itself, the parameters 
	 * set everything up so that we can call it later on in order to put a picture of a queen on it
	 * as well as test to see if it would have an open spot that could be used for the tip button
	 * 
	 * @param c The color of the panel
	 * @param row the row number of the panel
	 * @param column the column number of the panel
	 */
	public ChessBoardPiece(Color c, int row, int column){
		super();
		this.row = row;
		this.column = column;
		super.setBackground(c);
		//a boolean variable that describes if that space has a queen or not
		hasQueen = false;
	}
	
	/**
	 * This creates a CBP that is used only for the tip button if there aren't any 
	 * available spaces left for the user to place a queen.
	 * 
	 * @param row the row of the object
	 * @param column the column of the object
	 */
	public ChessBoardPiece(int row, int column){
		this.row = row;
		this.column = column;
		hasQueen = false;
	}

	/**
	 * This returns the row number so that this object can be compared with another
	 * 
	 * @return the row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * This returns the row number so that this object can be compared with another
	 * 
	 * @return the column number
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * This sets the image in a space to that of the queen when the user clicks on a board space
	 * 
	 * @throws IOException if the image the program is looking for is not found in the file
	 */
	public void setImage() throws IOException{
		img = ImageIO.read(new File("Bravo.png"));
	}
	
	/**
	 * This sets the image in a space to that of the stop sign when two or more queens can attack each other
	 * 
	 * @throws IOException if the image the program is looking for is not found in the file
	 */
	public void setBadImage() throws IOException{
		img = ImageIO.read(new File("StopSign.png"));
	}
	
	/**
	 * This removes the image when a user clicks on a space again so that it is empty
	 * if they want to remove a queen
	 */
	public void removeImage(){		
		img = null;
	}
	
	/**
	 * This method overrides the paintComponent method for the JPanel class, which is a JComponent
	 * in order to have the image be visible when the user clicks on the space.
	 * 
	 * @param g the graphics that will become the image that is supposed to appear
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}
	
	/**
	 * this checks to see if the specific instance of the object has a queen on it or not
	 * 
	 * @return the boolean value saying if a space has a queen or not.
	 */
	public boolean isHasQueen() {
		return hasQueen;
	}

	/**
	 * This allows you to set if a piece has a queen on it or not. When the user clicks a space
	 * where the original value is false, the value will change because that spot will then have a queen on it.
	 * 
	 * @param hasQueen the boolean value that will be set on the hasQueen instance variable
	 */
	public void setHasQueen(boolean hasQueen) {
		this.hasQueen = hasQueen;
	}
	
	/**
	 * This turns the location of the CBP into it's string representation so that when the user hits the "tip"
	 * button they know exactly where to put the next piece.
	 * 
	 * This code is not my own, I used the toString() that was a part of the Queen class we received in class.
	 * 
	 * @return the string representation of the CBP
	 */
	@Override
	public String toString(){
		return "" + "abcdefgh".charAt(column) + (row + 1);
	}
}