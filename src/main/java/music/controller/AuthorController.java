package music.controller;

import music.dao.impl.Mp3Sql;
import music.dao.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value="/author")
public class AuthorController {

    @Autowired
    public Mp3Sql sqlServerDao;

    @GetMapping(value="")
    public ModelAndView home() {
        List<Author> allAuthors = sqlServerDao.getAllAuthor();
        return new ModelAndView("views/author/index", "allAuthors", allAuthors);
    }

    @RequestMapping(value="/add")
    public ModelAndView add() {
        return new ModelAndView("views/author/add", "authors", null);
    }

    @RequestMapping(value="/edit")
    public ModelAndView edit(@RequestParam("id") int id) {
        Author author = sqlServerDao.getAuthorById(id);
        return new ModelAndView("views/author/edit", "author", author);
    }

    @PostMapping(value="/create")
    public ModelAndView add(@RequestParam("author_name") String name) {
        Author existAuthor = sqlServerDao.getAuthorByName(name);

        if (existAuthor == null) {
            sqlServerDao.insertAuthor(name);
            return new ModelAndView("redirect:/author");
        } else {
            return errorAuthor(name);
        }
    }

    @PostMapping(value="/update")
    public ModelAndView update(@RequestParam("author_name") String name, @RequestParam("id") int id) {
        Author existAuthor = sqlServerDao.getAuthorByName(name);

        if (existAuthor == null) {
            sqlServerDao.updateAuthor(id, name);
            return new ModelAndView("redirect:/author");
        } else {
            return errorAuthor(name);
        }
    }

    @GetMapping(value="/remove")
    public ModelAndView remove(@RequestParam("id") int id) {
        sqlServerDao.deleteAuthor(id);
        return new ModelAndView("redirect:/author");
    }

    private ModelAndView errorAuthor(String name) {
        ModelAndView model = new ModelAndView();
        model.setViewName("views/author/add");
        model.addObject("authors", name);
        model.addObject("error", "Author is already exist!");

        return model;
    }
}
