package model;

/**
 * Movable
 * @author Josh Chia
 * 
 * Methods all movable objects must implement
 * 
 */


public interface Movable {
	/**
	 * Moves the calling instance
	 * @param vx, movement along x-coordinate
	 * @param vy, movement along y-coordinate
	 */
	public void move(int vx, int vy);
}