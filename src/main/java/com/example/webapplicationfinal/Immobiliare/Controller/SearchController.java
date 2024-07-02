package com.example.webapplicationfinal.Immobiliare.Controller;

import com.example.webapplicationfinal.Database.dao.AnnunciDAO;
import com.example.webapplicationfinal.Model.Annuncio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final AnnunciDAO annunciDAO;

    @Autowired
    public SearchController(AnnunciDAO annunciDAO) {
        this.annunciDAO = annunciDAO;
    }

    @GetMapping
    public List<Annuncio> search(@RequestParam String position) {
        return annunciDAO.findByPosition(position);
    }
}
