package model;

/**
 * Polyomino
 * @author Josh Chia
 * 
 * An abstract grouping of Minos, which may or may not be adjacent 
 * to each other. Although Minos of a Polyomino move as a group in 
 * unison, the position of these Minos is thus absolute 
 * (not relative to each other).  
 *
 */

public class Polyomino implements Movable {
	public Mino[] minos;
	
	/**
	 * Construct a Polyomino
	 * @param ms, Minos that compose a Polyomino
	 */
	public Polyomino(Mino[] ms) {
		minos = ms;
	}
	
	/**
	 * Moves the Polyomino
	 * @vx, amount to move along x-axis
	 * @vy, amount to move along y-axis
	 */
	@Override
	public void move(int vx, int vy) {
		for(Movable m: minos) {
			m.move(vx, vy);
		}
	}
	
	/**
	 * Sets the visibility of a Polyomino
	 * @param b, visible if true
	 */
	public void setVisible(boolean b) {
		for (Mino m: minos) {
			m.setVisible(b);
		}
	}
}