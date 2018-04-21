package music.controller;

import music.dao.impl.Mp3Sql;
import music.dao.model.Author;
import music.dao.model.AuthorEntity;
import music.dao.model.Mp3;
import music.dao.model.MusicEntity;
import music.repositories.AuthorRepositories;
import music.repositories.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    public Mp3Sql sqlServerDao;

    @Autowired
    MusicRepository musicRepository;
    @Autowired
    AuthorRepositories authorRepository;

    @RequestMapping(value="", method = RequestMethod.GET)
    public ModelAndView home() {
        List<MusicEntity> allSongs = musicRepository.getAllMusicList();
        return new ModelAndView("views/music/index", "musics", allSongs);
    }

    @RequestMapping(value="/add")
    public ModelAndView add(ModelAndView model) {
        List<AuthorEntity> authors = authorRepository.getAllAuthor();

        model.addObject("musics", null);
        model.addObject("authors", authors);
        model.setViewName("views/music/add");

        return model;
    }

    @PostMapping(value="/add")
    public ModelAndView add(@RequestParam("song_name") String songName, @RequestParam("author_song") int authorId) {
        MusicEntity existSong = musicRepository.getMusicByName(songName);

        if (existSong == null) {
            AuthorEntity author = authorRepository.getAuthorById(authorId);

            musicRepository.insertMusic(songName, author);
            return new ModelAndView("redirect:/music");
        } else {
            ModelAndView model = new ModelAndView();
            List<AuthorEntity> authors = authorRepository.getAllAuthor();

            model.setViewName("views/music/add");
            model.addObject("musics", songName);
            model.addObject("authors", authors);
            model.addObject("error", "Song name is already exist!");

            return model;
        }
    }

    @GetMapping(value="/edit")
    public ModelAndView edit(@RequestParam("id") int id) {
        return editMusic(id);
    }

    @PostMapping(value="/edit")
    public ModelAndView edit(@RequestParam("song_name") String songName,
                             @RequestParam("author_song") int authorId,
                             @RequestParam("song_id") int songId) {

        MusicEntity existSong = musicRepository.getMusicByName(songName);

        if (existSong == null) {
            AuthorEntity author = authorRepository.getAuthorById(authorId);

            musicRepository.updateMusic(songName, author, songId);
            return new ModelAndView("redirect:/music");
        } else {
            return errorMusic(songId, songName);
        }
    }

    @GetMapping(value="/remove")
    public ModelAndView remove(@RequestParam("id") int id) {
        musicRepository.deleteMusic(id);
        return new ModelAndView("redirect:/music");
    }

    private ModelAndView editMusic(int id) {
        Mp3 song = sqlServerDao.getMp3ById(id);
        List<Author> authors = sqlServerDao.getAllAuthor();

        ModelAndView model = new ModelAndView();
        model.addObject("song", song);
        model.addObject("authors", authors);
        model.setViewName("views/music/edit");

        return model;
    }
    private ModelAndView errorMusic(int songId, String songName) {
        Mp3 song = sqlServerDao.getMp3ById(songId);
        List<Author> authors = sqlServerDao.getAllAuthor();
        ModelAndView model = new ModelAndView();

        model.addObject("song", song);
        model.addObject("authors", authors);
        model.addObject("error", "Song name '" + songName + "' is already exist!");
        model.setViewName("views/music/edit");

        return model;
    }
}
