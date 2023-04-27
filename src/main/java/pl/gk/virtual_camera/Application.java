package pl.gk.virtual_camera;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("hello-view.fxml")));
        stage.setTitle("Virtual Camera");
        stage.setScene(new Scene(root, 650, 650));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}