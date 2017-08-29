package music.repositories;

import music.dao.interfaces.AuthorEntityInterface;
import music.dao.model.AuthorEntity;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
@EnableTransactionManagement
public class AuthorRepositories {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertAuthor(String authorName) {
        AuthorEntity author = new AuthorEntity();
        author.setAuthorName(authorName);

        entityManager.persist(author);
    }

    @Transactional
    public void updateAuthor(int id, String name) {
        AuthorEntity update = entityManager.find(AuthorEntity.class, id);

        if (update != null) {
            update.setAuthorName(name);
            entityManager.merge(update);
        }
    }

    @Transactional
    public void deleteAuthor(int id) {
        AuthorEntity delete = entityManager.find(AuthorEntity.class, id);

        if (delete != null) {
            entityManager.remove(delete);
        }
    }

    @Transactional
    public List<AuthorEntity> getAllAuthor() {
        List<AuthorEntity> result = entityManager.createQuery("SELECT u FROM AuthorEntity u").getResultList();
        return result;
    }

    @Transactional
    public AuthorEntity getAuthorByName(String name) {
        List<AuthorEntity> result = entityManager.createQuery("SELECT u FROM AuthorEntity u WHERE u.authorName LIKE :authorName")
                .setParameter("authorName", name)
                .getResultList();

        return result.size() > 0 ? result.get(0) : new AuthorEntity();
    }

    @Transactional
    public AuthorEntity getAuthorById(int id) {
        return entityManager.createQuery("SELECT u FROM AuthorEntity u WHERE u.idRecord = :authorId", AuthorEntity.class)
                .setParameter("authorId", id).getSingleResult();
    }
}
