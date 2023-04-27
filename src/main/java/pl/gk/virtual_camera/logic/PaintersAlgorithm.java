package pl.gk.virtual_camera.logic;

import javafx.scene.shape.Shape;
import pl.gk.virtual_camera.model.Point2D;
import pl.gk.virtual_camera.model.Rectangle2D;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;

public class PaintersAlgorithm {

    ArrayList<Rectangle2D> rectangle2DList;
    ArrayList<Rectangle3D> rectangle3DList;

    public boolean doShapeRectangleBoundsInterfere(Rectangle2D rect_Q, Rectangle2D rect_P){
        Rectangle2D rect_Q_bounds = getBounds(rect_Q);
        Rectangle2D rect_P_bounds = getBounds(rect_P);

        if(intersect(rect_Q_bounds, rect_P_bounds)){
            return true;
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
