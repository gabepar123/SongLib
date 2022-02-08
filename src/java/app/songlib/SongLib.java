//Gabriel Parente
//Mohamed Smires


//TODO add names to top of every file

package app.songlib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.songlib.SongLibController;

import java.io.IOException;

public class SongLib extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SongLib.class.getResource("songlib.fxml"));

        AnchorPane root = (AnchorPane) fxmlLoader.load();
        //call start function in controller
        SongLibController songLibController = fxmlLoader.getController();
        songLibController.start(stage);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Song Library App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}