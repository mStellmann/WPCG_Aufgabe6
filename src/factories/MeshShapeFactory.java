package factories;

import interfaces.ITriangleMesh;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord3f;

import classes.AppearanceHelper;
import classes.Triangle;

import com.sun.j3d.utils.geometry.GeometryInfo;

/**
 * Factory for converting a ITriangleMesh into a Shape3D.
 * 
 * @author Matthias Stellmann & Grzegorz Markiewicz
 * 
 */
public class MeshShapeFactory {

	/**
	 * private constructor, utility class.
	 */
	private MeshShapeFactory() {
	};

	/**
	 * Creates a Shape3D-Object out of an ITriangleMesh.
	 * 
	 * @param mesh
	 *            ITriangleMesh to create the Shape3D-Object.
	 * @return Shape3D-Object
	 */
	public static Shape3D createMeshShape(ITriangleMesh mesh) {
		GeometryArray geoAry = createGeometryArray(mesh);

		Shape3D resultShape = new Shape3D(geoAry, new Appearance());
		AppearanceHelper.setColor(resultShape, new Color3f(0.2f, 0.7f, 0f));
		return resultShape;
	}

	/**
	 * Creates a Shape3D-Object with Texturecoordinates out of an ITriangleMesh.
	 * 
	 * @param mesh
	 *            ITriangleMesh to create the Shape3D-Object.
	 * @return Shape3D-Object with Texturecoordinates
	 */
	public static Shape3D createMeshShape(ITriangleMesh mesh, String textureFilename) {
		GeometryArray geoAry = createGeometryArray(mesh);
		Shape3D resultShape = new Shape3D(geoAry, AppearanceHelper.createTextureAppearance(textureFilename));
		return resultShape;
	}

	/**
	 * Creates a Shape3D-Object with texturecoordinates and shader out of an
	 * ITriangleMesh.
	 * 
	 * @param mesh
	 *            ITriangleMesh to create the Shape3D-Object.
	 * @return Shape3D-Object with texturecoordinates and shader
	 */
	public static Shape3D createMeshShape(ITriangleMesh mesh, String vertexShaderFilename, String fragmentShaderFilename, String textureFilename) {
		GeometryArray geoAry = createGeometryArray(mesh);
		Shape3D resultShape = new Shape3D(geoAry, AppearanceHelper.createShaderAppearance(vertexShaderFilename, fragmentShaderFilename, textureFilename));
		return resultShape;
	}

	/**
	 * This method creates the GeometryArray out of a TriangleMesh.
	 * 
	 * @param mesh
	 *            ITriangleMesh to create the Shape3D-Object
	 * @return GeometryArray
	 */
	private static GeometryArray createGeometryArray(ITriangleMesh mesh) {
		TriangleArray triAry = new TriangleArray(mesh.getNumberOfVertices(), TriangleArray.COORDINATES | TriangleArray.NORMALS | TriangleArray.TEXTURE_COORDINATE_3);

		int i = 0;
		for (Triangle elem : mesh.getTriangleList()) {
			Point3d[] coords = { mesh.getVertex(elem.getI()), mesh.getVertex(elem.getJ()), mesh.getVertex(elem.getK()) };
			TexCoord3f[] texCoords = { mesh.getTexturecoordinate(elem.getTexI()), mesh.getTexturecoordinate(elem.getTexJ()), mesh.getTexturecoordinate(elem.getTexK()) };
			// Setting the coordinates and the normals of the triangle
			triAry.setCoordinates(i, coords);
			triAry.setTextureCoordinates(0, i, texCoords);
			triAry.setNormal(i++, elem.getNormalVectorAsFloat());
			triAry.setNormal(i++, elem.getNormalVectorAsFloat());
			triAry.setNormal(i++, elem.getNormalVectorAsFloat());
		}

		GeometryInfo geoInfo = new GeometryInfo(triAry);
		return geoInfo.getGeometryArray();
	}
}
