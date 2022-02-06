//Gabriel Parente
//Mohammed Smires


package view.songlib;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SongLibController {
    @FXML Label songListLabel;

    @FXML
    protected void onHelloButtonClick() {
        songListLabel.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onAddButtonClick(ActionEvent event){ //TODO: add song to library
        System.out.println("Add Button was clicked!");
    }

    @FXML
    protected void onDeleteButtonClick(ActionEvent event){ //TODO: delete song from library
        System.out.println("Delete Button was clicked!");
    }

    @FXML
    protected void onEditButtonClick(ActionEvent event){ //TODO: edit song in library
        System.out.println("Edit Button was clicked!");
    }

}