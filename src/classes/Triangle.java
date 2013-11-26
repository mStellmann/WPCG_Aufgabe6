package classes;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 * This triangle class is used for the triangle meshes.
 * 
 * @author Matthias Stellmann & Grzegorz Markiewicz
 * 
 */
public class Triangle {

	/**
	 * Index of the first vertex.
	 */
	private int i;

	/**
	 * Index of the second vertex.
	 */
	private int j;

	/**
	 * Index of the third vertex.
	 */
	private int k;

	/**
	 * Normal unit vector for this triangle.
	 */
	private Vector3d normalVector;

	/**
	 * Index of the first texturecoordinate.
	 */
	private int texI;

	/**
	 * Index of the second texturecoordinate.
	 */
	private int texJ;

	/**
	 * Index of the third texturecoordinate.
	 */
	private int texK;

	/**
	 * Creates a new triangle.
	 * 
	 * @param i
	 *            Index of the first vertex.
	 * @param j
	 *            Index of the second vertex.
	 * @param k
	 *            Index of the third vertex.
	 */
	public Triangle(int i, int j, int k) {
		this(i, j, k, 0, 0, 0);
	}

	/**
	 * Creates a new triangle with texturecoordinates.
	 * 
	 * @param i
	 *            Index of the first vertex.
	 * @param j
	 *            Index of the second vertex.
	 * @param k
	 *            Index of the third vertex.
	 */
	public Triangle(int i, int j, int k, int texI, int texJ, int texK) {
		this.i = i;
		this.j = j;
		this.k = k;
		this.texI = texI;
		this.texJ = texJ;
		this.texK = texK;
	}

	/**
	 * Computes the normal unit vector for this triangle.
	 * 
	 * @param pA
	 *            Point of the first vertex.
	 * @param pB
	 *            Point of the second vertex.
	 * @param pC
	 *            Point of the third vertex.
	 */
	public void computeNormal(Point3d pA, Point3d pB, Point3d pC) {
		normalVector = new Vector3d();
		Vector3d pApB = new Vector3d();
		pApB.sub(pA, pB);
		Vector3d pApC = new Vector3d();
		pApC.sub(pA, pC);
		normalVector.cross(pApC, pApB);
		normalVector.normalize();
	}

	/**
	 * Getter.
	 * 
	 * @return Index of the first vertex.
	 */
	public int getI() {
		return i;
	}

	/**
	 * Getter.
	 * 
	 * @return Index of the second vertex.
	 */
	public int getJ() {
		return j;
	}

	/**
	 * Getter.
	 * 
	 * @return Index of the third vertex.
	 */
	public int getK() {
		return k;
	}

	/**
	 * Getter.
	 * 
	 * @return Index of the first texturecoordinate.
	 */
	public int getTexI() {
		return texI;
	}

	/**
	 * Getter.
	 * 
	 * @return Index of the second texturecoordinate.
	 */
	public int getTexJ() {
		return texJ;
	}

	/**
	 * Getter.
	 * 
	 * @return Index of the third texturecoordinate.
	 */
	public int getTexK() {
		return texK;
	}

	/**
	 * Getter.
	 * 
	 * @return Normal unit Vector.
	 */
	public Vector3d getNormalVector() {
		return normalVector;
	}

	/**
	 * Getter.
	 * 
	 * @return Normal unit Vector.
	 */
	public Vector3f getNormalVectorAsFloat() {
		return new Vector3f(normalVector);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + k;
		result = prime * result + ((normalVector == null) ? 0 : normalVector.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Triangle other = (Triangle) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (k != other.k)
			return false;
		if (normalVector == null) {
			if (other.normalVector != null)
				return false;
		} else if (!normalVector.equals(other.normalVector))
			return false;
		return true;
	}

}
