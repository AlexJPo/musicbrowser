package music.dao.model;

import javax.persistence.*;

@Entity
@Table(name="Music")
public class MusicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRecord")
    private int idRecord;

    @Column(name = "SongName")
    private String songName;

    @ManyToOne
    @JoinColumn(name = "AuthorId")
    private AuthorEntity authorName;

    public MusicEntity() {}

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

    public AuthorEntity getAuthor() {
        return authorName;
    }

    public void setAuthor(AuthorEntity author) {
        this.authorName = author;
    }

}
