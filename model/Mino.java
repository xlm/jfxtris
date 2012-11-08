package model;

import game.GridCanvas;
import data_types.Vector2D;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Mino
 * @author Josh Chia
 * 
 * The smallest (atomic) building block in the game of Tetris
 * that exist within a grid world (GridCanvas) 
 * Visually Minos are represented as rectangles
 *
 */
public class Mino extends Rectangle implements Movable {
	protected Vector2D pos;
	protected GridCanvas grid_canvas;
	
	/**
	 * Setup lighting effects for '3D look'
	 */
	private static Light.Distant light = new Light.Distant();
	static {
		light.setAzimuth(-90.0);
	}
	private static Lighting lighting = new Lighting();
	static {
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);
	}
	
	/**
	 * Construct default (color) Mino
	 * Used for debugging and creating demonstration games
	 * See DemoGame.java
	 * @param gc, grid world this Mino exists in
	 * @param p, position vector of this Mino
	 */
	public Mino(GridCanvas gc, Vector2D p) {
		super(gc.getGridWidth(), gc.getGridHeight(), Color.SEASHELL);
		super.setStroke(Color.BLACK);
		super.setEffect(lighting);
		
		grid_canvas = gc;
		pos = p;
		
		grid_canvas.getChildren().add(this);
		grid_canvas.updateDrawState(this);
	}
	
	/**
	 * Construct specific Mino, stipulating its color.
	 * Set as invisible first until we are sure that it can 
	 * be seen in the grid world.
	 * @param gc, gird world this Mino exists in
	 * @param p, position vector of this Mino
	 * @param c, color of this Mino
	 */
	public Mino(GridCanvas gc, Vector2D p, Paint c) {
		super(gc.getGridWidth(), gc.getGridHeight(), c);
		super.setStroke(Color.BLACK);
		super.setEffect(lighting);
		
		grid_canvas = gc;
		pos = p;
		
		gc.getChildren().add(this);
		this.setVisible(false);
		grid_canvas.updateDrawState(this);
	}
	
	/**
	 * Shift the Mino's position 
	 * @vx, value to shift the Mino along x-axis
	 * @vy, value to shift the Mino along y-axis
	 */
	@Override
	public void move(int vx, int vy) {
		pos.adjustXY(vx, vy);
		grid_canvas.updateDrawState(this);
	}
	
	/**
	 * Set the Mino's position
	 * @param x, new x-coordinate
	 * @param y, new y-coordinate
	 */
	public void setPos(double x, double y) {
		pos.setXY(x, y);	
		grid_canvas.updateDrawState(this);
	}
	
	public Vector2D getPos() {
		return pos;
	}
	
	/**
	 * Set Mino's color
	 * @param p, color to set it to
	 */
	public void setColor(Paint p) {
		this.setFill(p);
	}
	
	/**
	 * For JUnit Testing purposes, a Mino is equal to another if it
	 * occupies the same absolute position in the same grid world 
	 * @param m, other Mino being compared
	 * @return, true if they are equal
	 */
	public boolean equalsTest(Mino m) {
		return pos.getAbsX() == m.pos.getAbsX() && 
				pos.getAbsY() == m.pos.getAbsY() && 
				m.grid_canvas==grid_canvas;
	}
}