//Gabriel Parente
//Mohamed Smires



package app.songlib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.songlib.SongLibController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SongLib extends Application {

    SongLibController controller;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SongLib.class.getResource("songlib.fxml"));

        AnchorPane root = (AnchorPane) fxmlLoader.load();
        //call start function in controller
        controller = fxmlLoader.getController();
        controller.start(stage);

        Scene scene = new Scene(root);
        stage.setTitle("Song Library App");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() { //Called when application is closed
        saveData(); //save data before closing
    }

    public void saveData(){
        //Create file
        File file = new File("src/resources/songs.txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (Song song : controller.songList) {
                System.out.println("saving song: " + song.name);
                writer.write(song.toSaveString());
            }
            writer.close();
            System.out.println("Saved data and stopping application");
        } catch (IOException e) {
            System.out.println("Error saving data... Moving on");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}