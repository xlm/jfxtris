package model;

import data_types.RotateDirection;

/**
 * Rotatable
 * @author Josh Chia
 * 
 * Methods that all Rotatable entities must implement.
 * 
 */

public interface Rotatable {
	
	/**
	 * Rotate a Tetromino in either direction
	 * @param r, direction to rotate
	 */
	public void rotate(RotateDirection r);
	
}