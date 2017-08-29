package music.dao.model;

import javax.persistence.*;

@Entity
@Table(name="Author")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRecord")
    private int idRecord;
    @Column(name = "AuthorName")
    private String authorName;

    public AuthorEntity() {}

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int id) {
        this.idRecord = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
