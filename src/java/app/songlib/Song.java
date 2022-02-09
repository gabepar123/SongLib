//Gabriel Parente
//Mohamed Smires

package app.songlib;

//TODO check how this reacts to empty strings and how JavaFX takes integers for the year
public class Song implements Comparable<Song>{

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

    //FIXME: changed for now to support loading
    public Song(String name, String artist, String album, int year) {
        this(name, artist);

        if (album.contains("|") || year <= 0 || album.length() == 0 ) {
//            throw new IllegalArgumentException("Invalid Input");
            System.out.println("Invalid Input");
            this.album = "";
            this.year = 0;
        }

        this.album = album.strip();
        this.year = year;
    }

    @Override
    public int compareTo(Song s) {
        if (this.name.compareTo(s.name) != 0)
            return this.name.compareTo(s.name);
        return this.artist.compareTo(s.artist);
    }

    @Override
    public String toString() {
        return name + ", By: " + artist;
    }

    public String toSaveString() {
        return name + "|" + artist + "|" + album + "|" + year + "\n";
    }



}
