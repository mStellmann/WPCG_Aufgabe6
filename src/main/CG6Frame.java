package main;

import interfaces.ICurve;

import java.awt.BorderLayout;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import classes.AppearanceHelper;
import classes.HermiteCurve;
import classes.MonomialCurve;
import classes.SliderListener;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class CG6Frame extends JFrame {

	/**
	 * Gernerated serialnumber.
	 */
	private static final long serialVersionUID = -6619879728982489623L;

	/**
	 * Canvas object for the 3D content.
	 */
	private Canvas3D canvas3D;

	/**
	 * Simple universe (provides reasonable default values).
	 */
	protected SimpleUniverse universe;

	/**
	 * Scene graph for the 3D content scene.
	 */
	protected BranchGroup scene = new BranchGroup();

	/**
	 * JSlider for the Curves
	 */
	private JSlider jSlider;

	/**
	 * Default constructor.
	 */
	public CG6Frame() {
		// Create canvas object to draw on
		canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

		// The SimpleUniverse provides convenient default settings
		universe = new SimpleUniverse(canvas3D);
		universe.getViewingPlatform().setNominalViewingTransform();

		// Setup lighting
		addLight(universe);

		// Allow for mouse control
		OrbitBehavior ob = new OrbitBehavior(canvas3D);
		ob.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), Double.MAX_VALUE));
		universe.getViewingPlatform().setViewPlatformBehavior(ob);

		// Set the background color
		Background background = new Background(new Color3f(0.9f, 0.9f, 0.9f));
		BoundingSphere sphere = new BoundingSphere(new Point3d(0, 0, 0), 100000);
		background.setApplicationBounds(sphere);
		scene.addChild(background);

		// Setup frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Aufgabe 6 - Kurven");
		setSize(500, 500);
		getContentPane().add("Center", canvas3D);
		setVisible(true);

		// JSlider Frame
		jSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		JFrame sliderFrame = new JFrame("Slide it!");
		// Turn on labels at major tick marks.
		jSlider.setMajorTickSpacing(100);
		jSlider.setMinorTickSpacing(1);
		jSlider.setPaintTicks(true);
		jSlider.setPaintLabels(false);

		sliderFrame.getContentPane().add(jSlider, BorderLayout.CENTER);
		sliderFrame.pack();
		sliderFrame.validate();
		sliderFrame.setVisible(true);

	}

	/**
	 * Setup the lights in the scene. Attention: The light need to be added to
	 * the scene before the scene in compiled (see createSceneGraph()).
	 */
	private void addLight(SimpleUniverse universe) {
		addPointLight(new Point3f(10, 10, 10));
		addPointLight(new Point3f(-10, -10, -10));
		addPointLight(new Point3f(10, -10, 10));
		addDirectionalLight(new Vector3f(0, 0, 1));
	}

	void addPointLight(Point3f position) {
		PointLight light = new PointLight();
		light.setPosition(position);
		light.setColor(new Color3f(1, 1, 1));
		light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		scene.addChild(light);
	}

	void addDirectionalLight(Vector3f direction) {
		DirectionalLight light = new DirectionalLight();
		light.setDirection(direction);
		light.setColor(new Color3f(1, 1, 1));
		light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0));
		scene.addChild(light);
	}

	/**
	 * Create the default scene graph.
	 */
	protected void createSceneGraph() {
		Sphere curvePoint = new Sphere(0.05f);
		AppearanceHelper.setColor(curvePoint, new Color3f(1f, 0.5f, 0.2f));

		Vector3f p1 = new Vector3f(0f, 0.0f, 0f);
		Vector3f p2 = new Vector3f(-0.2f, 1f, 0f);
		Vector3f p3 = new Vector3f(1.5f, 0f, 0f);
		Vector3f p4 = new Vector3f(0.5f, 0f, 0f);
		ICurve curve = new MonomialCurve(p1, p2, p3, p4);
		ICurve curveHermite = new HermiteCurve(p1, p2, p3, p4);

		Transform3D curvePointTransform3D = new Transform3D();
		curvePointTransform3D.setTranslation(curveHermite.eval(0d));
		TransformGroup curvePointTransformGroup = new TransformGroup(curvePointTransform3D);
		curvePointTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		curvePointTransformGroup.addChild(curvePoint);

		jSlider.addChangeListener(new SliderListener(curveHermite, curvePointTransformGroup));

		scene.addChild(curvePointTransformGroup);
		scene.addChild(createCurveLine(curveHermite));
		// Assemble scene
		scene.compile();
		universe.addBranchGraph(scene);
	}

	private Shape3D createCurveLine(ICurve curve) {
		Point3f[] points = new Point3f[1000];
		for (int i = 0; i < 1000; i++) {
			points[i] = new Point3f(curve.eval((double) i / 1000));
		}
		LineArray lineAry = new LineArray(points.length, LineArray.COORDINATES);
		lineAry.setCoordinates(0, points);
		LineAttributes lineAttributes = new LineAttributes(0.1f, LineAttributes.PATTERN_DASH_DOT, true);
		Appearance lineAppearance = new Appearance();
		lineAppearance.setLineAttributes(lineAttributes);
		lineAppearance.setColoringAttributes(new ColoringAttributes(new Color3f(0f, 0f, 0f), ColoringAttributes.SHADE_FLAT));
		return new Shape3D(lineAry, lineAppearance);
	}

	/**
	 * Starting method.
	 * 
	 * @param args
	 *            Program arguments.
	 */
	public static void main(String[] args) {
		// Create the central frame
		CG6Frame frame = new CG6Frame();
		// Add content to the scene graph
		frame.createSceneGraph();
	}

}
