module pl.gk.virtual_camera {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.junit.jupiter.api;
    requires Jama;
    requires jama;

    opens pl.gk.virtual_camera to javafx.fxml;
    exports pl.gk.virtual_camera;
    exports pl.gk.virtual_camera.model;
    opens pl.gk.virtual_camera.model to javafx.fxml;
    exports pl.gk.virtual_camera.logic;
    opens pl.gk.virtual_camera.logic to javafx.fxml;
}