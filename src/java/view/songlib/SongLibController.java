//Gabriel Parente
//Mohammed Smires


package view.songlib;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SongLibController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}