package classes;

import interfaces.ICurve;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

/**
 * Implementation of a hermit curve.
 */
public class HermiteCurve implements ICurve {
	/**
	 * List of the control points.
	 */
	private List<Vector3f> points;

	/**
	 * Constructor.
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
	public HermiteCurve(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4) {
		this.points = new ArrayList<>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
	}

	@Override
	public Vector3f eval(double val) {
		double basic = 0;
		Vector3f result = new Vector3f();

		for (int i = 0; i < points.size(); i++) {
			Vector3f innerMult = new Vector3f();
			basic = evalBasisFunction(i, val);
			innerMult.scale((float) basic, points.get(i));
			result.add(innerMult);
		}
		return result;
	}

	@Override
	public Vector3f derivative(double val) {
		double basic = 0;
		Vector3f result = new Vector3f();

		for (int i = 1; i < points.size(); i++) {
			Vector3f innerMult = new Vector3f();
			basic = evalDerivative(i, val);
			innerMult.scale((float) basic, points.get(i));
			result.add(innerMult);
		}
		return result;
	}

	/**
	 * Basic functions for the evaluation.
	 * 
	 * @param n
	 *            Index of the function.
	 * @param val
	 *            Value of x.
	 * @return The value of the called basic function.
	 */
	private double evalBasisFunction(int n, double val) {
		double result = 0d;
		switch (n) {
		case 0:
			result = ((1 - val) * (1 - val)) * (1 + 2 * val);
			break;
		case 1:
			result = val * ((1 - val) * (1 - val));
			break;
		case 2:
			result = -(val * val) * (1 - val);
			break;
		case 3:
			result = (3 - 2 * val) * (val * val);
			break;
		default:
			break;
		}
		return result;
	}

	/**
	 * Basic derivation functions for the evaluation.
	 * 
	 * @param n
	 *            Index of the function.
	 * @param val
	 *            Value of x.
	 * @return The value of the called basic function.
	 */
	private double evalDerivative(int n, double val) {
		double result = 0d;
		switch (n) {
		case 0:
			result = 6 * val * (val - 1);
			break;
		case 1:
			result = 3 * val * val - 4 * val + 1;
			break;
		case 2:
			result = val * (3 * val - 2);
			break;
		case 3:
			result = -6 * val * (val - 1);
			break;
		default:
			break;
		}
		return result;

	}
}
