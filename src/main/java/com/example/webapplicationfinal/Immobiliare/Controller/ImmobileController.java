package com.example.webapplicationfinal.Immobiliare.Controller;

import com.example.webapplicationfinal.Database.dao.jdbc.AnnunciDAOJDBC;
import com.example.webapplicationfinal.Model.Annuncio;
import com.example.webapplicationfinal.Model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/immobili")
public class ImmobileController {

    private final AnnunciDAOJDBC annunciDao;

    @Autowired
    public ImmobileController(AnnunciDAOJDBC annunciDao) {
        this.annunciDao = annunciDao;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Annuncio>>> getAllImmobili() {
        List<Annuncio> immobili = annunciDao.findAll();

        Map<String, List<Annuncio>> immobiliPerCategoria = immobili.stream()
                .collect(Collectors.groupingBy(Annuncio::getTipoDiImmobile));

        return ResponseEntity.ok(immobiliPerCategoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getImmobileById(@PathVariable Long id) {
        Annuncio annuncio = annunciDao.findByPrimaryKey(id);
        if (annuncio != null) {
            Utente utente = annunciDao.findUtenteByAnnuncioId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("annuncio", annuncio);
            response.put("utente", utente);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}