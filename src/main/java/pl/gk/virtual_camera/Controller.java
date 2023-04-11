package pl.gk.virtual_camera;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Controller {

    public Canvas canvas = new Canvas();

    @FXML
    private Label welcomeText;

    @FXML
    public void initializeWindow(){
        Reader reader = new Reader();
        ArrayList<Rectangle3D> rectangle3DList = reader.readRectanglesData();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyTyped(this::keyEvent);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        initializeWindow();
    }


    @FXML
    public void keyEvent(KeyEvent keyEvent){
        System.out.println(keyEvent.getCode());
        switch (keyEvent.getCode()){
            case W:
                welcomeText.setText("go forward");
                break;
            case S:
                welcomeText.setText("go back");
                break;
            case A:
                welcomeText.setText("go left");
                break;
            case D:
                welcomeText.setText("go right");
                break;
            case Q:
                welcomeText.setText("rotate left");
                break;
            case E:
                welcomeText.setText("rotate right");
                break;
            case SHIFT:
                welcomeText.setText("zoom in");
                break;
            case CONTROL:
                welcomeText.setText("zoom out");
                break;
            case I:
                welcomeText.setText("turn up");
                break;
            case J:
                welcomeText.setText("turn left");
                break;
            case L:
                welcomeText.setText("turn right");
                break;
            case K:
                welcomeText.setText("turn down");
                break;
        }
    }
}