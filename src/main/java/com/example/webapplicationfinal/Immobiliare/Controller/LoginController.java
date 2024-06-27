package com.example.webapplicationfinal.Immobiliare.Controller;

import com.example.webapplicationfinal.Database.dao.jdbc.UtenteDAOJDBC;
import com.example.webapplicationfinal.Model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final UtenteDAOJDBC utenteDao;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @Autowired
    public LoginController(UtenteDAOJDBC utenteDao) {
        this.utenteDao = utenteDao;
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public boolean login(HttpSession session, @RequestParam String email, @RequestParam String password) {
        LOGGER.info("Login attempt with email: " + email);

        Utente utente = utenteDao.findByEmail(email);

        if (utente != null) {
            // Verifica la password hashata
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, utente.getPassword())) {
                // Login avvenuto con successo, crea la sessione utente
                session.setAttribute("usernameLogged", email);
                session.setAttribute("userRole", utente.getTipo());
                LOGGER.info("Login successful for username: " + email);
                return true;
            }
        }

        LOGGER.info("Login failed for username: " + email);
        return false;
    }

    @PostMapping("doRegister")
    @ResponseBody
    public String register(HttpSession session,
                           @RequestParam String nome,
                           @RequestParam String cognome,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String tipo) {
        LOGGER.info("Registration attempt with email: " + email);


        // Hash della password con BCrypt
        String hashedPassword = new BCryptPasswordEncoder().encode(password);

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(hashedPassword); // Salva la password hashed
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
}
