package pl.gk.virtual_camera;

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
}
