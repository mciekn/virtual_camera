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


    @FXML
    public void keyEvent(KeyEvent keyEvent){
        System.out.println(keyEvent.getCode());
        switch (keyEvent.getCode()){
            case W:
                //welcomeText.setText("go forward");
                break;
            case S:
                //welcomeText.setText("go back");
                break;
            case A:
                //welcomeText.setText("go left");
                break;
            case D:
                //welcomeText.setText("go right");
                break;
            case Q:
                //welcomeText.setText("rotate left");
                break;
            case E:
                //welcomeText.setText("rotate right");
                break;
            case SHIFT:
                //welcomeText.setText("zoom in");
                break;
            case CONTROL:
                //welcomeText.setText("zoom out");
                break;
            case I:
                //welcomeText.setText("turn up");
                break;
            case J:
                //welcomeText.setText("turn left");
                break;
            case L:
                //welcomeText.setText("turn right");
                break;
            case K:
                //welcomeText.setText("turn down");
                break;
        }
    }
}