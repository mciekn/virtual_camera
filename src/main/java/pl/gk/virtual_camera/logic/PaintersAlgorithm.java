package pl.gk.virtual_camera.logic;

import pl.gk.virtual_camera.model.Point2D;
import pl.gk.virtual_camera.model.Rectangle2D;

public class PaintersAlgorithm {

    public static boolean doShapeRectangleBoundsExcludeInterference(Rectangle2D rect_Q, Rectangle2D rect_P){
        Rectangle2D rect_Q_bounds = getBounds(rect_Q);
        Rectangle2D rect_P_bounds = getBounds(rect_P);

        return !intersect(rect_Q_bounds, rect_P_bounds);
    }

    public static boolean doShapesExcludeInterference(Rectangle2D rect_Q, Rectangle2D rect_P){
        return !intersect(rect_Q, rect_P);
    }

    private static Rectangle2D getBounds(Rectangle2D rectangle2D){
        double left = Double.MAX_VALUE;
        double top = Double.MIN_VALUE;
        double right = Double.MIN_VALUE;
        double bottom = Double.MAX_VALUE;

        for(Point2D point2D : rectangle2D.getPoint2DList()){
            left = Math.min(left, point2D.getX());
            top = Math.max(top, point2D.getY());
            right = Math.max(right, point2D.getX());
            bottom = Math.min(bottom, point2D.getY());
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
