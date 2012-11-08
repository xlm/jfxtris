package data_types;

/**
 * DropType
 * @author Josh Chia
 * 
 * Simple enumeration encapsulating different kinds of drops
 * as well as their respective score value per a cell.
 *
 */
public enum DropType {
	// Set the value (argument) to be passed to the constructor 
	NORMAL (0), SOFT(1), HARD(2);
	
	private int score_value;
	
	/**
	 * Sets the score value of the drop  
	 * @param v, score value per cell
	 */
	DropType(int v) {
		score_value = v;
	}
	
	/**
	 * Score value per a cell for that drop
	 * @return, score_value
	 */
	public int getScoreValue() {
		return score_value;
	}
}