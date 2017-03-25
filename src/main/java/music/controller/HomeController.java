package music.controller;

import music.dao.impl.Mp3Sql;
import music.dao.model.Mp3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    public Mp3Sql sqlServerDao;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String home(Model  model) {

        List<Mp3> allSongs = sqlServerDao.getAllMp3List();

        model.addAttribute("musics", allSongs);
        //model.addObject("musics", allSongs);
        //model.setViewName("index");

        //return  model;
        return "index";
    }
}
