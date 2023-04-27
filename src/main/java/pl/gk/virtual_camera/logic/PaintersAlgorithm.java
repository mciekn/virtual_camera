package pl.gk.virtual_camera.logic;

import javafx.scene.shape.Shape;
import pl.gk.virtual_camera.model.Point2D;
import pl.gk.virtual_camera.model.Point3D;
import pl.gk.virtual_camera.model.Rectangle2D;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PaintersAlgorithm {

    ArrayList<Rectangle2D> rectangle2DList;
    ArrayList<Rectangle3D> rectangle3DList;


    public void quickSort(ArrayList<Rectangle3D> rectangle3DList, Processor processor) {
        if (rectangle3DList.size() <= 1) {
            return;
        }
        System.out.println("sorting...");

        Rectangle3D pivot = rectangle3DList.get(0);
        ArrayList<Rectangle3D> lessThanPivot = new ArrayList<>();
        ArrayList<Rectangle3D> greaterThanPivot = new ArrayList<>();

        for (int i = 1; i < rectangle3DList.size(); i++) {
            Rectangle3D current = rectangle3DList.get(i);
            boolean isLessThanPivot = !runTests(pivot, current, processor);
            if (isLessThanPivot) {
                lessThanPivot.add(current);
            } else {
                greaterThanPivot.add(current);
            }
        }

        quickSort(lessThanPivot, processor);
        quickSort(greaterThanPivot, processor);

        rectangle3DList.clear();
        rectangle3DList.addAll(lessThanPivot);
        rectangle3DList.add(pivot);
        rectangle3DList.addAll(greaterThanPivot);
    }




    public static boolean runTests(Rectangle3D rect_Q, Rectangle3D rect_P, Processor processor){
        Rectangle2D rect_Q2D = processor.projectTo2D(rect_Q);
        Rectangle2D rect_P2D = processor.projectTo2D(rect_P);
        boolean firstStep = doShapeRectangleBoundsInterfere(rect_Q2D, rect_P2D);
        boolean secondStep = doShapesInterfere(rect_Q2D, rect_P2D);
        boolean thirdStep = isOnOppositeSide(rect_Q, rect_P);
        boolean fourthStep = isOnSameSide(rect_Q, rect_P);

        boolean result = firstStep || secondStep || thirdStep || fourthStep;

        return !result;
    }

    public static boolean doShapeRectangleBoundsInterfere(Rectangle2D rect_Q, Rectangle2D rect_P){
        Rectangle2D rect_Q_bounds = getBounds(rect_Q);
        Rectangle2D rect_P_bounds = getBounds(rect_P);

        if(intersect(rect_Q_bounds, rect_P_bounds)){
            return true;
        }

        return false;
    }

    public static boolean doShapesInterfere(Rectangle2D rect_Q, Rectangle2D rect_P){
        if(intersect(rect_Q, rect_P)){
            return true;
        }
        return false;
    }

    public static boolean isOnOppositeSide(Rectangle3D rect_Q, Rectangle3D rect_P){
        Point3D p1 = rect_Q.getPoint3DList().get(0);
        Point3D p2 = rect_Q.getPoint3DList().get(1);
        Point3D p3 = rect_Q.getPoint3DList().get(2);

        Point3D vector_1 = new Point3D(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());
        Point3D vector_2 = new Point3D(p3.getX() - p1.getX(), p3.getY() - p1.getY(), p3.getZ() - p1.getZ());

        Point3D vector_normal = new Point3D(vector_1.getY()*vector_2.getZ() - vector_1.getZ()*vector_2.getY(),
                vector_1.getZ()*vector_2.getX() - vector_1.getX()*vector_2.getZ(),
                vector_1.getX()*vector_2.getY() - vector_1.getY()*vector_2.getX());

        for (Point3D point3D : rect_P.getPoint3DList()){
            Point3D vector = new Point3D(point3D.getX(), point3D.getY(), point3D.getZ());
            double dotProduct = vector.getX() * vector_normal.getX() +
                                vector.getY() * vector_normal.getY() +
                                vector.getZ() * vector_normal.getZ();

            if (dotProduct > 0){
                return false;
            }
        }

        return true;
    }

    public static boolean isOnSameSide(Rectangle3D rect_Q, Rectangle3D rect_P){
        Point3D p1 = rect_Q.getPoint3DList().get(0);
        Point3D p2 = rect_Q.getPoint3DList().get(1);
        Point3D p3 = rect_Q.getPoint3DList().get(2);

        Point3D vector_1 = new Point3D(p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ());
        Point3D vector_2 = new Point3D(p3.getX() - p1.getX(), p3.getY() - p1.getY(), p3.getZ() - p1.getZ());

        Point3D vector_normal = new Point3D(vector_1.getY()*vector_2.getZ() - vector_1.getZ()*vector_2.getY(),
                vector_1.getZ()*vector_2.getX() - vector_1.getX()*vector_2.getZ(),
                vector_1.getX()*vector_2.getY() - vector_1.getY()*vector_2.getX());

        for (Point3D point3D : rect_P.getPoint3DList()){
            Point3D vector = new Point3D(point3D.getX(), point3D.getY(), point3D.getZ());
            double dotProduct = vector.getX() * vector_normal.getX() +
                    vector.getY() * vector_normal.getY() +
                    vector.getZ() * vector_normal.getZ();

            if (dotProduct > 0){
                return true;
            }
        }

        return false;
    }

    private static Rectangle2D getBounds(Rectangle2D rectangle2D){
        double left = Double.MAX_VALUE;
        double top = Double.MAX_VALUE;
        double right = Double.MIN_VALUE;
        double bottom = Double.MIN_VALUE;

        for(Point2D point2D : rectangle2D.getPoint2DList()){
            left = Math.min(left, point2D.getX());
            top = Math.min(top, point2D.getY());
            right = Math.max(right, point2D.getX());
            bottom = Math.max(bottom, point2D.getY());
        }

        return new Rectangle2D(new Point2D(left, top), new Point2D(right, top), new Point2D(right, bottom), new Point2D(left, bottom));
    }

    private static boolean intersect(Rectangle2D rect_Q, Rectangle2D rect_P){
        for(Point2D point2D : rect_Q.getPoint2DList()){
            if(rect_P.contains(point2D)){
                return true;
            }
        }

        for(Point2D point2D : rect_P.getPoint2DList()){
            if(rect_Q.contains(point2D)){
                return true;
            }
        }

        return false;
    }
}
