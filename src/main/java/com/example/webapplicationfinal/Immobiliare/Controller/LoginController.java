package com.example.webapplicationfinal.Immobiliare.Controller;

import com.example.webapplicationfinal.Database.dao.jdbc.UtenteDAOJDBC;
import com.example.webapplicationfinal.Model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final UtenteDAOJDBC utenteDao;

    @Autowired
    public LoginController(UtenteDAOJDBC utenteDao) {
        this.utenteDao = utenteDao;
    }


    @PostMapping("doLogin")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);

        if (loginOk(username, password)) {
            session.setAttribute("usernameLogged", username);
            return "index";
        } else {
            return "loginError";
        }
    }

    @PostMapping("doRegister")
    @ResponseBody
    public String register(HttpSession session,
                           @RequestParam String nome,
                           @RequestParam String cognome,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String tipo) {
        System.out.println(nome);
        System.out.println(email);
        System.out.println(password);
        System.out.println(tipo);

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password);
        nuovoUtente.setTipo(tipo);

        utenteDao.save(nuovoUtente);

        session.setAttribute("usernameLogged", email);

        return "Registrazione avvenuta con successo";
    }

    @GetMapping("doLogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    private boolean loginOk(String username, String password) {
        // Implementa la logica di verifica del login (ad esempio, verifica i dati nel database)
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        }
        return false;
    }

}
