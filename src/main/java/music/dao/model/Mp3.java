package music.dao.model;

import org.springframework.stereotype.Service;

@Service
public class Mp3 {

    private int idRecord;
    private String songName;
    private String authorName;

    public Mp3() {}

    public int getId() {
        return idRecord;
    }
    public void setId(int id) {
        this.idRecord = id;
    }

    public String getName() {
        return songName;
    }

    public void setName(String name) {
        this.songName = name;
    }

    public String getAuthor() {
        return authorName;
    }

    public void setAuthor(String author) {
        this.authorName = author;
    }

    /*@Override
    public*String toString() {
        return "[MP3 = Author: " + authorName + ", Song name: " + songName + " ]";
    }*/
}
