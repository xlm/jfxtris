package game;

import model.Piece;
import data_types.Vector2D;
import javafx.scene.Group;

/**
 * PieceHold
 * @author Josh Chia
 *
 * Used for swapping out between two Tetrominos in a
 * Game of Tetris. Can only be used at most one time between locks
 * before it needs to be reset. 
 * 
 */
public class PieceHold extends Group {
	private boolean used;
	private double width, height;
	
	private Vector2D pos;
	private Piece held;
	private PieceDisplay display;
	
	private final int DISPLAY_ROWS = 4;
	private final int DISPLAY_COLS = 4;
	
	/**
	 * Create a grid world for holding Tetrominos
	 * @param p, position of the hold
	 * @param w, width in pixels
	 * @param h, height in pixels
	 */
	public PieceHold(Vector2D p, double w, double h) {
		used = false;
		pos = p;
		held = null;
		width = w;
		height = h;
		
		display = new PieceDisplay(new Vector2D(pos,0, 0), width, height, DISPLAY_ROWS, DISPLAY_COLS);
		this.getChildren().add(display);
	}
	
	/**
	 * Reset usage of the Hold
	 */
	public void resetUse() {
		used = false;
	}
	
	/**
	 * Check if Hold can be used
	 * @return, true if it usable
	 */
	public boolean canUse() {
		return !used;
	}
	
	/**
	 * Swaps the Piece in the Hold for another if available.
	 * @param p, Piece to be swapped
	 * @return, the Piece swapped out
	 */
	public Piece swap(Piece p) {
		Piece swapped_piece = (held==null) ? null: held;
		held = p;
		display.setDisplay(p);
		used = true;
		return swapped_piece;
	}
}
