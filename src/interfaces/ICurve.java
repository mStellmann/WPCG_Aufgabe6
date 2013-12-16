package interfaces;

import javax.vecmath.Vector3f;

/**
 * Interface for a curve.
 */
public interface ICurve {
	/**
	 * Evaluates the the point on the curve.
	 * 
	 * @param val
	 *            The value of the x-coordinate.
	 * @return Point at x-coordinate.
	 */
	public Vector3f eval(double val);

	/**
	 * Derivative of the curve at the given x-coordinate.
	 * 
	 * @param val
	 *            The value of the x-coordinate.
	 * @return Devirative at the x-coordinate.
	 */
	public Vector3f derivative(double val);
}
