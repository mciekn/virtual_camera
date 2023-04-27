package pl.gk.virtual_camera.logic;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import pl.gk.virtual_camera.model.Point2D;
import pl.gk.virtual_camera.model.Rectangle2D;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private long lastTimeKeyPressed = 0;

    public Canvas canvas;

    private Processor processor;

    @FXML
    public void initialize(){
        Reader reader = new Reader();
        ArrayList<Rectangle3D> rectangle3DList = reader.readRectanglesData();
        processor = new Processor(rectangle3DList);
        processor.changeTranslation(105, Axis.Z);
        processor.changeTranslation(-105, Axis.Y);
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

            List<Double> pointsX = new ArrayList<>();
            List<Double> pointsY = new ArrayList<>();

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
                graphicsContext.setFill(Color.MEDIUMSEAGREEN);
                graphicsContext.fillPolygon(arrX, arrY, 4);
                graphicsContext.setFill(Color.BLACK);
            }
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
        draw(processor.project());
    }
}