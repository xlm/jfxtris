package game;

import model.Mino;
import model.Piece;
import data_types.Vector2D;
import javafx.scene.Group;

/**
 * GridCanvas
 * @author Josh Chia
 * 
 * The shared properties of all the 'grid worlds' which
 * Minos can exist in.
 *
 */
public abstract class GridCanvas extends Group {
	protected Vector2D pos;
	protected double height, width;
	protected int rows, cols;
	
	/**
	 * Constructs a grid worl which Minos can exist in
	 * @param p, position vector
	 * @param w, width in pixels
	 * @param h, height in pixels
	 * @param r, number of rows
	 * @param c, number ofcols
	 */
	public GridCanvas(Vector2D p, double w, double h, int r, int c) {
		pos = p;
		width = w;
		height = h;
		rows = r;
		cols = c;
	}
	
	/**
	 * Get size to draw individual grid width
	 * @return, pixel width of a individual grid
	 */
	abstract public double getGridWidth();
	
	/**
	 * Get size to draw individual grid height
	 * @return, pixel height of a individual grid
	 */
	abstract public double getGridHeight();
	
	/**
	 * All grid world's must have their own specific rules for
	 * spawning a new Tetromino
	 * @param piece, to be spawned
	 * @return, position vector
	 */
	abstract public Vector2D getSpawnPos(Piece piece);
	
	/**
	 * Translate x-coordinate of position vector into
	 * real x-coordinate for drawing 
	 * @param p, position vector to translation
	 * @return, the draw x-coordinate     
	 */
	public final double getDrawX(Vector2D p) {
		return getGridWidth()*p.getAbsX() + pos.getAbsX();
	}
	
	/**
	 * Translate y-coordinate of the position vector into
	 * real y-coordinate for drawing 
	 * @param p, position vector to translation
	 * @return, the draw y-coordinate 
	 */
	public final double getDrawY(Vector2D p) {
		return height-getGridHeight()*(1+p.getAbsY()) + pos.getAbsY();
	}
	
	/**
	 * Determine if a Mino is visible in the 'grid world'
	 * @param m, Mino being set visible/invisible 
	 */
	public final void updateVisibility(Mino m) {
		if (m.getX() < pos.getAbsX() || m.getX() > pos.getAbsX()+width || m.getY() < pos.getAbsY() || m.getY() > pos.getAbsY()+height) {
			m.setVisible(false);
		}
		else {
			m.setVisible(true);
		}
	}
	
	/**
	 * Updates a Mino draw state and whether it can be drawn 
	 * @param m, Mino to update
	 */
	public final void updateDrawState(Mino m) {
		m.setX(getDrawX(m.getPos()));
		m.setY(getDrawY(m.getPos()));
		
		updateVisibility(m);
	}
}
