package game;

import model.Mino;
import model.Piece;
import model.Tetromino;
import data_types.Vector2D;

/**
 * PieceDisplay
 * @author Josh Chia
 * 
 * A grid world for displaying Tetrominos.
 * Used for previewing the next Pieces or holding Tetrominos.
 * 
 * Unlike the Playfield, it has no rules for moving or manipulating
 * the Tetromino, it is only for display.
 *
 */
public class PieceDisplay extends GridCanvas {
	Tetromino tetromino;
	
	// Same as the superclass constructor
	protected PieceDisplay(Vector2D p, double w, double h, int r, int c) {
		super(p, w, h, r, c);
	}
	
	/**
	 * Set a Piece for display 
	 * @param p, piece to display
	 */
	public void setDisplay(Piece p) {
		if (tetromino != null) {
			for (Mino m: tetromino.minos) {
				this.getChildren().remove(m);
			}
		}
		tetromino = p.create(this);
	}
	
	/**
	 * Get size to draw individual grid width
	 * @return, pixel width of a individual grid
	 */
	@Override
	public double getGridWidth() {
		return width/cols;
	}

	/**
	 * Get size to draw individual grid height
	 * @return, pixel height of a individual grid
	 */
	@Override
	public double getGridHeight() {
		return height/rows;
	}

	/**
	 * The spawn rules for this grid world
	 */
	@Override
	public Vector2D getSpawnPos(Piece p) {
		switch(p) {
		case J:
		case L:
		case S:
		case Z:
		case T:
			return new Vector2D(1.5, 1);
		default:
			return new Vector2D(1, 1);
		}
	}
}
