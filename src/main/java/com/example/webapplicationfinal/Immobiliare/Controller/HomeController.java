package com.example.webapplicationfinal.Immobiliare.Controller;

import jakarta.servlet.http.HttpSession;
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
    public String addAnnouncement() { return "add_announcement"; }

    @GetMapping("/my_announcements")
    public String myAnnouncements() { return "my_announcements"; }

    @GetMapping("/profile")
    public String profile() { return "profile"; }

    @GetMapping("/auth/doLogout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logout successful";
    }

}
