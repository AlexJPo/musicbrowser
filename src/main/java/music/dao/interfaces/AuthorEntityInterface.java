package music.dao.interfaces;

import java.util.List;

public interface AuthorEntityInterface {
    void insertAuthor(String authorName);

    void updateAuthor(int id, String authorName);

    void deleteAuthor(int id);

    <T> List<T> getAllAuthor();

    <T> T getAuthorByName(String name);

    <T> T getAuthorById(int id);
}
