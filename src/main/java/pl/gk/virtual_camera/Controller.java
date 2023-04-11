package pl.gk.virtual_camera;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Controller {

    private long keyCooldown = 5 * 10000000;
    private long lastTimePressed = 0;

    public Canvas canvas;

    private Processor processor;

    @FXML
    public void initialize(){
        Reader reader = new Reader();
        ArrayList<Rectangle3D> rectangle3DList = reader.readRectanglesData();
        processor = new Processor(rectangle3DList);
        processor.changeTranslation(105, "z");
        processor.changeTranslation(-105, "y");
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
        graphicsContext.setStroke(Color.WHITE);
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
        if(System.nanoTime() - lastTimePressed > keyCooldown){
            switch (keyEvent.getCode()){
                case W:
                    //welcomeText.setText("go forward");
                    processor.changeTranslation(-10, "z");
                    System.out.println("ess");
                    break;
                case S:
                    //welcomeText.setText("go back");
                    processor.changeTranslation(10, "z");
                    break;
                case A:
                    //welcomeText.setText("go left");
                    processor.changeTranslation(-10, "x");
                    break;
                case D:
                    //welcomeText.setText("go right");
                    processor.changeTranslation(10, "x");
                    break;
                case Q:
                    //welcomeText.setText("rotate left");
                    processor.changeRotation(30, "z");
                    break;
                case E:
                    //welcomeText.setText("rotate right");
                    processor.changeRotation(-30, "z");
                    break;
                case SHIFT:
                    //welcomeText.setText("zoom in");
                    processor.changeDistance(-15);
                    break;
                case CONTROL:
                    processor.changeDistance(15);
                    //welcomeText.setText("zoom out");
                    break;
                case I:
                    //welcomeText.setText("turn up");

                    processor.changeRotation(30, "x");
                    break;
                case J:
                    processor.changeRotation(-30, "y");
                    //welcomeText.setText("turn left");
                    break;
                case L:
                    processor.changeRotation(30, "y");
                    //welcomeText.setText("turn right");
                    break;
                case K:
                    //welcomeText.setText("turn down");
                    processor.changeRotation(-30, "x");
                    break;
            }
        }
        lastTimePressed = System.nanoTime();
        draw(processor.project());
    }
}