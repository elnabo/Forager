package forager.util;

import java.awt.geom.Point2D;

import static java.lang.Math.sqrt;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.PI;
import static java.lang.Math.sin;

/**
 * A 2D vector.
 * 
 * @author Guillaume Desquesnes
 */
public class Vector2D
{
	/** The x value. */
	public double x;
	/** The y value. */
	public double y;
	
	/**
	 * Create a (0,0) vector.
	 */
	public Vector2D()
	{		
		x = 0;
		y = 0;
	}
	
	/**
	 * Create a vector from its x and y value.
	 * 
	 * @param x The x value.
	 * @param y The y value.
	 */
	public Vector2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create the vector between the two points.
	 * 
	 * @param start The start point of the vector.
	 * @param end The end point of the vector.
	 */
	public Vector2D(Point2D.Double start, Point2D.Double end)
	{		
		x = start.x - end.x;
		y = start.y - end.y;
	}
	
	/**
	 * Create a vector from a point.
	 * 
	 * @param position The point.
	 */
	public Vector2D(Point2D.Double position)
	{		
		x = position.x;
		y = position.y;
	}
	
	/**
	 * Copy a vector.
	 * 
	 * @param v The vector.
	 */
	public Vector2D(Vector2D v)
	{		
		x = v.x;
		y = v.y;
	}
	
	public static Vector2D fromPolar(double r, double theta)
	{
		double x = r * cos(theta);
		double y = r * sin(theta);
		return new Vector2D(x,y);
	}
	
	/**
	 * Add two vectors and return the result.
	 * 
	 * @param other The other vector to add.
	 * 
	 * @return The sum of the vectors.
	 */
	public Vector2D add(Vector2D other)
	{
		return new Vector2D(x+other.x, y+other.y);
	}
	
	/**
	 * Add a vector.
	 * 
	 * @param other The other vector to add.
	 */
	public Vector2D iadd(Vector2D other)
	{
		x += other.x;
		y += other.y;
		return this;
	}
	
	/**
	 * Substract two vectors and return the result.
	 * 
	 * @param other The vector to substract.
	 * 
	 * @return The difference between the vectors.
	 */
	public Vector2D sub(Vector2D other)
	{
		return new Vector2D(x-other.x, y-other.y);
	}
	
	/**
	 * Substract a vector.
	 * 
	 * @param other The vector to substract.
	 */
	public Vector2D isub(Vector2D other)
	{
		x -= other.x;
		y -= other.y;
		return this;
	}
    
    /**
     * Return the vector multiplied by a scalar.
     * 
     * @param s The scalar.
     * 
     * @return The vector multiplied by the scalar.
     */
    public Vector2D scalarMul(double s)
    {
		return new Vector2D(x * s, y * s);
	}
	
	/**
     * Multiply the vector by a scalar.
     * 
     * @param s The scalar.
     */
    public Vector2D iscalarMul(double s)
    {
		x *= s;
		y *= s;
		return this;
	}		
	
	/**
	 * Return the norm of the vector.
	 * 
	 * @return The norm of the vector.
	 */
	public double norm()
	{
		return sqrt(x*x + y*y);
	}
	
	/**
	 * Return the unit vector.
	 * 
	 * @return The unit vector.
	 */
	public Vector2D unitVector() 
	{
		double n = norm();
        if (n != 0) {
            return new Vector2D(x / n, y / n);
        }
        return new Vector2D(0,0);
    }
	
	
	/**
	 * Return the Theta of the vector;
	 * 
	 * @return The theta of the vector.
	 */
	public double getOrientation()
	{
		return 2 * (atan(y/(sqrt(x * x + y * y) + x)));
	}
	
	public Vector2D rotate(double theta)
	{
		return fromPolar(norm(), (getOrientation() + theta)%(2 * PI));
	}
	
	/**
	 * Return a point version of the vector.
	 * 
	 * @return A point version of the vector.
	 */
	public Point2D.Double toPoint()
	{
		return new Point2D.Double(x,y);
	}
	
	public String toString()
	{
		return "x = " + x + ", y = " + y;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == null) { return false;}
		if (!(other instanceof Vector2D)) { return false;}
		Vector2D o = (Vector2D) other;
		return Math.abs(o.x - x) < 0.001 && Math.abs(o.y -y) < 0.001;
	}

}
