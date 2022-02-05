package app.songlib;

//TODO check how this reacts to empty strings and how JavaFX takes integers for the year
public class Song {

    public String name;
    public String artist;
    public String album;
    public int year;

    public Song(String name, String artist) throws IllegalArgumentException {

        if (name.contains("|") || artist.contains("|") || name.length() == 0 || artist.length() == 0){
            throw new IllegalArgumentException("Invalid Input");
        }

        this.name = name.strip();
        this.artist = artist.strip();
    }

    public Song(String name, String artist, String album, int year) {
        this(name, artist);

        if (album.contains("|") || year <= 0 || album.length() == 0 ) {
            throw new IllegalArgumentException("Invalid Input");
        }
        this.album = album.strip();
        this.year = year;
    }
}
