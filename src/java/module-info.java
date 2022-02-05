module app.songlib {
    requires javafx.controls;
    requires javafx.fxml;


    opens app.songlib to javafx.fxml;
    exports app.songlib;
    exports view.songlib;
    opens view.songlib to javafx.fxml;
}