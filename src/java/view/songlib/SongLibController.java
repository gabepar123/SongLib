//Gabriel Parente
//Mohammed Smires


package view.songlib;

import app.songlib.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class SongLibController {
    @FXML Label songListLabel;

    List<Song> songList = new ArrayList<>();

    @FXML private ListView<Song> songListView;

    @FXML private TextField songNameTF;

    @FXML private TextField artistNameTF;

    @FXML private Label selectedAlbum;

    @FXML private Label selectedArtist;

    @FXML private Label selectedSong;

    @FXML private Label selectedYear;




    //todo Add confirmation message
    @FXML
    protected void onAddButtonClick(ActionEvent event) { //TODO: add song to library
        String songName = songNameTF.getText();
        String artistName = artistNameTF.getText();
        songNameTF.clear();
        artistNameTF.clear();

        try {
            Song s = new Song(songName, artistName);
            songList.add(s);

            updateSongList();

        } catch (IllegalArgumentException e) {
            System.out.println("Bad Input!");
            //todo turn this into error popup
        }


    }


    @FXML
    protected void onDeleteButtonClick(ActionEvent event){ //TODO: delete song from library
        System.out.println("Delete Button was clicked!");
    }

    @FXML
    protected void onEditButtonClick(ActionEvent event){ //TODO: edit song in library
        System.out.println("Edit Button was clicked!");
    }
    @FXML
    protected void onSongListViewClick(MouseEvent event){
        Song s = songListView.getSelectionModel().getSelectedItem();
        if (s == null) return; //if there is no songs in the list
        selectedSong.setText(s.name);
        selectedArtist.setText(s.artist);
        //selectedAlbum.setText(s.album);
        //selectedYear.setText(String.valueOf(s.year));

    }
    //call this after adding or removing a song from songList
    private void updateSongList(){
        Collections.sort(songList);
        ObservableList<Song> obSongList = FXCollections.observableList(songList);
        songListView.setItems(obSongList);

    }


}