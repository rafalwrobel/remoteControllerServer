module remoteControllerServer {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports pl.praktyki to javafx.graphics;
    opens pl.praktyki to javafx.fxml;

}