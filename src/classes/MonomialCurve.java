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
	 *            Array of all given control points. [degree = points.length -
	 *            1]
	 */
	public MonomialCurve(Vector3f... points) {
		this(points.length - 1);
		controlPoints.addAll(Arrays.asList(points));
	}

	public static MonomialCurve interpolate(Vector3f[] vectorArray) {
		if (vectorArray.length == 2) {
			Vector3f c0 = vectorArray[0];
			Vector3f c1 = new Vector3f();
			c1.sub(vectorArray[1], vectorArray[0]);

			return new MonomialCurve(new Vector3f[] { c0, c1 });
		} else if (vectorArray.length == 3) {

			Vector3f a = vectorArray[0];
			Vector3f b = vectorArray[1];
			Vector3f c = vectorArray[2];

			Vector3f n = new Vector3f();

			Vector3f c0 = a;
			// c1 = (-1*y3)-(3*y1)+(4*y2);
			Vector3f c1 = new Vector3f();
			c1.scale(-1, c);
			n.scale(-3, a);
			c1.add(n);
			n.scale(4, b);
			c1.add(n);

			// c2 = (2*y3)+(2*y1)-(4*y2)
			Vector3f c2 = new Vector3f();
			c2.scale(2, c);
			n.scale(2, a);
			c2.add(n);
			n.scale(-4, b);
			c2.add(n);

			return new MonomialCurve(new Vector3f[] { c0, c1, c2 });
		} else {

		}
		return null;
	}

	@Override
	public Vector3f eval(double val) {
		double basic = 0;
		Vector3f result = new Vector3f();

		for (int i = 0; i < controlPoints.size(); i++) {
			Vector3f innerMult = new Vector3f();
			basic = Math.pow(val, i);
			innerMult.scale((float) basic, controlPoints.get(i));
			result.add(innerMult);
		}
		return result;
	}

	@Override
	public Vector3f derivative(double val) {
		double outDer = 0;
		Vector3f result = new Vector3f();

		for (int i = 1; i < controlPoints.size(); i++) {
			Vector3f innerMult = new Vector3f();
			outDer = i * Math.pow(val, i - 1);
			innerMult.scale((float) outDer, controlPoints.get(i));
			result.add(innerMult);
		}
		return result;
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
