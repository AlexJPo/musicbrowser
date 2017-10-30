package music.repositories;

import music.dao.model.AuthorEntity;
import music.dao.model.MusicEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
@EnableTransactionManagement
public class MusicRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public void insertMusic(String name, AuthorEntity author) {
        Session session = entityManager.unwrap(Session.class);

        MusicEntity music = new MusicEntity();
        music.setName(name);
        music.setAuthor(author);

        session.save(music);
    }

    @Transactional
    public void updateMusic(String name, AuthorEntity author, int songId) {
        Session session = entityManager.unwrap(Session.class);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MusicEntity> query = builder.createQuery(MusicEntity.class);

        Root<MusicEntity> musicRoot = query.from( MusicEntity.class );
        query.select(musicRoot);
        query.where(builder.equal(musicRoot.get("idRecord"), songId));

        MusicEntity update = entityManager.createQuery(query).getSingleResult();
        update.setName(name);
        update.setAuthor(author);

        session.update(update);
    }

    @Transactional
    public void deleteMusic(int songId) {
        Session session = entityManager.unwrap(Session.class);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MusicEntity> query = builder.createQuery(MusicEntity.class);

        Root<MusicEntity> musicRoot = query.from( MusicEntity.class );
        query.select(musicRoot);
        query.where(builder.equal(musicRoot.get("idRecord"), songId));

        MusicEntity delete = entityManager.createQuery(query).getSingleResult();

        if (delete != null) {
            session.remove(delete);
        }
    }

    @Transactional
    public MusicEntity getMusicById(int id) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<MusicEntity> query = builder.createQuery(MusicEntity.class);

            Root<MusicEntity> musicRoot = query.from( MusicEntity.class );
            query.select(musicRoot);
            query.where(builder.equal(musicRoot.get("idRecord"), id));

            MusicEntity result = entityManager.createQuery(query).getSingleResult();
            return result;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public MusicEntity getMusicByName(String name) {
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<MusicEntity> query = builder.createQuery(MusicEntity.class);

            Root<MusicEntity> musicRoot = query.from( MusicEntity.class );
            query.select(musicRoot);
            query.where(builder.equal(musicRoot.get("songName"), name));

            MusicEntity result = entityManager.createQuery(query).getSingleResult();
            return result;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Transactional
    public List<MusicEntity> getAllMusicList() {

        //from MobileVendor mobileVendor join fetch mobileVendor.phoneModel PhoneModels
        //List<MusicEntity> result = entityManager.createQuery("SELECT u FROM MusicEntity u join fetch u.authorName a").getResultList();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MusicEntity> query = builder.createQuery(MusicEntity.class);

        Root<MusicEntity> musicRoot = query.from( MusicEntity.class );
        Join<MusicEntity, AuthorEntity> musicAythor = musicRoot.join( "authorName" );

        query.select( musicRoot );

        List<MusicEntity> result = entityManager.createQuery(query).getResultList();

        return  result;
    }

}
