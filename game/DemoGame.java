package game;

import model.Mino;
import data_types.Vector2D;

/**
 * DemoGame
 * @author Josh Chia
 * 
 * Specialization of Game class that constructs predefined Games of Tetris.
 * In this case it is used to demonstrate recursive gravity or wall kicks.  
 *
 */
public class DemoGame extends Game {

	/**
	 * Constructs a predefined Game of Tetris 
	 * @param x, x-coordinate position
	 * @param y, y-coordinate position
	 * @param width, in pixels
	 * @param height, in pixels
	 * @param map, which map (grid) to load
	 */
	public DemoGame(double x, double y, double width, double height, int map) {
		super(x, y, width, height, 20, 10, 2);
		
		// Preset level for demonstration of:
		switch (map) {
		// Wall Kicks
		case 2:
			playfield.grid[0][0] = new Mino(playfield, new Vector2D(0,0));
			playfield.grid[0][1] = new Mino(playfield, new Vector2D(1,0));
			playfield.grid[0][2] = new Mino(playfield, new Vector2D(2,0));
			playfield.grid[0][3] = new Mino(playfield, new Vector2D(3,0));
			playfield.grid[0][4] = new Mino(playfield, new Vector2D(4,0));
			playfield.grid[0][6] = new Mino(playfield, new Vector2D(6,0));
			playfield.grid[0][7] = new Mino(playfield, new Vector2D(7,0));
			playfield.grid[0][8] = new Mino(playfield, new Vector2D(8,0));
			playfield.grid[0][9] = new Mino(playfield, new Vector2D(9,0));
			
			playfield.grid[1][0] = new Mino(playfield, new Vector2D(0,1));
			playfield.grid[1][1] = new Mino(playfield, new Vector2D(1,1));
			playfield.grid[1][2] = new Mino(playfield, new Vector2D(2,1));
			playfield.grid[1][3] = new Mino(playfield, new Vector2D(3,1));
			playfield.grid[1][6] = new Mino(playfield, new Vector2D(6,1));
			playfield.grid[1][7] = new Mino(playfield, new Vector2D(7,1));
			playfield.grid[1][8] = new Mino(playfield, new Vector2D(8,1));
			playfield.grid[1][9] = new Mino(playfield, new Vector2D(9,1));
			
			playfield.grid[2][0] = new Mino(playfield, new Vector2D(0,2));
			playfield.grid[2][1] = new Mino(playfield, new Vector2D(1,2));
			playfield.grid[2][6] = new Mino(playfield, new Vector2D(6,2));
			playfield.grid[2][7] = new Mino(playfield, new Vector2D(7,2));
			playfield.grid[2][8] = new Mino(playfield, new Vector2D(8,2));
			playfield.grid[2][9] = new Mino(playfield, new Vector2D(9,2));
			
			playfield.grid[3][1] = new Mino(playfield, new Vector2D(1,3));
			playfield.grid[3][2] = new Mino(playfield, new Vector2D(2,3));
			playfield.grid[3][3] = new Mino(playfield, new Vector2D(3,3));
			playfield.grid[3][7] = new Mino(playfield, new Vector2D(7,3));
			playfield.grid[3][8] = new Mino(playfield, new Vector2D(8,3));
			playfield.grid[3][9] = new Mino(playfield, new Vector2D(9,3));
			
			playfield.grid[4][6] = new Mino(playfield, new Vector2D(6,4));
			playfield.grid[4][7] = new Mino(playfield, new Vector2D(7,4));
			playfield.grid[4][8] = new Mino(playfield, new Vector2D(8,4));
			playfield.grid[4][9] = new Mino(playfield, new Vector2D(9,4));
			
			playfield.grid[5][5] = new Mino(playfield, new Vector2D(5,5));
			playfield.grid[5][6] = new Mino(playfield, new Vector2D(6,5));
			playfield.grid[5][7] = new Mino(playfield, new Vector2D(7,5));
			
			playfield.grid[6][4] = new Mino(playfield, new Vector2D(4,6));
			playfield.grid[6][5] = new Mino(playfield, new Vector2D(5,6));
			break;
		// Recursive Gravity
		case 1:
		default:
			for (int r=0; r<7; r++) {
				playfield.grid[r][0] = new Mino(playfield, new Vector2D(0,r));
				playfield.grid[r][1] = new Mino(playfield, new Vector2D(1,r));
				playfield.grid[r][2] = new Mino(playfield, new Vector2D(2,r));
				playfield.grid[r][3] = new Mino(playfield, new Vector2D(3,r));
				playfield.grid[r][4] = new Mino(playfield, new Vector2D(4,r));
				playfield.grid[r][5] = new Mino(playfield, new Vector2D(5,r));
				playfield.grid[r][6] = new Mino(playfield, new Vector2D(6,r));
				playfield.grid[r][7] = new Mino(playfield, new Vector2D(7,r));
				playfield.grid[r][8] = new Mino(playfield, new Vector2D(8,r));
			}
			for (int r=7; r<12; r++) {
				playfield.grid[r][1] = new Mino(playfield, new Vector2D(1,r));
				playfield.grid[r][2] = new Mino(playfield, new Vector2D(2,r));
				playfield.grid[r][3] = new Mino(playfield, new Vector2D(3,r));
				playfield.grid[r][4] = new Mino(playfield, new Vector2D(4,r));
				playfield.grid[r][5] = new Mino(playfield, new Vector2D(5,r));
				playfield.grid[r][6] = new Mino(playfield, new Vector2D(6,r));
				playfield.grid[r][7] = new Mino(playfield, new Vector2D(7,r));
				playfield.grid[r][8] = new Mino(playfield, new Vector2D(8,r));
				playfield.grid[r][9] = new Mino(playfield, new Vector2D(9,r));
			}
			for (int r=12; r<13; r++) {
				playfield.grid[r][0] = new Mino(playfield, new Vector2D(0,r));
				playfield.grid[r][1] = new Mino(playfield, new Vector2D(1,r));
				playfield.grid[r][2] = new Mino(playfield, new Vector2D(2,r));
				playfield.grid[r][4] = new Mino(playfield, new Vector2D(4,r));
				playfield.grid[r][5] = new Mino(playfield, new Vector2D(5,r));
				playfield.grid[r][6] = new Mino(playfield, new Vector2D(6,r));
				playfield.grid[r][7] = new Mino(playfield, new Vector2D(7,r));
				playfield.grid[r][8] = new Mino(playfield, new Vector2D(8,r));
				playfield.grid[r][9] = new Mino(playfield, new Vector2D(9,r));
			}
			for (int r=13; r<20; r++) {
				playfield.grid[r][9] = new Mino(playfield, new Vector2D(9,r));
			}
			for (int r=13; r<18; r++) {
				playfield.grid[r][0] = new Mino(playfield, new Vector2D(0,r));
			}
			break;
		}
	}
}
