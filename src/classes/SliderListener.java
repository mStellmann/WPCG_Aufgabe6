package classes;

import interfaces.ICurve;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Vector3f;

/**
 * Implementation of a ChangeListener for the JSlider.
 */
public class SliderListener implements ChangeListener {
	private ICurve curve;
	private TransformGroup transformGroup;
	private TransformGroup rotationTanArrow;

	public SliderListener(ICurve curve, TransformGroup transformGroup, TransformGroup rotationTanArrow) {
		this.curve = curve;
		this.transformGroup = transformGroup;
		this.rotationTanArrow = rotationTanArrow;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (source.getValueIsAdjusting()) {
	    	double val = (double) source.getValue() / 1000;
	        Vector3f point = curve.eval(val);
	        Vector3f derivation = curve.derivative(val);
	        
	        double angle = Math.atan2(derivation.getY(), derivation.getX());	        
			Transform3D rotTanArrow = new Transform3D();
			rotTanArrow.rotZ(angle + (-90 * Math.PI / 180));		
			rotationTanArrow.setTransform(rotTanArrow);
	        
	        Transform3D transform3d = new Transform3D();
	        transform3d.setTranslation(point);
	        transformGroup.setTransform(transform3d);
	    }
	}

}
