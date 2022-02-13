//Gabriel Parente
//Mohamed Smires


package view.songlib;

import app.songlib.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class SongLibController {
    @FXML Label songListLabel;

    public List<Song> songList = new ArrayList<>(); //Made public to be accessible by saveData command.

    ObservableList<Song> obsSongList;

    @FXML private ListView<Song> songListView;

    @FXML private TextField songNameTF;

    @FXML private TextField artistNameTF;

    @FXML
    private TextField albumNameTF;

    @FXML
    private TextField yearNameTF;

    @FXML private Label selectedAlbum;

    @FXML private Label selectedArtist;

    @FXML private Label selectedSong;

    @FXML private Label selectedYear;

    Song currSong; // made global to support removal

    Stage stage;

    public void start(Stage stage){

        this.stage = stage;
        loadSongs();

        obsSongList = FXCollections.observableArrayList(songList);
        songListView.setItems(obsSongList);

        if (!songList.isEmpty()){
            songListView.getSelectionModel().select(0);
            currSong = songListView.getSelectionModel().getSelectedItem();
        }

        songListView
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                        (obs, oldVal, newVal) ->
                                showSelectedItem(stage)
                );

    }



    //todo Add confirmation message
    //todo check for duplicate names
    @FXML
    protected void onAddButtonClick(ActionEvent event) { //TODO: add song to library
        String songName = songNameTF.getText();
        String artistName = artistNameTF.getText();
        String albumName = albumNameTF.getText();
        String yearString = yearNameTF.getText();

        songNameTF.clear();
        artistNameTF.clear();
        albumNameTF.clear();
        yearNameTF.clear();

        //todo check when i should clear text if they CANCEL
        if (!confirmAction("Add")){
            return;
        }

        try {
            Song s;

            if (yearString.length() != 0) {
                int year = Integer.parseInt(yearString.strip());
                s = new Song(songName, artistName, albumName, year);
            }
            else
                s = new Song(songName, artistName, albumName);

            if (songExists(s)) return;

            songList.add(s);
            //select new song
            songListView.getSelectionModel().select(s);
            currSong = s;

            updateSongList();

        } catch (Exception e) {
            System.out.println("Bad Input! on add");
            showError("Bad input on add!");
        }


    }

    @FXML
    protected void onDeleteButtonClick(ActionEvent event){ //TODO: delete song from library
        System.out.println("Delete Button was clicked!");
        //TODO: add error pop up if we try to delete a song when there are none?

        //todo check when i should clear text if they CANCEL
        if (!confirmAction("Delete")){
            return;
        }

        if (currSong == null){
            System.out.println("No song selected");
            showError("No Song Selected!");
            return;
        }

        int currSongIndex = songListView.getSelectionModel().getSelectedIndex();

        songList.remove(currSong); //remove the song from the list
        //clear text
        selectedSong.setText("");
        selectedArtist.setText("");
        selectedAlbum.setText("");
        selectedYear.setText("");

        //update the listView
        updateSongList();

        //todo clean this up maybe?
        //selects the next song after deleting
        if (currSongIndex >= songList.size()){
            currSongIndex--;
        }
        if (currSongIndex >= 0){
            songListView.getSelectionModel().select(currSongIndex);
            currSong = songListView.getSelectionModel().getSelectedItem();
        }
        else
            currSong = null; //empty list



    }

    //check for duplicate names
    @FXML
    protected void onEditButtonClick(ActionEvent event){ //TODO: edit song in library
        System.out.println("Edit Button was clicked!");


        String songName = songNameTF.getText();

        String artistName = artistNameTF.getText();

        String albumName = albumNameTF.getText();
        if (albumName.length() == 0){
            albumName = currSong.album;
        }

        String yearString = yearNameTF.getText();
        if (yearString.length() == 0){
            yearString = String.valueOf(currSong.year);
        }

        songNameTF.clear();
        artistNameTF.clear();
        albumNameTF.clear();
        yearNameTF.clear();

        //todo check when i should clear text if they CANCEL
        if (!confirmAction("Edit")){
            return;
        }

        try {
            Song s;

            if (yearString.length() != 0 && Integer.parseInt(yearString.strip()) != 0) {
                int year = Integer.parseInt(yearString.strip());
                s = new Song(songName, artistName, albumName, year);
            }
            else
                s = new Song(songName, artistName, albumName);

            if (songExists(s)) return;

            songList.remove(currSong);
            songList.add(s);
            songListView.getSelectionModel().select(s);
            currSong = s;

            updateSongList();


        } catch (Exception e) {
            System.out.println("Bad input on edit!");
            showError("Bad input on edit!");
            //todo turn this into error popup
        }

    }

    private void showSelectedItem(Stage stage){
        currSong = songListView.getSelectionModel().getSelectedItem();
        if (currSong == null) return; //if there is no songs in the list
        selectedSong.setText(currSong.name);
        songNameTF.setText(currSong.name);
        selectedArtist.setText(currSong.artist);
        artistNameTF.setText(currSong.artist);
        selectedAlbum.setText(currSong.album); //todo set year and album
        albumNameTF.setText(currSong.album);
        String yearString = String.valueOf(currSong.year);
        if (yearString.equals("0")) {
            selectedYear.setText("");
            yearNameTF.setText("");
        }
        else {
            selectedYear.setText(yearString);
            yearNameTF.setText(yearString);
        }
    }

    //call this after adding or removing a song from songList
    private void updateSongList(){
        Collections.sort(songList);
        obsSongList = FXCollections.observableArrayList(songList);
        songListView.setItems(obsSongList);

    }


    private void loadSongs() {
        //FIXME: Check if directory is correct!
        File file = new File("src/resources/songs.txt");

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] split = line.split("\\|");
                String songName = split[0];
                String artistName = split[1];
                String albumName = split[2];
                int year = Integer.parseInt(split[3]);
                Song s;
                if (year != 0) {
                    s = new Song(songName, artistName, albumName, year);
                }
                else
                    s = new Song(songName, artistName, albumName);

                songList.add(s);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found! Moving on without loading songs.");
        }
    }

    private boolean confirmAction(String action){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Are you sure you want to " + action + " this song?");
        alert.setContentText("Press OK to confirm.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }

    private void showError(String error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Error!");
        alert.setHeaderText("Error");
        alert.setContentText(error);
        alert.showAndWait();

    }

    private boolean songExists(Song s) {
        //iterate through songList to check for song with same name and artist
        for (Song song : songList) {
            if (song.compareTo(s) == 0) {
                showError("Song already exists");
                return true;
            }
        }
        return false;
    }

}