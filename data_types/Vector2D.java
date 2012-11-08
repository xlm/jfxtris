package data_types;

/**
 * Vector2D
 * @author Josh Chia
 * 
 * Representation of two-dimensional real Euclidean vectors
 * Vectors can be either absolute or relative to another Vector2D object
 * Used to represent coordinate positions, rotation points, movement (velocity) etc.
 * Removes the need to use 'conventions' with primitive data types 
 *
 */
public class Vector2D {
	private double x, y;
	private Vector2D relative;
	
	/**
	 * Construct an absolute vector (relative to the origin)
	 * @param x, magnitude along x-axis 
	 * @param y, magnitude along y-axis
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construct a relative vector (relative to another vector)
	 * @param v, the vector this vector is relative to
	 * @param x, this vector's magnitude along x-axis
	 * @param y, this vector's magnitude along y-axis
	 */
	public Vector2D(Vector2D v, double x, double y) {
		this.x = x;
		this.y = y;
		relative = v;
	}
	
	/**
	 * Get the absolute magnitude of the vector's x-component
	 * @return, absolute magnitude of the x-component
	 */
	public double getAbsX() {
		return (isRelative()) ? relative.getAbsX()+x: x;
	}
	
	/**
	 * Get the absolute magnitude of the vector's y-component
	 * @return, absolute magnitude of the y-component
	 */
	public double getAbsY() {
		return (isRelative()) ? relative.getAbsY()+y: y; 
	}
	
	/**
	 * Get this vector's x-component (ignores relative vector)
	 * @return, this vector's x-component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Get this vector's y-component (ignores relative vector)
	 * @return, this vector's y-component
	 */
	public double getY() {
		return y; 
	}
	
	/**
	 * Convert this vector into a absolute vector (relative to origin)
	 */
	public void makeAbsolute() {
		this.x = getAbsX();
		this.y = getAbsY();
		relative = null;
	}
	
	/**
	 * Make this vector relative to another
	 * @param v, the vector that this vector is set relative to 
	 */
	public void makeRelative(Vector2D v) {
		relative = v;
	}
	
	/**
	 * Check if a vector is relative or absolute
	 * @return, true if relative or false if absolute (relative to origin)
	 */
	public boolean isRelative() {
		return relative!=null;
	}
	
	/**
	 * Set the value of the vector's components
	 * @param x, new x-component value
	 * @param y, new y-component value
	 */
	public void setXY(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Adjust the x-component and y-component of a vector
	 * @param dx, amount to adjust the x-component 
	 * @param dy, amount to adjust the y-component
	 */
	public void adjustXY(double dx, double dy) {
		x += dx;
		y += dy;
	}
}