package music.dao.impl;

import music.dao.interfaces.*;
import music.dao.model.Author;
import music.dao.model.Mp3;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;


import java.sql.ResultSet;;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

@Component("sqlServerDao")
public class Mp3Sql implements Mp3Inerface, AuthorInterface {
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final class MP3RowMapper implements RowMapper<Mp3> {

        @Override
        public Mp3 mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mp3 mp3 = new Mp3();
            mp3.setId(rs.getInt("IdRecord"));
            mp3.setName(rs.getString("SongName"));
            mp3.setAuthor(rs.getString("AuthorName"));

            return mp3;
        }
    }
    private static final class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setIdRecorde(rs.getInt("IdRecord"));
            author.setAuthorName(rs.getString("AuthorName"));

            return author;
        }
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void insert(Mp3 mp3) {
        String sql = "INSERT INTO Music (Name, Author) VALUES (:name, :author)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", mp3.getName());
        namedParameters.addValue("author", mp3.getAuthor());

        jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void delete(Mp3 mp3) {

    }

    @Override
    public Mp3 getById(int id) {
        String sql = "SELECT Music.IdRecord, Music.SongName, Author.AuthorName FROM Music " +
                "INNER JOIN Author ON Music.AuthorId = Author.IdRecord " +
                "WHERE Music.IdRecord = :id";

        SqlParameterSource param = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, param, new MP3RowMapper());
    }

    @Override
    public List<Mp3> getMp3ListByName(String name) {
        String sql = "SELECT * FROM Music WHERE Name like :name";

        SqlParameterSource param = new MapSqlParameterSource("name", "%" + name + "%");
        return jdbcTemplate.query(sql, param, new MP3RowMapper());
    }

    @Override
    public List<Mp3> getAllMp3List() {
        String sql = "SELECT Music.IdRecord, Music.SongName, Author.AuthorName " +
                "FROM Music " +
                "INNER JOIN Author ON Music.AuthorId = Author.IdRecord";

        return jdbcTemplate.query(sql, new MP3RowMapper());
    }

    @Override
    public List<Mp3> getMp3ListByAuthor(String author) {
        String sql = "SELECT * FROM Music WHERE Author like :author";

        SqlParameterSource param = new MapSqlParameterSource("author", "%" + author + "%");
        return jdbcTemplate.query(sql, param, new MP3RowMapper());
    }

    @Override
    public void insertAuthor(String authorName) {
        String sql = "INSERT INTO Author (AuthorName) VALUES (:authorName)";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("authorName", authorName);

        jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void updateAuthor(int id, String authorName) {
        String sql = "UPDATE Author SET AuthorName = :authorName WHERE IdRecord = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        namedParameters.addValue("authorName", authorName);

        jdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public void deleteAuthor(int id) {
        String sql = "DELETE FROM Author WHERE IdRecord = :id";
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, param);
    }

    @Override
    public List<Author> getAllAuthor() {
        String sql = "SELECT * FROM Author";

        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    @Override
    public Author getAuthorByName(String name) {
        String sql = "SELECT * FROM Author WHERE LOWER(AuthorName) like :name";

        try {
            SqlParameterSource param = new MapSqlParameterSource("name", "%" + name.toLowerCase().trim() + "%");
            return jdbcTemplate.queryForObject(sql, param, new AuthorRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Author getAuthorById(int id) {
        String sql = "SELECT * FROM Author WHERE IdRecord = :id";

        try {
            SqlParameterSource param = new MapSqlParameterSource("id", id);
            return jdbcTemplate.queryForObject(sql, param, new AuthorRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
