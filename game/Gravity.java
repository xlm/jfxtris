package game;


/**
 * Gravity
 * @author Josh Chia
 * 
 * Abstract representation of all forms of gravity in a Game
 * Gravity only acts if a certain number of frames elapsed before
 * reseting that count of frames. 
 *
 */
public abstract class Gravity {
	protected int frame, frame_count;
	protected int cell;
	protected Game game;
	
	/**
	 * Construct Gravity for a Game
	 * @param c, cells to move downward (y-coordinate)
	 * @param f, frames to wait before actioning
	 * @param g, Game that gravity is controlling
	 */
	public Gravity(int c, int f, Game g) {
		cell = c;
		frame = f;
		game = g;
		frame_count = 0;
	}
	
	/**
	 * Calls gravity to act (to be called on a frame-basis) 
	 */
	public final void call() {
		frame_count++;
		if (frame_count == frame) {
			effect();
			frame_count = 0;
		}
	}
	
	/**
	 * Set the amount of cells to drop per a frame
	 * @param c, cells
	 */
	public final void setCell(int c) {
		cell = c;
	}
	
	/**
	 * Set the number of frames to wait before dropping
	 * @param f, frames
	 */
	public final void setFrame(int f) {
		frame = (f <= 0) ? 1: f;
		frame_count = 0;
	}
	
	/**
	 * Get current cell value of Gravity
	 * @return, cell
	 */
	public final int getCell() {
		return cell;
	}
	
	/**
	 * Get current frame value of Gravity (not frame count)
	 * @return, frame
	 */
	public final int getFrame() {
		return frame;
	}
	
	/**
	 * Apply gravity (drop) all the affected Polyominos
	 */
	abstract protected void effect();
}
