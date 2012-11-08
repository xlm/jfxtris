package model;

import game.GridCanvas;

import java.util.ArrayList;
import java.util.List;

import data_types.RotateDirection;
import data_types.Vector2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * Playfield
 * @author Josh Chia
 * 
 * The grid world in which Tetrominos are spawned and the game is played
 * 
 * The grid 2D-array is used to track which grids are free or otherwise 
 * occupied by locked Mino. Polyominos and Tetrominos are not locked 
 * therefore they aren't on the grid (must be locked first). 
 *
 */

public class Playfield extends GridCanvas {
	public Tetromino active_piece;
	public Mino[][] grid;
	
	private int vanish;
	private int spawn_pos_x;
	private int spawn_pos_y;
	
	public List<Polyomino> polyominos = new ArrayList<Polyomino>();
	
	/**
	 * Constructs a Playfield
	 * @param p, position to draw the Playfield
	 * @param w, draw width (pixels) of the Playfield 
	 * @param h, draw height (pixels) of the Playfield
	 * @param r, number of rows
	 * @param c, number of columns
	 * @param v, size of vanish zone
	 */
	public Playfield(Vector2D p, double w, double h, int r, int c, int v) {
		super(p, w, h, r, c);
		vanish = v;
		grid = new Mino[r][c];
		
		spawn_pos_x = cols/2-1;
		spawn_pos_y = rows-vanish;
		
		createBackground();
	}
	
	/**
	 * Spawns a Tetromino onto the Playfield
	 * @param t, Tetromino to spawn
	 */
	public void spawn(Tetromino t) {
		active_piece = t;
	}
	
	/**
	 * Checks if a Tetromino can be spawned.
	 * Used for game over conditions .
	 * @param p, Piece to spawn
	 * @return, true if it can be spawned
	 */
	public boolean canSpawn(Piece p) {
		Tetromino t = p.create(this);
		return isLegalPosition(t) ? true: false;
	}
	
	/**
	 * Checks if Playfield needs to spawn a new Tetromino
	 * @return, true if a new Tetromino needs to be spawned
	 */
	public boolean needSpawn() {
		return active_piece == null;
	}
	
	/**
	 * Moves a Polyomino according to the rules and conditions
	 * of the Playfield's state 
	 * @param p, Polyomino to move
	 * @param vx, amount to move along x-axis
	 * @param vy, amount to move along y-axis
	 */
	public void move(Polyomino p, int vx, int vy) {
		// Vectors are zero, nothing to do
		if (vx==0 && vy==0) {
			return;
		}
		
		// Calculate the unit vectors i.e. +/-1
		int dx = (int) Math.signum(vx);		
		int dy = (int) Math.signum(vy);
		
		// Move the Polyomino incrementally (recursive call)
		if (isLegalMove(p, dx, dy)) {
			p.move(dx, dy);
			move(p, vx-dx, vy-dy);
		}
	}
	
	public boolean isDroppable(Polyomino p) {
		return isLegalMove(p, 0, -1);
	}
	
	/**
	 * Rotates a Tetromino according to the state of the Playfield
	 * and the Wall Kick translations.
	 * @param t, Tetromino to rotate
	 * @param d, direction being rotated
	 */
	public void rotate(Tetromino t, RotateDirection d) {
		// Get Wall Kick translations
		Vector2D[] wall_kick_tests = Piece.getWallKickTranslations(t, d);
		
		// Check if any of the translations lead to valid positions
		for (Vector2D v: wall_kick_tests) {
			t.move((int)v.getAbsX(), (int)v.getAbsY());
			t.rotate(d);
			if (isLegalPosition(t)) {
				break;
			}
			else {
				t.rotate(d.opposite());
				t.move((int)-v.getAbsX(), (int)-v.getAbsY());
			}
		}
	}
	
	/**
	 * Clears a row in the Playfield 
	 * @param r, row to clear
	 */
	public void clearLine(int r) {
		for (Mino m: grid[r]) {
			this.getChildren().remove(m);
		}
		grid[r] = new Mino[cols];
	}
	
	/**
	 * Check if a row has formed a Line Clear
	 * @param r, row to check
	 * @return, true if it is a Line Clear
	 */
	public boolean isLine(int r) {
		for (Mino m: grid[r]) {
			if (m==null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add Polyomino to the Playfield's grid
	 * @param p, Polyomino to add
	 */
	public void addToGrid(Polyomino p) {
		for (Mino m: p.minos) {
			grid[(int) m.pos.getAbsY()][(int) m.pos.getAbsX()] = m;
		}
	}
	
	/**
	 * Remove Polyomino from the Playfield's grid
	 * @param p, Polyomino to remove
	 */
	public void removeFromGrid(Polyomino p) {
		for (Mino m: p.minos) {
			grid[(int) m.pos.getAbsY()][(int) m.pos.getAbsX()] = null;
		}
	}
	
	/**
	 * Remove a Polyomino visually only.
	 * This is useful for swapping Tetrominos for Piece Hold since
	 * they are not on the grid. 
	 * @param p
	 */
	public void removeFromGroup(Polyomino p) {
		for (Mino m: p.minos) {
			this.getChildren().removeAll(m);
		}
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
		return height/(rows-vanish);
	}
	
	/**
	 * Check if Polyomino occupies a legal position
	 * @param p, Polyomino to check
	 * @return, true if legal
	 */
	private boolean isLegalPosition(Polyomino p) {
		return isLegalMove(p, 0, 0);
	}
	
	/**
	 * Check if Polyomino can be legally moved
	 * @param p, Polyomino to check
	 * @param vx, amount to move along x-axis
	 * @param vy, amount to move along y-axis
	 * @return, true if legal
	 */
	public boolean isLegalMove(Polyomino p, int vx, int vy) {
		int new_pos_x;
		int new_pos_y;
		
		for (Mino m: p.minos) {
			new_pos_x = (int) (m.pos.getAbsX() + vx);
			new_pos_y = (int) (m.pos.getAbsY() + vy);
			
			if (new_pos_x < 0 || new_pos_x >= cols || new_pos_y < 0  || new_pos_y >= rows) {
				return false;
			}
			
			if (grid[new_pos_y][new_pos_x] != null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the spawn position for a Piece
	 * @param p, piece to get spawn position
	 */
	@Override
	public Vector2D getSpawnPos(Piece p) {
		switch (p) {
		default:
			return new Vector2D(spawn_pos_x, spawn_pos_y);
		}
	}
	
	/**
	 * Create a background image for the Playfield
	 */
	private void createBackground() {
		Rectangle background = new Rectangle(pos.getAbsX(), pos.getAbsY(), width, height);
		background.setFill(Color.BLACK);
		background.setStrokeWidth(5);
		background.setStroke(Color.DARKGREY);
		this.getChildren().add(background);

		for (int i=0; i<cols; i++) {
			Line l = new Line(pos.getAbsX()+getGridWidth()*i, pos.getAbsY(), pos.getAbsX()+getGridWidth()*i, pos.getAbsY()+height);
			l.setStroke(Color.DARKGREY);
			this.getChildren().add(l);
		}

		for (int i=0; i<rows-1; i++) {
			Line l = new Line(pos.getAbsX(), pos.getAbsY()+getGridHeight()*i, pos.getAbsX()+width, pos.getAbsY()+getGridHeight()*i);
			l.setStroke(Color.DARKGREY);
			this.getChildren().add(l);
		}
	}
	
	/**
	 * Get the current state of a Playfield's grid, thus it needs to add any 
	 * Tetrominos or Polyominos.
	 * Used for debugging, console printing or if following strict MVC.
	 * @return, grid of Minoes
	 */
	public Mino[][] getDrawGrid() {
		// Create a deep copy of the grid
		Mino[][] draw_grid = new Mino[rows][cols]; 
		for (int r=0; r<rows; r++) {
			for (int c=0; c<cols; c++) {
				draw_grid[r][c] = grid[r][c];
			}
		}
		// Add Tetromino (active piece) for drawing
		if (active_piece != null) {
			for (Mino m: active_piece.minos) {
				draw_grid[(int)(active_piece.getPos().getY() + m.pos.getY())][(int)(active_piece.getPos().getX() + m.pos.getX())] = m; 
			}
		}
		
		// Add Polyominos
		for (Polyomino p: polyominos) {
			for (Mino m: p.minos) {
				draw_grid[(int) m.pos.getY()][(int) m.pos.getX()] = m;
			}
		}
		return draw_grid;
	}
	
	/**
	 * Console draw a Playfield's grid
	 * @param grid, grid to draw
	 */
	public static void consoleDraw(Mino[][] grid) {
		for (int row=grid.length-1; row>=0; row--) {
			for (int col=0; col<grid[row].length; col++) {
				if (grid[row][col] != null) {
					System.out.print("*");
				}
				else {
					System.out.print("-");
				}
			}
			System.out.print("\n");
		}
	}
}