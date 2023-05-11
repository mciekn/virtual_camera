package pl.gk.virtual_camera.model;


import pl.gk.virtual_camera.logic.ObstructionDetector;

import java.util.ArrayList;

public class Rectangle3D implements Comparable<Rectangle3D>{
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

    public Point3D getCenter() {
        Point3D center = new Point3D(0, 0, 0);
        double midX = (point3DList.get(0).getX() + point3DList.get(1).getX() + point3DList.get(2).getX() + point3DList.get(3).getX()) / 4;
        double midY = (point3DList.get(0).getY() + point3DList.get(1).getY() + point3DList.get(2).getY() + point3DList.get(3).getY()) / 4;
        double midZ = (point3DList.get(0).getZ() + point3DList.get(1).getZ() + point3DList.get(2).getZ() + point3DList.get(3).getZ()) / 4;
        center.setX(midX);
        center.setY(midY);
        center.setZ(midZ);
        return center;
    }


    public void setPoint3DList(ArrayList<Point3D> point3DList){
        this.point3DList = point3DList;
    }

    @Override
    public int compareTo(Rectangle3D p) {
        return ObstructionDetector.isPointOnObserverSide(this, p.getCenter());
    }
}
