package music.dao.interfaces;

import music.dao.model.Mp3;
import java.util.List;

public interface Mp3Inerface {
    void insert(Mp3 mp3);

    void delete(Mp3 mp3);

    Mp3 getById(int id);

    List<Mp3> getMp3ListByName(String name);

    List<Mp3> getAllMp3List();

    List<Mp3> getMp3ListByAuthor(String author);
}
