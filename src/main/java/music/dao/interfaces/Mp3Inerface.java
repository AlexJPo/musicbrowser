package music.dao.interfaces;

import music.dao.model.Mp3;
import java.util.List;

public interface Mp3Inerface {
    void insertMp3(String name, int authorId);

    void updateMp3(String name, int authorId, int songId);

    void deleteMp3(int songId);

    Mp3 getMp3ById(int id);

    Mp3 getMp3ByName(String name);

    List<Mp3> getMp3ListByName(String name);

    List<Mp3> getAllMp3List();

    List<Mp3> getMp3ListByAuthor(String author);
}
