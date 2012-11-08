package model;

import game.GridCanvas;

/**
 * TetrominoFactory
 * @author Josh Chia
 * 
 * A simple generic Factory interface for producing 
 * Tetrominos
 */

public interface TetrominoFactory<T> {
	/**
	 * Creates a Tetromino in a Grid Canvas
	 * @param gc, the grid canvas to place the Tetromino
	 * @return, Tetromino instance
	 */
	public Tetromino create(GridCanvas gc);
}