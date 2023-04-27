package pl.gk.virtual_camera.model;

import java.util.ArrayList;

public class Rectangle2D {
    private ArrayList<Point2D> point2DList = new ArrayList<>();

    public Rectangle2D(Point2D pointA, Point2D pointB, Point2D pointC, Point2D pointD){
        point2DList.add(pointA);
        point2DList.add(pointB);
        point2DList.add(pointC);
        point2DList.add(pointD);
    }

    public Rectangle2D(){}

    public ArrayList<Point2D> getPoint2DList(){
        return point2DList;
    }

    public void setPoint2DList(ArrayList<Point2D> point2DList){
        this.point2DList = point2DList;
    }

    public boolean contains(Point2D point) {
        // Check if the point is inside the rectangle
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;

        for (Point2D point2D : point2DList) {
            minX = Math.min(minX, point2D.getX());
            minY = Math.min(minY, point2D.getY());
            maxX = Math.max(maxX, point2D.getX());
            maxY = Math.max(maxY, point2D.getY());
        }

        return (point.getX() > minX && point.getX() < maxX &&
                point.getY() > minY && point.getY() < maxY);
    }

}
