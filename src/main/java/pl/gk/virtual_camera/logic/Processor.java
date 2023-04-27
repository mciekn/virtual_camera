package pl.gk.virtual_camera.logic;

import pl.gk.virtual_camera.model.Point2D;
import pl.gk.virtual_camera.model.Point3D;
import pl.gk.virtual_camera.model.Rectangle2D;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;

public class Processor {

    private final ArrayList<Rectangle3D> rectangle3DList;
    private ArrayList<Rectangle2D> rectangle2DList;
    double distance = -200f;


    public Processor(ArrayList<Rectangle3D> rectangle3DList){
        this.rectangle3DList = rectangle3DList;
    }

    public ArrayList<Rectangle2D> project(){
        rectangle2DList = new ArrayList<>();
        for(Rectangle3D rectangle3D : rectangle3DList){
            Rectangle2D rectangle2D = new Rectangle2D();
            ArrayList<Point2D> point2DList = new ArrayList<>();

            for(Point3D point3D : rectangle3D.getPoint3DList()){
                double x = point3D.getX();
                double y = point3D.getY();
                double z = point3D.getZ();

                double xProjected = ((x * distance) / (z > 1 ? z : 1)) + 325;
                double yProjected = ((y * distance) / (z > 1 ? z : 1)) + 325;

                Point2D point2D = new Point2D(xProjected, yProjected);
                point2DList.add(point2D);
                System.out.println("Po zmianie x:" + (xProjected) + " y " + (yProjected));
            }
            rectangle2D.setPoint2DList(point2DList);
            rectangle2DList.add(rectangle2D);
        }
        return rectangle2DList;
    }

    public ArrayList<Rectangle2D> getRectangle2DList() {
        return rectangle2DList;
    }

    public void changeDistance(double change) {
        this.distance += change;
        if (this.distance < -1000) {
            this.distance = -1000;
        }
        if (this.distance > -20) {
            this.distance = -20;
        }
    }

    public void changeTranslation(double change, Axis axis) {
        for (Rectangle3D rectangle3D : this.rectangle3DList) {
            for (Point3D point3D : rectangle3D.getPoint3DList()) {
                switch (axis) {
                    case X -> point3D.setX(point3D.getX() + change);
                    case Y -> point3D.setY(point3D.getY() + change);
                    case Z -> point3D.setZ(point3D.getZ() + change);
                }
            }
        }
    }

    public void changeRotation(double change, Axis axis) {
        change = Math.toRadians(change);
        for (Rectangle3D rectangle3D : this.rectangle3DList) {
            for (Point3D point3D : rectangle3D.getPoint3DList()) {
                double x = point3D.getX();
                double y = point3D.getY();
                double z = point3D.getZ();
                switch (axis) {
                    case X -> {
                        point3D.setY(Math.cos(change) * y - Math.sin(change) * z);
                        point3D.setZ(Math.sin(change) * y + Math.cos(change) * z);
                    }
                    case Y -> {
                        point3D.setX(Math.cos(change) * x + Math.sin(change) * z);
                        point3D.setZ(-Math.sin(change) * x + Math.cos(change) * z);
                    }
                    case Z -> {
                        point3D.setX(Math.cos(change) * x - Math.sin(change) * y);
                        point3D.setY(Math.sin(change) * x + Math.cos(change) * y);
                    }
                }
            }
        }
    }

}
