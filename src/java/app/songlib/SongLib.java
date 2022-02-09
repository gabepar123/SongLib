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

import java.io.*;
import java.util.List;


public class SongLib extends Application {


    SongLibController controller;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SongLib.class.getResource("songlib.fxml"));

        AnchorPane root = (AnchorPane) fxmlLoader.load();
        //call start function in controller
        controller = fxmlLoader.getController();
        controller.start(stage);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Song Library App");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() { //Called when application is closed

        saveData(); //save data before closing

        System.out.println("Stopping");
    }

    //FIXME: Seems to work, test some more
    public void saveData(){

        //Create file
        File file = new File("src/resources/songs.txt");
        try {
            FileWriter writer = new FileWriter(file);
            //Save a line to the file for each song
            for (Song song : controller.songList) { //Save each song to the file
                System.out.println("saving song: " + song.name);
                writer.write(song.toSaveString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}