package music.repositories;

import music.dao.model.AuthorEntity;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
@EnableTransactionManagement
public class AuthorRepositories {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertAuthor(String authorName) {
        /*AuthorEntity author = new AuthorEntity();
        author.setAuthorName(authorName);

        entityManager.persist(author);*/

        Session session = entityManager.unwrap(Session.class);
        Criteria authorCriteria = session.createCriteria(AuthorEntity.class);

        AuthorEntity author = new AuthorEntity();
        author.setAuthorName(authorName);

        session.save(author);
    }

    @Transactional
    public void updateAuthor(int id, String name) {
        /*AuthorEntity update = entityManager.find(AuthorEntity.class, id);

        if (update != null) {
            update.setAuthorName(name);
            entityManager.merge(update);
        }*/

        Session session = entityManager.unwrap(Session.class);
        AuthorEntity update = (AuthorEntity) session.createCriteria(AuthorEntity.class).
                add(Restrictions.eq("idRecord", id))
                .uniqueResult();

        if (update != null) {
            update.setAuthorName(name);
            session.update(update);
        }
    }

    @Transactional
    public void deleteAuthor(int id) {
        /*AuthorEntity delete = entityManager.find(AuthorEntity.class, id);

        if (delete != null) {
            entityManager.remove(delete);
        }*/

        Session session = entityManager.unwrap(Session.class);
        AuthorEntity update = (AuthorEntity) session.createCriteria(AuthorEntity.class).
                add(Restrictions.eq("idRecord", id))
                .uniqueResult();

        if (update != null) {
            session.remove(update);
        }
    }

    @Transactional
    public List<AuthorEntity> getAllAuthor() {
        Session session = entityManager.unwrap(Session.class);
        Criteria authorCriteria = session.createCriteria(AuthorEntity.class);

        List<AuthorEntity> result = authorCriteria.list();

        return result;
    }

    @Transactional
    public AuthorEntity getAuthorByName(String name) {
        /*List<AuthorEntity> result = entityManager.createQuery("SELECT u FROM AuthorEntity u WHERE u.authorName LIKE :authorName")
                .setParameter("authorName", name)
                .getResultList();

        return result.size() > 0 ? result.get(0) : new AuthorEntity();*/

        Session session = entityManager.unwrap(Session.class);
        Criteria authorCriteria = session.createCriteria(AuthorEntity.class);

        List<AuthorEntity> result = authorCriteria.add(Restrictions.like("authorName", name)).list();

        return result.size() > 0 ? result.get(0) : new AuthorEntity();
    }

    @Transactional
    public AuthorEntity getAuthorById(int id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuthorEntity> query = builder.createQuery(AuthorEntity.class);

        Root<AuthorEntity> authorRoot = query.from(AuthorEntity.class);

        query.select(authorRoot);
        query.where(builder.equal(authorRoot.get("idRecord"), id));

        AuthorEntity result = entityManager.createQuery(query).getSingleResult();

        return result;

        /*Session session = entityManager.unwrap(Session.class);
        return (AuthorEntity) session.createCriteria(AuthorEntity.class).
                add(Restrictions.like("idRecord", id))
                .uniqueResult();*/

        /*return entityManager.createQuery("SELECT u FROM AuthorEntity u WHERE u.idRecord = :authorId", AuthorEntity.class)
                .setParameter("authorId", id).getSingleResult();*/
    }
}
