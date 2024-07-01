package com.example.webapplicationfinal.Immobiliare.Controller;

import com.example.webapplicationfinal.Database.dao.jdbc.AnnunciDAOJDBC;
import com.example.webapplicationfinal.Model.Annuncio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Controller
@RequestMapping("/announcements")
public class AnnuncioController {

    private final AnnunciDAOJDBC annunciDao;
    private static final Logger LOGGER = Logger.getLogger(AnnuncioController.class.getName());

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public AnnuncioController(AnnunciDAOJDBC annunciDao) {
        this.annunciDao = annunciDao;
    }

    @PostMapping("/addAnnouncements")
    @ResponseBody
    public ResponseEntity<String> aggiungiAnnuncio(HttpSession session,
                                                   @RequestParam String titolo,
                                                   @RequestParam String tipoDiImmobile,
                                                   @RequestParam String descrizione,
                                                   @RequestParam int prezzo,
                                                   @RequestParam String position,
                                                   @RequestParam("images") MultipartFile[] files) {
        LOGGER.info("Aggiunta di un nuovo annuncio: " + titolo);

        Long utenteId = (Long) session.getAttribute("userId");
        if (utenteId == null) {
            LOGGER.warning("Utente non autenticato durante l'aggiunta dell'annuncio");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autenticato");
        }

        Annuncio nuovoAnnuncio = new Annuncio();
        nuovoAnnuncio.setTitolo(titolo);
        nuovoAnnuncio.setTipoDiImmobile(tipoDiImmobile);
        nuovoAnnuncio.setDescrizione(descrizione);
        nuovoAnnuncio.setPrezzo(prezzo);
        nuovoAnnuncio.setUtenteId(utenteId);
        nuovoAnnuncio.setImages(new ArrayList<>());
        nuovoAnnuncio.setPosition(position);

        try {
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Path uploadPath = Paths.get("src/main/resources/static/css/images"); // Percorso aggiornato

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                nuovoAnnuncio.getImages().add(fileName); // Aggiungi solo il nome del file
            }

            annunciDao.save(nuovoAnnuncio);

            return ResponseEntity.ok("Annuncio aggiunto con successo");
        } catch (IOException ex) {
            LOGGER.severe("Impossibile salvare le immagini per l'annuncio " + titolo + ". Errore: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante il salvataggio delle immagini");
        }
    }


    @PutMapping("/{id}/edit")
    @ResponseBody
    public ResponseEntity<String> modificaAnnuncio(@PathVariable Long id,
                                                   @RequestBody Annuncio updatedAnnuncio) {
        LOGGER.info("Modifica dell'annuncio con ID: " + id + ", Titolo: " + updatedAnnuncio.getTitolo() + ", Descrizione: " + updatedAnnuncio.getDescrizione() + ", Prezzo: " + updatedAnnuncio.getPrezzo());

        Annuncio annuncio = annunciDao.findByPrimaryKey(id);
        if (annuncio == null) {
            LOGGER.warning("Annuncio non trovato per l'ID: " + id);
            return ResponseEntity.notFound().build();
        }

        // Aggiorna solo i campi necessari
        annuncio.setTitolo(updatedAnnuncio.getTitolo());
        annuncio.setDescrizione(updatedAnnuncio.getDescrizione());
        annuncio.setPrezzo(updatedAnnuncio.getPrezzo());

        annunciDao.update(annuncio);

        return ResponseEntity.ok("Annuncio modificato con successo");
    }


    @GetMapping("/myAnnouncements")
    @ResponseBody
    public List<Annuncio> mieiAnnunci(HttpSession session) {
        Long utenteId = (Long) session.getAttribute("userId");
        if (utenteId == null) {
            LOGGER.warning("Utente non autenticato durante il recupero dei propri annunci");
            return new ArrayList<>();
        }
        return annunciDao.findByUtenteId(utenteId);
    }

    @GetMapping("/lista")
    @ResponseBody
    public List<Annuncio> listaAnnunci() {
        return annunciDao.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Annuncio dettaglioAnnuncio(@PathVariable Long id) {
        return annunciDao.findByPrimaryKey(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> eliminaAnnuncio(@PathVariable Long id) {
        LOGGER.info("Eliminazione dell'annuncio con ID: " + id);

        Annuncio annuncio = annunciDao.findByPrimaryKey(id);
        if (annuncio == null) {
            LOGGER.warning("Annuncio non trovato per l'ID: " + id);
            return ResponseEntity.notFound().build();
        }

        try {
            // Elimina le immagini associate all'annuncio dal filesystem
            for (String imageName : annuncio.getImages()) {
                Path imagePath = Paths.get(uploadDir).resolve(imageName);
                Files.deleteIfExists(imagePath);
            }

            annunciDao.delete(annuncio);

            return ResponseEntity.ok("Annuncio eliminato con successo");
        } catch (IOException ex) {
            LOGGER.severe("Impossibile eliminare le immagini associate all'annuncio " + id + ". Errore: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante l'eliminazione delle immagini associate");
        }
    }

}