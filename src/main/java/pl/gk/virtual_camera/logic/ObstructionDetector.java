package pl.gk.virtual_camera.logic;

import Jama.Matrix;
import pl.gk.virtual_camera.model.Point3D;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;

public class ObstructionDetector {

    /*  FUNCTIONS FOR SORTING CUBE WALLS BASED ON PLANE SET BY OTHER WALL.
        IF A WALL AND OBSERVER ARE ON THE SAME SIDE OF THE PLANE SET BY OTHER WALL,
        IT MEANS THIS WALL COVERS THE OTHER.
    */
    public static Matrix getPlaneFromPolygon(Rectangle3D rectangle3D) {

        ArrayList<Point3D> point3DList = rectangle3D.getPoint3DList();

        //Point[] polygonVertices = polygon.getVertices();

        double x1 = point3DList.get(0).getX();
        double y1 = point3DList.get(0).getY();
        double z1 = point3DList.get(0).getZ();

        double x2 = point3DList.get(1).getX();
        double y2 = point3DList.get(1).getY();
        double z2 = point3DList.get(1).getZ();

        double x3 = point3DList.get(2).getX();
        double y3 = point3DList.get(2).getY();
        double z3 = point3DList.get(2).getZ();

        double ux = x2 - x1;
        double uy = y2 - y1;
        double uz = z2 - z1;
        double vx = x3 - x1;
        double vy = y3 - y1;
        double vz = z3 - z1;

        double a = uy * vz - uz * vy;
        double b = uz * vx - ux * vz;
        double c = ux * vy - uy * vx;
        double d = (-a * x1 - b * y1 - c * z1);

        return new Matrix(new double[]{a, b, c, d}, 1);
    }

    public static double checkPointSideAgainstPlane(Matrix plane, Point3D point) {
        return pointMatrixMultiplication(plane, point) * pointMatrixMultiplication(plane, new Point3D(0, 0, 0));
    }

    public static int isPointOnObserverSide(Rectangle3D polygon2, Point3D point1) {
        double result = checkPointSideAgainstPlane(getPlaneFromPolygon(polygon2), point1);
        if (result > 0)
            return -1;
        else
            return 1;
    }

    private static double pointMatrixMultiplication(Matrix m, Point3D p) {
        return m.get(0, 0) * p.getX() + m.get(0, 1) * p.getY() + m.get(0, 2) * p.getZ() + m.get(0, 3);
    }

}
