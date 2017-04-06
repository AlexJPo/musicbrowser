package music.dao.interfaces;

import music.dao.model.Author;

import java.util.List;

public interface AuthorInterface {
    void insertAuthor(String authorName);

    void updateAuthor(int id, String authorName);

    void deleteAuthor(int id);

    List<Author> getAllAuthor();

    Author getAuthorByName(String name);

    Author getAuthorById(int id);
}
