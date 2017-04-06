package music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }
}
