package tests;

import javax.vecmath.Vector3f;

import interfaces.ICurve;

import org.junit.Test;

import classes.MonomialCurve;

import static org.junit.Assert.*;

public class TestCurves {
	@Test
	public void testMonomial() {
		Vector3f p0 = new Vector3f(0f, 0f, 0f);
		Vector3f p1 = new Vector3f(2f, 2f , 0f);
		Vector3f p2 = new Vector3f(2f, 2f , 0f);
		ICurve curve = MonomialCurve.interpolate(new Vector3f[] {p0, p1});
		assertEquals(p0, curve.eval(0));
		assertEquals(p1, curve.eval(1));
		
		curve = MonomialCurve.interpolate(new Vector3f[] {p0, p1, p2});
		assertEquals(p0, curve.eval(0));
		assertEquals(p1, curve.eval(0.5));
		assertEquals(p2, curve.eval(1));
	}
}
