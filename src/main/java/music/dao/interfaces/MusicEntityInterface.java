package music.dao.interfaces;

import music.dao.model.Mp3;

import java.util.List;

public interface MusicEntityInterface {
    void insertMusic(String name, int authorId);

    void updateMusic(String name, int authorId, int songId);

    void deleteMusic(int songId);

    <T> T getMusicById(int id);

    <T> T getMusicByName(String name);

    <T> List<T> getMusicListByName(String name);

    <T> List<T> getAllMusicList();

    <T> List<T> getMusicListByAuthor(String author);
}
