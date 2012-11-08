package game;

import model.Tetromino;

/**
 * LockDelay
 * @author Josh Chia
 * 
 * Implementation of Tetris Guideline locking mechanism for
 * Tetrominos. The lock delay is reset if the Tetromino is
 * moved downwards.
 *
 */

public class LockDelay {
	private final int DELAY;
	private int delay_count;
	private Tetromino target;
	
	/**
	 * Constructs a lock delay mechanism
	 * @param d, number of frames to delay
	 */
	public LockDelay(int d) {
		DELAY = d;
	}
	
	/**
	 * Sets the lock delay on a Tetromino
	 * @param t, to apply lock delay
	 */
	public void setTarget(Tetromino t) {
		target = t;
		delay_count = 0;
	}
	
	/**
	 * Sets the number of elapsed frames.
	 * Used for setting instant lock for hard drops 
	 * @param d
	 */
	public void setDelayCount(int d) {
		delay_count = (d > DELAY) ? DELAY: d;
	}
	
	/**
	 * Frame updates of locking mechanism, counting elapsed frames 
	 * if the target cannot be dropped.
	 * 
	 * Uses a boolean to keep the lock delay more cohesive instead
	 * of passing references to Game or Playfield. Useful for tracking
	 * whether to reset Piece Hold by the Game.
	 * 
	 * @param droppable, true if target can be dropped
	 */
	public void updateLock(boolean droppable) {
		if (target == null) {
			return;
		}
		
		// If can't be dropped, decrease the remaining delay
		if (!droppable) {
			if (delay_count < DELAY) {
				delay_count++;
			}
		}
		// If it can be dropped, reset the delay
		else if (delay_count != 0) {
			setDelayCount(0);
		}
	}
	
	/**
	 * Used to notify the caller (Game) to lock the piece
	 * @return, true if time to lock the target
	 */
	public boolean timeToLock() {
		return delay_count == DELAY;
	}
}
