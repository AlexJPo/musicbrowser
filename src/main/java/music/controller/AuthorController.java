package music.controller;

import music.dao.impl.Mp3Sql;
import music.dao.model.Author;
import music.dao.model.AuthorEntity;
import music.repositories.AuthorRepositories;
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
    @Autowired
    AuthorRepositories authorRepository;

    @GetMapping(value="")
    public ModelAndView home() {
        List<AuthorEntity> allAuthors = authorRepository.getAllAuthor();
        return new ModelAndView("views/author/index", "allAuthors", allAuthors);

        /*List<Author> allAuthors = sqlServerDao.getAllAuthor();
        return new ModelAndView("views/author/index", "allAuthors", allAuthors);*/
    }

    @RequestMapping(value="/add")
    public ModelAndView add() {
        return new ModelAndView("views/author/add", "authors", null);
    }

    @PostMapping(value="/add")
    public ModelAndView add(@RequestParam("author_name") String name) {
        AuthorEntity existAuthor = authorRepository.getAuthorByName(name);

        if (existAuthor.getAuthorName() == null) {
            authorRepository.insertAuthor(name);
            return new ModelAndView("redirect:/author");
        } else {
            return errorAuthor(name);
        }

        /*Author existAuthor = sqlServerDao.getAuthorByName(name);

        if (existAuthor == null) {
            sqlServerDao.insertAuthor(name);
            return new ModelAndView("redirect:/author");
        } else {
            return errorAuthor(name);
        }*/
    }

    @RequestMapping(value="/edit")
    public ModelAndView edit(@RequestParam("id") int id) {
        AuthorEntity author = authorRepository.getAuthorById(id);

        ModelAndView model = new ModelAndView();
        model.setViewName("views/author/edit");
        model.addObject("author", author);
        model.addObject("error", "");

        //return new ModelAndView("views/author/edit", "author", author);
        return  model;

        /*
        Author author = sqlServerDao.getAuthorById(id);
        return new ModelAndView("views/author/edit", "author", author);
        */
    }

    @PostMapping(value="/update")
    public ModelAndView update(@RequestParam("author_name") String name, @RequestParam("id") int id) {
        AuthorEntity existAuthor = authorRepository.getAuthorByName(name);

        if (existAuthor.getAuthorName() == null) {
            authorRepository.updateAuthor(id, name);

            return new ModelAndView("redirect:/author");
        } else {
            return errorAuthor(name);
        }

        /*Author existAuthor = sqlServerDao.getAuthorByName(name);

        if (existAuthor == null) {
            sqlServerDao.updateAuthor(id, name);
            return new ModelAndView("redirect:/author");
        } else {
            return errorAuthor(name);
        }*/
    }

    @GetMapping(value="/remove")
    public ModelAndView remove(@RequestParam("id") int id) {
        authorRepository.deleteAuthor(id);
        return new ModelAndView("redirect:/author");

        /*sqlServerDao.deleteAuthor(id);
        return new ModelAndView("redirect:/author");*/
    }

    private ModelAndView errorAuthor(String name) {
        ModelAndView model = new ModelAndView();
        model.setViewName("views/author/add");
        model.addObject("authors", name);
        model.addObject("error", "Author is already exist!");

        return model;
    }
}
