package classes;

import interfaces.ICurve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Vector3f;

/**
 * Monomial curve representation.
 */
public class MonomialCurve implements ICurve {
	/**
	 * List of the control points.
	 */
	private List<Vector3f> controlPoints;
	/**
	 * Degree of the curve.
	 */
	private int degree;

	/**
	 * Constructor.
	 * 
	 * @param degree
	 *            The value of the degree for the curve.
	 */
	private MonomialCurve(int degree) {
		this.controlPoints = new ArrayList<>();
		this.degree = degree;
	}

	/**
	 * Constructor for a curve with degree = 3.
	 * 
	 * @param p1
	 *            The first control point.
	 * @param p2
	 *            The second control point.
	 * @param p3
	 *            The third control point.
	 * @param p4
	 *            The fourth control point.
	 */
	public MonomialCurve(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		this(3);
		controlPoints.add(p1);
		controlPoints.add(p2);
		controlPoints.add(p3);
		controlPoints.add(p4);
	}

	/**
	 * Constructor for a curve with degree = n.
	 * 
	 * @param points
	 *            Array of all given control points. [degree = points.length - 1]
	 */
	public MonomialCurve(Vector3f... points) {
		this(points.length - 1);
		controlPoints.addAll(Arrays.asList(points));
	}

	@Override
	public Vector3f eval(double val) {
		float f_x = 0;

		for (int i = 0; i < controlPoints.size(); ++i) {
			f_x += (float) Math.pow(val, i) * controlPoints.get(i).y;
		}

		return new Vector3f((float) val, f_x, 0f);
	}

	@Override
	public Vector3f derivative(double val) {
		float f_x = 0;

		for (int i = 1; i < controlPoints.size(); ++i) {
			f_x += (float) Math.pow(val, i - 1) * controlPoints.get(i).y * i;
		}

		return new Vector3f((float) val, f_x, 0f);
	}

	/**
	 * Getter.
	 * 
	 * @return The degree of the curve.
	 */
	public int getDegree() {
		return degree;
	}
}
