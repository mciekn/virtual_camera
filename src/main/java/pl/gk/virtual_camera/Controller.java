package pl.gk.virtual_camera;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    private long keyCooldown = 5 * 10000000;
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
        }
    }

    public void keyEvent(KeyEvent keyEvent){
        System.out.println(keyEvent.getCode());
        if(System.nanoTime() - lastTimeKeyPressed > keyCooldown){
            switch (keyEvent.getCode()){
                case W:
                    processor.changeTranslation(-10, Axis.Z);
                    break;
                case S:
                    processor.changeTranslation(10, Axis.Z);
                    break;
                case A:
                    processor.changeTranslation(-10, Axis.X);
                    break;
                case D:
                    processor.changeTranslation(10, Axis.X);
                    break;
                case Q:
                    processor.changeRotation(10, Axis.Z);
                    break;
                case E:
                    processor.changeRotation(-10, Axis.Z);
                    break;
                case SHIFT:
                    processor.changeDistance(-15);
                    break;
                case CONTROL:
                    processor.changeDistance(15);
                    break;
                case I:
                    processor.changeRotation(10, Axis.X);
                    break;
                case J:
                    processor.changeRotation(-10, Axis.Y);
                    break;
                case L:
                    processor.changeRotation(10, Axis.Y);
                    break;
                case K:
                    processor.changeRotation(-10, Axis.X);
                    break;
            }
        }
        lastTimeKeyPressed = System.nanoTime();
        draw(processor.project());
    }
}