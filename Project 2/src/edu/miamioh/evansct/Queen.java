package edu.miamioh.evansct;

/**
 * This class was given in the lecture and is not my own code. The only thing I
 * added were the getters for the instance variables.
 * 
 * A queen in the nQueens problem.
 */
public class Queen{
	private int row, column;
	
	/**
	 * Constructs a queen at a given location.
	 * @param r the row
	 * @param c the column
	 */
	public Queen(int r, int c){
		row = r;
		column = c;
	}
	
	/**
	 * 
	 * THIS METHOD IS FROM CLASS AND IS NOT MY CODE
	 * Checks whether this queen attacks another.
	 * @param other the other queen
	 * @return true if this and the other queen are in the same row,
	 * 			column, or diagonal.
	 */
	public boolean attacks(Queen other){
		return row == other.row || column == other.column || 
				Math.abs(row - other.row) == Math.abs(column - other.column);
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	/**
	 * I added this code in so that I can test to see if one queen is equal to another
	 * so this is my own code.
	 * 
	 * @param q the queen being compared
	 * @return if they are the same or not
	 */
	public boolean equals(Queen q){
		if(q.getRow() == row && q.getColumn() == column){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Used for testing purposes, not my own code (given in class)
	 */
	public String toString(){
		return "" + "abcdefgh".charAt(column) + (row + 1);
	}
}