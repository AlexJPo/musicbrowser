package music.dao.model;

public class Author {
    private int idRecord;
    private String authorName;

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
