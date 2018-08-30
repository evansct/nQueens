package edu.miamioh.evansct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
 * This class creates the GUI that becomes the game of the N-Queens. There are a couple listeners that 
 * make it all possible and several methods that go into creating all the components of each part of the 
 * board
 *
 */
public class NQueensBoard extends JFrame{

	private JPanel panel;
	private JButton check, tip;
	private ArrayList<Queen> queens = new ArrayList<Queen>();
	private int numQueens = 0;
	private ArrayList<ChessBoardPiece> listOfSquares = new ArrayList<ChessBoardPiece>();

	/**
	 * This is the constructor for the frame that holds the GUI and N-Queens game.
	 */
	public NQueensBoard(){
		super();
		setFrame(this);
		setButtons(this);
		setBoard();
		super.add(panel);
		super.setVisible(true);
	}

	/**
	 * This method sets up the board into a chess board layout and add the listeners to all the spots.
	 */
	public void setBoard(){
		panel = new JPanel();
		Color c = Color.CYAN;
		GridLayout grid = new GridLayout(8, 8);
		//sets the panel to a grid layout so that I can just create 64 objects and it places them exactly 
		//where they're supposed to go.
		panel.setLayout(grid);
		//loops for the rows
		for(int i = 0; i < 8; i++){
			//loops through the columns 
			for(int j = 0; j < 8; j++){
				ChessBoardPiece cbp = new ChessBoardPiece(c, i, j);
				MouseListener listener = new SpaceListener();
				cbp.addMouseListener(listener);
				listOfSquares.add(cbp);
				panel.add(cbp);
				//how it decides which color to place next
				if(c == Color.CYAN && j /7 != 1){
					c = Color.BLACK;
				}
				else if(c == Color.BLACK && j / 7 != 1){
					c = Color.CYAN;
				}
			}
		}
	}

	/**
	 * This sets up the basic aspects of the GUI so that it has a specific size and title,
	 * also so that it can't be resized at all.
	 * 
	 * @param q An NQueensBoard object that will be set to these settings
	 */
	public static void setFrame(NQueensBoard q){
		q.setSize(600, 600);
		q.setResizable(false);
		q.setTitle("nQueens Problem");
		q.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * This method sets up the buttons on the GUI so that we can have the check and tip button
	 * 
	 * @param q the object that will be set up with these buttons
	 */
	public void setButtons(NQueensBoard q){
		check = new JButton("Check");
		tip = new JButton("Tip");
		ActionListener listener = new ButtonListener();
		check.addActionListener(listener);
		tip.addActionListener(listener);
		JPanel panel = new JPanel();
		panel.add(check);
		panel.add(tip);
		q.add(panel, BorderLayout.SOUTH);
	}



	//This creates the mouse listener so that the program knows when a user clicks on a CBP
	public class SpaceListener implements MouseListener{

		/**
		 * This is what is called when one of the CBP's are clicked
		 */
		@Override
		public void mouseClicked(MouseEvent click) {
			if(click.getSource() instanceof ChessBoardPiece){
				//casts the source to a CBP
				ChessBoardPiece cbp = (ChessBoardPiece) click.getSource();
				Queen q = new Queen(cbp.getRow(), cbp.getColumn());
				//testing to see if there is already a queen on the space
				if(cbp.isHasQueen() == false){
					//only allowing 8 queens to be placed at a time
					if(numQueens < 8){
						try {
							cbp.setImage();
							cbp.repaint();
							cbp.setHasQueen(true);
							queens.add(q);
							numQueens++;

							//makes it so that the tip button goes away if there are 8 queens
							if(numQueens == 8){
								tip.setVisible(false);
							} 

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				//tests to see if there is already a queen on a spot and removes it if so
				else if(cbp.isHasQueen() == true){
					cbp.removeImage();
					cbp.repaint();
					cbp.setHasQueen(false);
					removeQueen(cbp, q);
					tip.setVisible(true);
				}
			}
		}

		//The following 4 methods aren't used but are a part of the interface
		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

		/**
		 * This method searches through the arraylist of queens and removes the one at the spot 
		 * where the user clicks
		 * 
		 * @param cbp a CBP that is having its queen removed
		 * @param q the queen being removed
		 */
		private void removeQueen(ChessBoardPiece cbp, Queen q){
			int row = cbp.getRow();
			int column = cbp.getColumn();
			int qRow = q.getRow();
			int qColumn = q.getColumn();
			if(row == qRow && column == qColumn){
				for(int i = 0; i < queens.size(); i++){
					if(queens.get(i).equals(q)){
						queens.remove(i);
						numQueens--;
					}
				}
			}
		}
	}
	
	public class ButtonListener implements ActionListener{

		private boolean qAttacks;

		/**
		 * The action performed method that comes with the ActionListener interface
		 * 
		 * @param click the click from the action occurring.
		 */
		@Override
		public void actionPerformed(ActionEvent click) {
			//tests to see if the source is the check button
			if(click.getSource().equals(check)){
				//sets the boolean variable to false
				qAttacks = false;
				//loops through the queen arraylist once to test if one queen attacks another
				for(int i = 0; i < queens.size(); i++){
					//loops through a second time to see if this one attacks the first
					for(int j = i + 1; j < queens.size(); j++){
						//if the queens do attack each other
						if(queens.get(i).attacks(queens.get(j))){
							qAttacks = true;
							//loops through all the board spaces to get which spaces have queens that can be attacked 
							for(int k = 0; k < listOfSquares.size(); k++){
								//compares the rows, columns, and diagonals of both queens with the board spaces to see
								//if they can be attacked and therefore set the attack image
								if(listOfSquares.get(k).getColumn() == queens.get(i).getColumn() &&
										listOfSquares.get(k).getRow() == queens.get(i).getRow() &&
										Math.abs(listOfSquares.get(k).getRow() - queens.get(i).getRow()) ==
										Math.abs(listOfSquares.get(k).getColumn() - queens.get(i).getColumn()) ||
										listOfSquares.get(k).getColumn() == queens.get(j).getColumn() &&
										listOfSquares.get(k).getRow() == queens.get(j).getRow() &&
										Math.abs(listOfSquares.get(k).getRow() - queens.get(j).getRow()) == 
										Math.abs(listOfSquares.get(k).getColumn() - queens.get(j).getColumn())){
									try {
										listOfSquares.get(k).setBadImage();
										listOfSquares.get(k).repaint();
									} catch (IOException e) {
										e.printStackTrace();
									} 
								} 
							}
						}
					}
				}
				//tells the user if what they have is good so far
				if(numQueens < 8 && qAttacks == false){
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "You're Safe for now...");
				} 
				//tells the user if they win
				else if(numQueens == 8 && qAttacks == false){
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Congrats! You win!!!");
				}
				//tells the user that they aren't done completely
				else {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Try again.");
				}
			}

			//if the source is the tip button
			if(click.getSource().equals(tip)){
				ChessBoardPiece cbp = findSafety();
				//if there aren't any open spaces on the board
				if(cbp.getRow() == 10 && cbp.getColumn() == 10){
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "There are no possible spots open.");
				} else {
					//will display the position to the user where they can place a queen
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, cbp.toString());

				}
			}
		}

		/**
		 * This loops through the list of CBPs and queens to see if a spot would be able to hold a queen or not.
		 * 
		 * @return the CBP that is open and available to have a queen.
		 */
		public ChessBoardPiece findSafety(){
			boolean attacksDiagonal = false;
			boolean upDown = false;
			boolean rightLeft = false;
			boolean safe;
			//if there aren't any queens yet, will always tell the user to place a queen in the first spot
			if(queens.size() == 0){
				ChessBoardPiece cbp = new ChessBoardPiece(0,0);
				return cbp;
			}
			//loops through the list of CBPs and sets all of the boolean variables 
			for(int i = 0; i < listOfSquares.size(); i++){
				safe = true;
				attacksDiagonal = false;
				upDown = false;
				rightLeft = false;
				int sColumn = listOfSquares.get(i).getColumn();
				int sRow = listOfSquares.get(i).getRow();
				//loops through the array of queens to see if it could attack a board space or not 
				for(int j = 0; j < queens.size(); j++){
					int qColumn = queens.get(j).getColumn();
					int qRow = queens.get(j).getRow();
					if(sColumn == qColumn){
						upDown = true;
					}
					if(sRow == qRow){
						rightLeft = true;
					}
					if(Math.abs(qRow - sRow) == Math.abs(qColumn - sColumn)){
						attacksDiagonal = true;
					}
				}
				//if a space isn't safe to put a queen sets safe to false
				if(upDown || rightLeft || attacksDiagonal){
					safe = false;
				}
				//if a space is safe to put a piece returns that space
				if(safe){
					return listOfSquares.get(i);
				}
			}
			//if no spaces are good to place a piece
			ChessBoardPiece fakeCBP = new ChessBoardPiece(10,10);
			return fakeCBP;
		}
	}

	//the main to call the NQueensBoard object to be created
	public static void main(String[] args) throws IOException {
		NQueensBoard n = new NQueensBoard();
	}
}