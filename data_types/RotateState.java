package data_types;


/**
 * RotateState
 * @author Josh Chia
 *
 * Captures the different states of rotation that a Tetromino
 * can be in. Used as part of implementing wall kicks.
 * 
 * Different rotation states:
 *  ZERO:	original orientation
 *  RIGHT:	rotated right (clockwise 90 degrees from ZERO)
 *  TWO: 	rotated right or left twice (clockwise/anti-clockwise 180 degrees from ZERO)
 *  LEFT:	rotated left (ant-clockwise 90 degrees from ZERO)
 *  
 */

public enum RotateState {
	ZERO, RIGHT, TWO, LEFT;
	
	private final static int LEN = 4;

	/**
	 * Get the new RotateState after rotating
	 * @param r, direction to rotate
	 * @return, new RotationState
	 */
	public RotateState rotate(RotateDirection r) {
		switch (r) {
		case LEFT:
			return RotateState.values()[(this.ordinal()+3)%RotateState.LEN];
		case RIGHT:
			return RotateState.values()[(this.ordinal()+1)%RotateState.LEN];
		default:
			return this;
		}
	}
}
