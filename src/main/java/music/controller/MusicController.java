package music.controller;

import music.dao.impl.Mp3Sql;
import music.dao.model.Author;
import music.dao.model.Mp3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    public Mp3Sql sqlServerDao;

    @RequestMapping(value="", method = RequestMethod.GET)
    public ModelAndView home() {
        List<Mp3> allSongs = sqlServerDao.getAllMp3List();
        return new ModelAndView("views/music/index", "musics", allSongs);
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") int id, ModelAndView  model) {
        Mp3 song = sqlServerDao.getById(id);
        List<Author> authors = sqlServerDao.getAllAuthor();

        model.setViewName("views/edit");
        model.addObject("song", song);
        model.addObject("authors", authors);
        //return new ModelAndView("views/edit");
        return model;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("author_song") String author_song,
                         @ModelAttribute Mp3 song,
                         BindingResult bindingResult) {

        return null;
    }
}
