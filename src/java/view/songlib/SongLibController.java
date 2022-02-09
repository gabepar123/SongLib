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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class SongLibController implements Initializable {
    @FXML Label songListLabel;

    FileChooser fileChooser = new FileChooser();

    public List<Song> songList = new ArrayList<>(); //Made public to be accessible by saveData command.

    ObservableList<Song> obsSongList;

    @FXML private ListView<Song> songListView;

    @FXML private TextField songNameTF;

    @FXML private TextField artistNameTF;

    @FXML
    private TextField albumNameTF;

    @FXML
    private TextField yearNameTF;

    //todo gonna rename these to selectedAlbumLabel for clarity probably
    @FXML private Label selectedAlbum;

    @FXML private Label selectedArtist;

    @FXML private Label selectedSong;

    @FXML private Label selectedYear;

    Song currSong; // made global to support removal

    public void start(Stage stage){
        // TODO load songList from file here
        obsSongList = FXCollections.observableArrayList(songList);
        songListView.setItems(obsSongList);

        if (!songList.isEmpty()){
            songListView.getSelectionModel().select(0);
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
        songNameTF.clear();
        artistNameTF.clear();

        try {
            Song s = new Song(songName, artistName);
            songList.add(s);
            //select new song
            songListView.getSelectionModel().select(s);
            currSong = s;

            updateSongList();

        } catch (IllegalArgumentException e) {
            System.out.println("Bad Input!");
            //todo turn this into error popup
        }


    }


    @FXML
    protected void onDeleteButtonClick(ActionEvent event){ //TODO: delete song from library
        System.out.println("Delete Button was clicked!");
        //TODO: add error pop up if we try to delete a song when there are none?

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
        String yearString = yearNameTF.getText();

        Song s;
        try { //todo probably just make one constructor call tbh
            if (albumName.length() == 0 || yearString.length() == 0) {
                s = new Song(songName, artistName);
            }
            else {
                int year = Integer.parseInt(yearString);
                s = new Song(songName, artistName, albumName, year);
            }

            songList.remove(currSong);
            songList.add(s);
            songListView.getSelectionModel().select(s);
            currSong = s;

            updateSongList();
        } catch (IllegalArgumentException e) {
            System.out.println("Bad Input!");
            //todo turn this into error popup
        }

    }

    private void showSelectedItem(Stage stage){
        System.out.println("here");
        currSong = songListView.getSelectionModel().getSelectedItem();
        if (currSong == null) return; //if there is no songs in the list
        selectedSong.setText(currSong.name);
        selectedArtist.setText(currSong.artist);
        //selectedAlbum.setText(currSong.album); //todo set year and album
        //selectedYear.setText(String.valueOf(currSong.year));

    }

    //call this after adding or removing a song from songList
    private void updateSongList(){
        Collections.sort(songList);
        obsSongList = FXCollections.observableArrayList(songList);
        songListView.setItems(obsSongList);

    }


    //Called when controller is initialized automatically
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FIXME: Check if directory is correct!
        File file = new File("src/resources/songs.txt");

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) { //TODO: Lets try to have each line be a song
                String line = scanner.nextLine();
                //Split string somehow
                String[] split = line.split("\\|");
                String songName = split[0];
                String artistName = split[1];
                String albumName = split[2];
                int year = Integer.parseInt(split[3]);
                Song s = new Song(songName, artistName, albumName, year);
                songList.add(s);
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found! Moving on without loading songs.");
        }

    }
}