package data_types;

/**
 * RotateDirection
 * @author Josh Chia
 * 
 * Different ways you can rotate a Tetromino.
 * Usage of enumeration allows simplification of Rotatable interface
 * and makes calculation of Wall Kicks simplier.
 *
 */
public enum RotateDirection {
	RIGHT, LEFT;
	
	/**
	 * Get the opposite rotate direction
	 * @return, opposite direction
	 */
	public final RotateDirection opposite() {
		return (this==RIGHT) ? LEFT: RIGHT;
	}
}
