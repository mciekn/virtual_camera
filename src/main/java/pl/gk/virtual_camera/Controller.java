package pl.gk.virtual_camera;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {

    private long lastTimeKeyPressed = 0;

    public Canvas canvas;

    private Processor processor;

    private static double dist;

    @FXML
    public void initialize(){
        Reader reader = new Reader();
        ArrayList<Rectangle3D> rectangle3DList = reader.readRectanglesData();
        processor = new Processor(rectangle3DList);
        processor.changeTranslation(105, Axis.Z);
        processor.changeTranslation(-105, Axis.Y);
        dist = processor.distance;
        processor.project();
        draw(processor.getRectangle2DList());

        canvas.setOnMouseClicked(e -> {
            double x = e.getX() - 325.0;
            double y = e.getY() - 325.0;
            System.out.println("[" + x +", " + y + "]");
        });


        canvas.setFocusTraversable(true);
        canvas.setOnKeyTyped(this::keyEvent);
    }


    public void draw(ArrayList<Rectangle2D> rectangle2DList){
        System.out.println("drawing...");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 650, 650);
        graphicsContext.setStroke(Color.MEDIUMSEAGREEN);
        graphicsContext.beginPath();
        for(Rectangle2D rectangle2D : rectangle2DList){

            int i = 0;
            double startingPointX = 0;
            double startingPointY = 0;
            /*
            for(Point2D point2D : rectangle2D.getPoint2DList()){
                System.out.println(i);
                if(i==0){
                    startingPointX = point2D.getX();
                    startingPointY = point2D.getY();
                    graphicsContext.moveTo(point2D.getX(), point2D.getY());
                }
                else{
                    graphicsContext.lineTo(point2D.getX(), point2D.getY());
                }
                i++;
            }
            graphicsContext.lineTo(startingPointX, startingPointY);
            graphicsContext.stroke();
            */
            //==========FILLING==RECTANGLES========================================

            List<Double> pointsX = new ArrayList();
            List<Double> pointsY = new ArrayList();

            for(Point2D point2D : rectangle2D.getPoint2DList()){
                pointsX.add(point2D.getX());
                pointsY.add(point2D.getY());
            }


            double[] arrX = pointsX.stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();

            double[] arrY = pointsY.stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();

            System.out.println("LEN" + pointsX.size());



            if(pointsX.size() > 2){
                System.out.println(rectangle2DList.indexOf(rectangle2D));
                if(rectangle2DList.indexOf(rectangle2D) % 2 == 0){
                    graphicsContext.setFill(Color.RED);
                }
                else {
                    graphicsContext.setFill(Color.BLUE);
                }

                graphicsContext.fillPolygon(arrX, arrY, 4);
            }
            graphicsContext.setFill(Color.BLACK);




        }
    }

    public void keyEvent(KeyEvent keyEvent){
        System.out.println(keyEvent.getCode());
        long COOLDOWN = 50000000;
        if(System.nanoTime() - lastTimeKeyPressed > COOLDOWN){
            switch (keyEvent.getCode()) {
                case W -> processor.changeTranslation(-10, Axis.Z);
                case S -> processor.changeTranslation(10, Axis.Z);
                case A -> processor.changeTranslation(-10, Axis.X);
                case D -> processor.changeTranslation(10, Axis.X);
                case R -> processor.changeTranslation(-10, Axis.Y);
                case F -> processor.changeTranslation(10, Axis.Y);
                case Q -> processor.changeRotation(10, Axis.Z);
                case E -> processor.changeRotation(-10, Axis.Z);
                case SHIFT -> processor.changeDistance(-15);
                case CONTROL -> processor.changeDistance(15);
                case I -> processor.changeRotation(10, Axis.X);
                case J -> processor.changeRotation(-10, Axis.Y);
                case L -> processor.changeRotation(10, Axis.Y);
                case K -> processor.changeRotation(-10, Axis.X);
            }
        }
        lastTimeKeyPressed = System.nanoTime();
        dist = processor.distance;
        draw(processor.project());

    }

    static class RectangleComparator implements Comparator<Rectangle3D>
    {
        @Override
        public int compare(Rectangle3D rectangleQ, Rectangle3D rectangleP){
            if(isOnTheOtherSideOfQ(rectangleQ, rectangleP)
            || isOnTheSameSideOfP(rectangleQ, rectangleP)
            || doesProjectionExcludeCovering(projectTo2D(rectangleQ), projectTo2D(rectangleP))){
                System.out.println("true");
                return 1;
            }
            else {
                System.out.println("false");
                return -1;
            }
        }
    }


    public static boolean doesProjectionExcludeCovering(Rectangle2D rectangleQ, Rectangle2D rectangleP){
        for(Point2D point2D : rectangleQ.getPoint2DList()){
            int isInsideValue = checkInside(rectangleP.getPoint2DList(), 4, point2D);
            if(isInsideValue == 1){
                return false;
            }
        }

        for(Point2D point2D : rectangleP.getPoint2DList()){
            int isInsideValue = checkInside(rectangleQ.getPoint2DList(), 4, point2D);
            if(isInsideValue == 1){
                return false;
            }
        }
        return true;
    }

    public static boolean isOnTheOtherSideOfQ(Rectangle3D rectangleQ, Rectangle3D rectangleP){
        for(Point3D point3D : rectangleP.getPoint3DList()){
            for(Point3D point3DQ : rectangleQ.getPoint3DList()){
                if(point3D.getZ() <= point3DQ.getZ()){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isOnTheSameSideOfP(Rectangle3D rectangleQ, Rectangle3D rectangleP){
        for(Point3D point3D : rectangleQ.getPoint3DList()){
            for(Point3D point3DP : rectangleP.getPoint3DList()){
                if(point3D.getZ() >= point3DP.getZ()){
                    return false;
                }
            }
        }
        return true;
    }

    public static Rectangle2D projectTo2D(Rectangle3D rectangle3D){
        Rectangle2D rectangle2D = new Rectangle2D();
        ArrayList<Point2D> point2DList = new ArrayList<>();

        for(Point3D point3D : rectangle3D.getPoint3DList()){
            double x = point3D.getX();
            double y = point3D.getY();
            double z = point3D.getZ();

            double xProjected = ((x * dist) / (z > 1 ? z : 1)) + 325;
            double yProjected = ((y * dist) / (z > 1 ? z : 1)) + 325;

            Point2D point2D = new Point2D(xProjected, yProjected);
            point2DList.add(point2D);
            System.out.println("Po zmianie x:" + (xProjected) + " y " + (yProjected));
        }
        rectangle2D.setPoint2DList(point2DList);

        return rectangle2D;
    }


    public static class Line {
        public Point2D p1, p2;
        public Line(Point2D p1, Point2D p2)
        {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    static int onLine(Line l1, Point2D p)
    {
        // Check whether p is on the line or not
        if (p.getX() <= Math.max(l1.p1.getX(), l1.p2.getX())
                && p.getX() <= Math.min(l1.p1.getX(), l1.p2.getX())
                && (p.getY() <= Math.max(l1.p1.getY(), l1.p2.getY())
                && p.getY() <= Math.min(l1.p1.getY(), l1.p2.getY())))
            return 1;

        return 0;
    }

    static int direction(Point2D a, Point2D b, Point2D c)
    {
        double val = (b.getY() - a.getY()) * (c.getX() - b.getX())
                - (b.getX() - a.getX()) * (c.getY() - b.getY());

        if (val == 0)

            // Collinear
            return 0;

        else if (val < 0)

            // Anti-clockwise direction
            return 2;

        // Clockwise direction
        return 1;
    }

    static int isIntersect(Line l1, Line l2)
    {
        // Four direction for two lines and points of other
        // line
        int dir1 = direction(l1.p1, l1.p2, l2.p1);
        int dir2 = direction(l1.p1, l1.p2, l2.p2);
        int dir3 = direction(l2.p1, l2.p2, l1.p1);
        int dir4 = direction(l2.p1, l2.p2, l1.p2);

        // When intersecting
        if (dir1 != dir2 && dir3 != dir4)
            return 1;

        // When p2 of line2 are on the line1
        if (dir1 == 0 && onLine(l1, l2.p1) == 1)
            return 1;

        // When p1 of line2 are on the line1
        if (dir2 == 0 && onLine(l1, l2.p2) == 1)
            return 1;

        // When p2 of line1 are on the line2
        if (dir3 == 0 && onLine(l2, l1.p1) == 1)
            return 1;

        // When p1 of line1 are on the line2
        if (dir4 == 0 && onLine(l2, l1.p2) == 1)
            return 1;

        return 0;
    }

    static int checkInside(ArrayList<Point2D> poly, int n, Point2D p)
    {

        // When polygon has less than 3 edge, it is not
        // polygon

        if (n < 3)
            return 0;

        // Create a point at infinity, y is same as point p
        Point2D pt = new Point2D(9999, p.getY());
        Line exline = new Line(p, pt);
        int count = 0;
        int i = 0;
        do {

            // Forming a line from two consecutive points of
            // poly
            Line side
                    = new Line(poly.get(i), poly.get((i + 1) % n));
            if (isIntersect(side, exline) == 1) {

                // If side is intersects exline
                if (direction(side.p1, p, side.p2) == 0)
                    return onLine(side, p);
                count++;
            }
            i = (i + 1) % n;
        } while (i != 0);

        // When count is odd
        return count & 1;
    }
}