package model;
import data_types.RotateDirection;
import data_types.RotateState;
import data_types.Vector2D;
import model.Polyomino;
import model.Piece;
import model.Rotatable;
import model.Mino;

/**
 * Tetromino
 * @author Josh Chia
 * 
 * Specific kind of Polyomino that players' control, either
 * shifting left, right or rotating left or right (90 degrees).
 *  
 * The Tetrominos that can be created is construed by the 
 * enumerable Pieces. 
 *
 */

public class Tetromino extends Polyomino implements Rotatable {
	private Vector2D pos, rot;
	private RotateState rot_state;
    public final Piece piece;

    /**
     * Constructs a Tetromino
     * @param p, type of Piece
     * @param pos, position as vector
     * @param rot, rotation point, relative to pos
     * @param ms, Minos that compose a Tetromino
     */
    protected Tetromino(Piece p, Vector2D pos, Vector2D rot, Mino[] ms) {
    	super(ms);
    	this.pos = pos;
    	this.rot = rot;
		piece = p;
		rot_state = RotateState.ZERO;
    }
    
    /**
     * Set point to rotate the Tetromino (relative to position)
     * @param rx, x-component of the rotation vector
     * @param ry, y-component of the rotation vector
     */
    private void setRotationPoint(double rx, double ry) {
    	rot.setXY(rx, ry);
    }
    
    /**
     * Get the state of rotation (used for Wall Kicks)
     * @return, current RotateState
     */
    public RotateState getRotState() {
    	return rot_state;
    }
    
    /**
     * Get the current position
     * @return current position vector
     */
    public Vector2D getPos() {
    	return pos;
    }
    
    /**
     * Set the vector position of the Tetromino
     * @param px, x-coordinate
     * @param py y-coordinate
     */
    protected void setPos(int px, int py) {
    	pos.setXY(px, py);
    }
    
    /**
     * Moves Tetromino 
     * @vx, amount to move along x-axis
     * @vy, amount to move along y-axis
     */
    @Override
    public void move(int vx, int vy) {
		pos.adjustXY(vx, vy);
		for(Mino m: minos) {
			m.grid_canvas.updateDrawState(m);
		}
	}
    
    /**
     * Rotates the Tetromino
     * @r, direction to rotate
     */
    @Override
    public void rotate(RotateDirection r) {
    	switch (r) {
    	case RIGHT:
    		setPos((int)(-rot.getY() + pos.getAbsX() + rot.getX()), (int)(rot.getX() + pos.getAbsY() + rot.getY()));
    		
    		for (Mino m: minos) {
    			m.setPos(m.pos.getY(), -m.pos.getX());
    		}
    		
    		setRotationPoint(rot.getY(), -rot.getX());
    		break;
    	case LEFT:
    		setPos((int)(rot.getY() + pos.getAbsX() + rot.getX()), (int)(-rot.getX() + pos.getAbsY() + rot.getY())); 
    		
    		for (Mino m: minos) {
    			m.setPos(-m.pos.getY(), m.pos.getX());
    		}
    		
    		setRotationPoint(-rot.getY(), rot.getX());
    		break;
		default:
			break;
    	}
    	rot_state = rot_state.rotate(r);
    } 

    /**
     * For JUnit testing purposes, Tetrominos are equal if they 
     * occupy the same state of rotation and positions.
     * 
     * The Object equals method is not overridden in case a strong 
     * equality is needed. 
     * 
     * @param t, Tetromino compare with
     * @return, true if consider equal
     */
    public boolean equals(Tetromino t) {
    	if (rot_state != t.getRotState()) {
    		return false;
    	}
    	
    	if (minos.length != t.minos.length) {
    		return false;
    	}
    	
    	for (int i=0; i<minos.length; i++) {
    		if (!minos[i].equalsTest(t.minos[i])) {
    			return false;
    		}
    	}
    	return true;
    }
}