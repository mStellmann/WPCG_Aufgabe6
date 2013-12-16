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

	public SliderListener(ICurve curve, TransformGroup transformGroup) {
		this.curve = curve;
		this.transformGroup = transformGroup;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (source.getValueIsAdjusting()) {
	        Vector3f point = curve.eval((double) source.getValue() / 1000);
	        Transform3D transform3d = new Transform3D();
	        transform3d.setTranslation(point);
	        transformGroup.setTransform(transform3d);
	    }
	}

}
