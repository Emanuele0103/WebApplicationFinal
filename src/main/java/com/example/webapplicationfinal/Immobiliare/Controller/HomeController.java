package com.example.webapplicationfinal.Immobiliare.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/immobili")
    public String properties() {
        return "immobili";
    }

    @GetMapping("/contatti")
    public String contact() {
        return "contatti";
    }

    @GetMapping("/accesso")
    public String login() {
        return "accesso";
    }

    @GetMapping("/add_announcement")
    public String add_announcement() {
        return "add_announcement";
    }

    @GetMapping("/my-announcement")
    public String my_announcement() {
        return "my-announcement";
    }

}
