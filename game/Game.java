package game;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Mino;
import model.Piece;
import model.Playfield;
import model.Polyomino;
import model.RandomGenerator;
import model.Tetromino;

import data_types.DropType;
import data_types.RotateDirection;
import data_types.Vector2D;

import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Game
 * @author Josh Chia
 * 
 * Encapsulates all the entities and variables that represent the state
 * of the Game of Tetris
 * 
 * Used to abstract away the underlying details of how the game runs and
 * provide a safe interface for controlling the game
 *
 */
public class Game extends Group {
	// Preset Game Constants
	private final int PREVIEW_SIZE = 5;
	private final int INITIAL_LEVEL = 1;
	private final int DELAY_FRAMES = 60; // Guideline maximum
	private final int INITIAL_DROP_GRAVITY_CELLS = 1; // Default initial gravity
	private final int INITIAL_DROP_GRAVITY_FRAMES = 48;
	private final double DROP_GRAVITY_GROWTH_FACTOR = 1.0; // i.e. increases at 100%
	private final int CLEAR_GRAVITY_CELLS = 1; 
	private final int CLEAR_GRAVITY_FRAMES = 1; // lower frames, more responsive animation
	
	/*
	 * Preset UI Constants
	 * Used to make Games scalable for different screen resolutions
	 * 
	 * Using relative factors allows the game to support some variation from
	 * default resolution.
	 * 
	 * If there is more time, would be better to implement an abstraction
	 * for layouts. 
	 */
	private final double PLAYFIELD_RELATIVE_OFFSET_X = 0.25;
	private final double PLAYFIELD_RELATIVE_OFFSET_Y = 0.10;
	private final double PLAYFIELD_RELATIVE_WIDTH = 0.50;
	private final double PLAYFIELD_RELATIVE_HEIGHT = 0.85;
	private final double PIECE_DISPLAY_RELATIVE_WIDTH = 0.2;
	private final double PIECE_DISPLAY_RELATIVE_HEIGHT = 0.17;
	
	// Constants determined by Game parameters
	public final double PLAYFIELD_POS_X, PLAYFIELD_POS_Y, PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT;
	public final double PIECE_DISPLAY_WIDTH, PIECE_DISPLAY_HEIGHT;
	private final int PLAYFIELD_ROWS, PLAYFIELD_COLS, PLAYFIELD_VANISH;
	private final Vector2D PLAYFIELD_POS;
	private final Font GAME_FONT;
	private final double PADDING;
	
	// Game Objects (that compose a Game of Tetris)
	protected Playfield playfield;
	private DropGravity drop_gravity;
	private LineClearGravity recursive_gravity;
	private RandomGenerator<Piece> rand_gen;
	private LockDelay lock_delay;
	private ScoreSystem score;
	private PiecePreview piece_preview;
	private PieceHold piece_hold;
	
	// JavaFX UI
	protected Text score_text, level_text, goal_text, next_text, hold_text, feedback_text;
	
	// Game Variables - used to cache lines to clear (reduce search time)
	private Set<Integer> rows_to_clear = new HashSet<Integer>();

	/**
	 * Sets up a game of Tetris
	 * @param x, x-coordinate to display the game
	 * @param y, y-coordinate to display the game
	 * @param width, in pixels
	 * @param height, in pixels
	 * @param rows, number of rows
	 * @param cols, number of cols
	 * @param vanish, size of vanish zone
	 */
	public Game(double x, double y, double width, double height, int rows, int cols, int vanish) {
		// Set/Determine Constants for Playfield
		PLAYFIELD_POS_X = x + width * PLAYFIELD_RELATIVE_OFFSET_X;
		PLAYFIELD_POS_Y = y + height * PLAYFIELD_RELATIVE_OFFSET_Y;
		PLAYFIELD_POS = new Vector2D(PLAYFIELD_POS_X, PLAYFIELD_POS_Y);
		PLAYFIELD_WIDTH = width * PLAYFIELD_RELATIVE_WIDTH;
		PLAYFIELD_HEIGHT = height * PLAYFIELD_RELATIVE_HEIGHT;
		PLAYFIELD_ROWS = rows;
		PLAYFIELD_COLS = cols;
		PLAYFIELD_VANISH = vanish;
		
		// Set/Determine Constants for PieceDisplay
		PIECE_DISPLAY_WIDTH = PIECE_DISPLAY_RELATIVE_WIDTH * width;
		PIECE_DISPLAY_HEIGHT = PIECE_DISPLAY_RELATIVE_HEIGHT * height;
		
		// Style Setup
		GAME_FONT = Font.font("Tahoma",  FontWeight.NORMAL, height*0.03);
		PADDING = height * 0.02;
		
		// Game Setup ("Gridworlds")
		playfield = new Playfield(PLAYFIELD_POS, PLAYFIELD_WIDTH, PLAYFIELD_HEIGHT, PLAYFIELD_ROWS, PLAYFIELD_COLS, PLAYFIELD_VANISH);
		piece_preview = new PiecePreview(new Vector2D(PLAYFIELD_POS, PLAYFIELD_WIDTH+PADDING, 0), PREVIEW_SIZE, PIECE_DISPLAY_WIDTH, PIECE_DISPLAY_HEIGHT);
		piece_hold = new PieceHold(new Vector2D(PLAYFIELD_POS_X-PADDING*10, PLAYFIELD_POS_Y), PIECE_DISPLAY_WIDTH, PIECE_DISPLAY_HEIGHT);
		
		// Game Setup ("Controllers")
		drop_gravity = new DropGravity(INITIAL_DROP_GRAVITY_CELLS, INITIAL_DROP_GRAVITY_FRAMES, this);
		recursive_gravity = new LineClearGravity(CLEAR_GRAVITY_CELLS, CLEAR_GRAVITY_FRAMES, this);
		score = new ScoreSystem(INITIAL_LEVEL, this);
		rand_gen = new RandomGenerator<Piece>(Piece.class, PREVIEW_SIZE);
		lock_delay = new LockDelay(DELAY_FRAMES);
		

		// UI Setup
		score_text = new Text(PLAYFIELD_POS_X, PLAYFIELD_POS_Y-PADDING, "Score: " + score.score);
		score_text.setFont(GAME_FONT);

		level_text = new Text(PLAYFIELD_POS_X+PLAYFIELD_WIDTH-PADDING, PLAYFIELD_POS_Y-PADDING, "Level: " + score.level);
		level_text.setFont(GAME_FONT);
		level_text.setTranslateX(-level_text.getLayoutBounds().getWidth());

		goal_text = new Text(PLAYFIELD_POS_X+PLAYFIELD_WIDTH+PADDING*2, PLAYFIELD_POS_Y+PLAYFIELD_HEIGHT, "Goal: " + score.goal);
		goal_text.setFont(GAME_FONT);
		goal_text.setTranslateY(goal_text.getLayoutBounds().getHeight());

		next_text = new Text(PLAYFIELD_POS_X+PLAYFIELD_WIDTH+PADDING*3, PLAYFIELD_POS_Y-PADDING, "Next");
		next_text.setFont(GAME_FONT);

		hold_text = new Text(PLAYFIELD_POS_X-PADDING*6, PLAYFIELD_POS_Y-PADDING, "Hold");
		hold_text.setFont(GAME_FONT);

		feedback_text = new Text(PLAYFIELD_POS_X, PLAYFIELD_POS_Y+PLAYFIELD_HEIGHT, "");
		feedback_text.setFont(GAME_FONT);
		feedback_text.setTranslateY(feedback_text.getLayoutBounds().getHeight());
		
		// Add to JavaFx Group for display 
		this.getChildren().add(playfield);
		this.getChildren().add(piece_preview);
		this.getChildren().add(piece_hold);
		this.getChildren().add(score_text);
		this.getChildren().add(level_text);
		this.getChildren().add(goal_text);
		this.getChildren().add(next_text);
		this.getChildren().add(hold_text);
		this.getChildren().add(feedback_text);
	}
	
	/**
	 * Frame update of the game 
	 * @param frames, number frame updates
	 */
	public void updateGame(int frames) {
		while (frames > 0) {
			// If game is over terminate
			if (isGameOver()) {
				feedback_text.setText("GAME OVER! Press Esc");
				return;
			}

			// Spawn piece if needed and possible (line clear gravity not in effect)
			if (playfield.needSpawn() && recursive_gravity.isEmpty() && rows_to_clear.isEmpty()) {
				spawn(rand_gen.next());
				piece_preview.update(rand_gen.getPreviewList());
			}

			// Call drop gravity on the active Tetromino 
			callDropGravity();

			// Update and lock the Tetromino if applicable
			updateLockAndHold();

			// Clear lines if needed, create Polyominos to drop 
			if (!rows_to_clear.isEmpty()) {
				clearLines();
			}

			// Call line clear gravity on Polyominos created after line clear
			recursive_gravity.call();
			
			frames--;
		}
	}
	
	/**
	 * Left shift the active piece
	 */
	public void shiftLeft() {
		if (!playfield.needSpawn()) {
			playfield.move(playfield.active_piece, -1, 0);
		}
	}
	
	/**
	 * Right shift the active piece
	 */
	public void shiftRight() {
		if (!playfield.needSpawn()) {
			playfield.move(playfield.active_piece, 1, 0);
		}
	}
	
	/**
	 * Set the drop to hard or soft
	 * @param d, Drop type
	 */
	public void setDrop(DropType d) {
		if (!playfield.needSpawn()) {
			drop_gravity.setDropType(d);
		}
	}
	
	/**
	 * Rotate the active piece
	 * @param d, Rotate direction
	 */
	public void rotate(RotateDirection d) {
		if (!playfield.needSpawn()) {
			playfield.rotate(playfield.active_piece, d);
		}
	}
	
	/**
	 * Hold the active piece if possible
	 */
	public void holdPiece() {
		if (!playfield.needSpawn() && piece_hold.canUse()) {
			Piece current = playfield.active_piece.piece;
			Piece replace = piece_hold.swap(current);

			if (current != null) {
				playfield.removeFromGroup(playfield.active_piece);
			}

			if (replace != null) {
				spawn(replace);
			}
			else {
				spawn(rand_gen.next());
			}
		}
	}
	
	/**
	 * Game is over if there is a need to spawn and it cannot be spawned
	 * @return, true if over
	 */
	public boolean isGameOver() {
		return playfield.needSpawn() && !playfield.canSpawn(rand_gen.peek());
	}
	
	/**
	 * Call drop gravity, updating score for soft or hard drops
	 */
	private void callDropGravity() {
		if (playfield.active_piece == null) return;
		
		int dropped;
		double old_y, new_y;
		
		old_y = playfield.active_piece.getPos().getAbsY();
		drop_gravity.call();
		new_y = playfield.active_piece.getPos().getAbsY();
		
		dropped = (int)(old_y-new_y); //since 0 = bottom
		score.addCellsDropped(dropped, drop_gravity.getDropType());
		score_text.setText("Score: " + score.score);
		
		// Instant lock on hard drop (per Guideline/sonic-lock)
		if (drop_gravity.getDropType() == DropType.HARD){
			lock_delay.setDelayCount(60);
		}
		
		// Reset to normal
		if (drop_gravity.getDropType() != DropType.NORMAL) {
			drop_gravity.setDropType(DropType.NORMAL);
		}
	}
	
	/**
	 * Update the state of the lock delay and hold usage
	 * if there is a lock
	 */
	private void updateLockAndHold() {
		if (playfield.active_piece != null) {
			lock_delay.updateLock(playfield.isDroppable(playfield.active_piece));
			if (lock_delay.timeToLock()) {
				lock(playfield.active_piece);
				piece_hold.resetUse();
			}
		}
	}
	
	/**
	 * Lock a Polyomino's position in the Playfield and
	 * determine which rows to check for line clears
	 * @param p, Polyomino to lock
	 */
	protected void lock(Polyomino p) {
		boolean lock_active_piece = p.equals(playfield.active_piece); 
		
		if (lock_active_piece) {
			playfield.active_piece = null;
		}
		
		playfield.addToGrid(p);
		
		getRowsToClear(p);
		
		if (lock_active_piece && rows_to_clear.isEmpty()) {
			score.resetCombo();
		}
	}
	
	/**
	 * Determine which rows to clear based on a Polyomino
	 * being locked
	 * @param p, Polyomino being locked
	 */
	private void getRowsToClear(Polyomino p) {
		for (Mino m: p.minos) {
			if (playfield.isLine((int) m.getPos().getAbsY())) {
				rows_to_clear.add((int) m.getPos().getAbsY());
			}
		}
	}
	
	/**
	 * Clear the lines and apply recursive gravity
	 * Uses rows_to_clear to cache where to look for line clears
	 */
	private void clearLines() {
		for (int r: rows_to_clear) {
			playfield.clearLine(r);
		}
		
		for (int r: rows_to_clear) {
			recursive_gravity.addAll(createPolyominos(r+1));
		}
		
		score.addLinesCleared(rows_to_clear.size());
		score_text.setText("Score: " + score.score);
		level_text.setText("Level: " + score.level);
		goal_text.setText("Goal: " + score.goal);
		rows_to_clear.clear();
	}
	
	/**
	 * Spawns a particular Tetromino Piece
	 * @param p, Piece to spawn
	 */
	private void spawn(Piece p) {
		Tetromino t = p.create(playfield);
		drop_gravity.set(t);
		lock_delay.setTarget(t);
		playfield.spawn(t);
	}
	
	/**
	 * Create the Polyominos to apply recursive gravity to
	 * after there is a line clear.
	 * @param r, row that was line cleared
	 * @return, list of Polyominos created from the line clear
	 */
	private List<Polyomino> createPolyominos(int r) {
		List<Polyomino> debris = new ArrayList<Polyomino>();
		boolean consecutive = false;
		
		for (int c=0; c<playfield.cols; c++) {
			if (!consecutive && playfield.grid[r][c] != null) {
				List<Mino> ms = floodFill(r, c);
				debris.add(new Polyomino(ms.toArray(new Mino[ms.size()])));
				consecutive = true;
			}
			else if (playfield.grid[r][c] == null) {
				consecutive = false;
			}
		}
		return debris;
	}
	
	/**
	 * Flood-fill algorithm for generating Polyominos after 
	 * line clear
	 * @param r, row to check
	 * @param c, column to check
	 * @return, list of Minos to construct the Polyomino
	 */
	private List<Mino> floodFill(int r, int c) {
		List<Mino> ms = new ArrayList<Mino>();
		
		if (r < 0 || r >= playfield.rows || c < 0 || c >= playfield.cols) {
			return ms;
		}
		
		if (playfield.grid[r][c] == null) {
			return ms;
		}
		else {
			ms.add(playfield.grid[r][c]);
			playfield.grid[r][c] = null; // remove it
		}
		
		ms.addAll(floodFill(r+1, c));
		ms.addAll(floodFill(r, c-1));
		ms.addAll(floodFill(r-1, c));
		ms.addAll(floodFill(r, c+1));
		
		return ms;
	}
	
	/**
	 * Increase the gravity on level up
	 * @param i, levels gained
	 */
	protected void updateGravity(int i) {
		while (i>0) {
			increaseGravity();
			i--;
		}
	}
	
	/**
	 * Increase gravity algorithm
	 * Improves responsiveness before increasing the magnitude of the drop
	 * Creates a smooth animation.
	 */
	private void increaseGravity() {
		// Drop frames first before increasing cells (responsiveness)
		if (drop_gravity.getFrame() > 1) {
			drop_gravity.setFrame((int)(drop_gravity.getFrame()/(1+DROP_GRAVITY_GROWTH_FACTOR)));
		}
		else {
			drop_gravity.setCell((int)(drop_gravity.getCell()*(1+DROP_GRAVITY_GROWTH_FACTOR)));
		}
	}
}
