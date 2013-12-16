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
		Vector3f point0 = new Vector3f(points.get(0));
		point0.scale((float) evalBasisFunction(0, val));
		
		Vector3f m0 = new Vector3f(points.get(1));
		m0.sub(points.get(0));
		m0.scale((float) evalBasisFunction(1, val));
		
		Vector3f m1 = new Vector3f(points.get(2));
		m1.sub(points.get(3));
		m1.scale((float) evalBasisFunction(2, val));
		
		Vector3f point1 = new Vector3f(points.get(3));
		point1.scale((float) evalBasisFunction(3, val));
		
		point0.add(m0);
		point0.add(m1);
		point0.add(point1);
		return point0;
	}

	@Override
	public Vector3f derivative(double val) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Basic functions for the evaluation.
	 * 
	 * @param n Index of the function.
	 * @param val Value of x.
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
	 * TODO JavaDoc
	 * 
	 * @param n
	 * @param val
	 * @return
	 */
	private double evalDerivative(int n, double val) {
		// TODO Auto-generated method stub
		return 0.0;
	}
}
