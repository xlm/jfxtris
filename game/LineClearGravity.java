package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Polyomino;

/**
 * LineClearGravity
 * @author Josh Chia
 * 
 * Gravity employed to plug the gaps created after a 
 * line is cleared.
 * 
 * Implemented as recursive gravity using flood fill.
 * Can be used as naive if cell value is set to 1
 */
public class LineClearGravity extends Gravity {
	private List<Polyomino> affected, cleanup;
	
	/**
	 * Construct LineClear gravity for a Game of Tetris
	 * @param c, cells
	 * @param f, frames 
	 * @param g, game gravity affects
	 */
	public LineClearGravity(int c, int f, Game g) {
		super(c, f, g);
		affected = new ArrayList<Polyomino>();
		cleanup = new ArrayList<Polyomino>();
	}
	
	/**
	 * Add Polyominos to be affected by this Gravity 
	 * @param ps, Polyominos to be affected
	 */
	public void addAll(Collection<Polyomino> ps) {
		affected.addAll(ps);
	}
	
	/**
	 * Continues to drop a Polyomino until it can no longer
	 * be dropped, at which point it locks it and removes it
	 * from being affected.
	 * 
	 * This implementation allows more robust gravity settings 
	 * to be used i.e. not just 20G, i.e. 1G, 2G.
	 * This enables some very nice visual gravity effects.
	 * Lower the frame setting, the most fluid the animation.
	 */
	@Override
	protected void effect() {
		for (Polyomino p: affected) {
			if (game.playfield.isDroppable(p)) {
				game.playfield.move(p, 0, -cell);
			}
			else {
				game.lock(p);
				cleanup.add(p);
			}
		}
		affected.removeAll(cleanup);
	}
	
	/**
	 * Check if gravity has no object to affected
	 * @return, true if empty
	 */
	public boolean isEmpty() {
		return affected.isEmpty();
	}
}