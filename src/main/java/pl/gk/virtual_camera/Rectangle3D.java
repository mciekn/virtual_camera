package pl.gk.virtual_camera;

import java.util.ArrayList;

public class Rectangle3D {
    ArrayList<Point3D> point3DList = new ArrayList<>();

    public Rectangle3D(Point3D pointA, Point3D pointB, Point3D pointC, Point3D pointD){
        point3DList.add(pointA);
        point3DList.add(pointB);
        point3DList.add(pointC);
        point3DList.add(pointD);
    }

    public Rectangle3D(){}

    public ArrayList<Point3D> getPoint3DList(){
        return point3DList;
    }

    public void setPoint3DList(ArrayList<Point3D> point3DList){
        this.point3DList = point3DList;
    }
}
