//Gabriel Parente
//Mohamed Smires

package app.songlib;

public class Song implements Comparable<Song> {

    public String name;
    public String artist;
    public String album;
    public int year=0;

    public Song(String name, String artist) throws IllegalArgumentException {

        name = name.strip();
        artist = artist.strip();

        if (name.contains("|") || artist.contains("|") || name.length() == 0 || artist.length() == 0){
            throw new IllegalArgumentException("Invalid Input");
        }

        this.name = name;
        this.artist = artist;

    }

    public Song(String name, String artist, String album){
        this(name, artist);

        String tempAlbum = album.strip();

        //checks the difference between no album name and an white space album name
        if (album.contains("|") || (tempAlbum.length() == 0 && album.length() != 0)) {
            throw new IllegalArgumentException("Invalid Input");
        }

        this.album = tempAlbum;

    }

    public Song(String name, String artist, String album, int year) {
        this(name, artist, album);

        if (year <= 0){
            throw new IllegalArgumentException("Invalid Input");
        }
        this.year = year;
    }

    @Override
    public int compareTo(Song s) {
        int c = this.name.toLowerCase().compareTo(s.name.toLowerCase());
        if (c!= 0)
            return c;
        return this.artist.toLowerCase().compareTo(s.artist.toLowerCase());
    }

    @Override
    public String toString() {
        return name + ", By: " + artist;
    }

    public String toSaveString() {
        return name + "|" + artist + "|" + album + "|" + year + "\n";
    }



}
