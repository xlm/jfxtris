package game;

import model.Tetromino;
import data_types.DropType;

/**
 * DropGravity
 * 
 * Gravity that acts upon the active Tetromino in play.
 * Implemented according to the Tetris Guidelines for
 * soft and hard drops.
 * 
 * @author Josh Chia
 *
 */

public class DropGravity extends Gravity {
	private Tetromino affected;
	private DropType drop_type;
	private int current_frame;
	
	/**
	 * Construct drop gravity for a game of Tetris
	 * @param c, cells to drop
	 * @param f, frames (frequency) it drops
	 * @param g, game it is affecting
	 */
	public DropGravity(int c, int f, Game g) {
		super(c, f, g);
		drop_type = DropType.NORMAL;
		current_frame = frame;
	}
	
	/**
	 * Implements hard and soft-drops per Tetris Guidelines
	 * Hard: set to instantly drop (i.e. number of rows in a Playfield)
	 * Soft: 60 cells per second i.e. 1 cell per frame
	 */
	@Override
	protected void effect() {
		if (affected != null) {
			switch (drop_type) {
			case HARD:
				game.playfield.move(affected, 0, -game.playfield.rows);
				break;
			case SOFT:
				game.playfield.move(affected, 0, -1);
				break;
			case NORMAL:
			default:
				game.playfield.move(affected, 0, -cell);
				break;
			}
		}
	}
	
	/**
	 * Reconfigure gravity depending on selected type of drop
	 * @param d, drop type i.e. HARD, SOFT, NORMAL
	 */
	public final void setDropType(DropType d) {
		if (drop_type != d) {
			drop_type = d;
			frame_count = 0;
			switch (drop_type) {
			case HARD:
			case SOFT:
				current_frame = frame;
				frame = 1;
				break;
			case NORMAL:
			default:
				frame = current_frame;
				break;
			}
		}
	}
	
	/**
	 * Get the current drop type of Gravity
	 * @return drop_type
	 */
	public final DropType getDropType() {
		return drop_type;
	}
	
	/**
	 * Set the Tetromino that DropGravity affects
	 * @param t, Tetromino to apply gravity to
	 */
	public void set(Tetromino t) {
		affected = t;
	}
}
