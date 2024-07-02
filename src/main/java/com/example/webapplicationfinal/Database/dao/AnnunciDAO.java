package com.example.webapplicationfinal.Database.dao;

import com.example.webapplicationfinal.Model.Annuncio;

import java.util.List;

public interface AnnunciDAO {
    void save(Annuncio annuncio);  // Create
    Annuncio findByPrimaryKey(Long id);  // Retrieve
    List<Annuncio> findAll();  // Retrieve all
    void update(Annuncio annuncio);  // Update
    void delete(Annuncio annuncio);  // Delete

    // Metodi specifici
    List<Annuncio> findByUtenteId(Long utenteId);

    List<Annuncio> findByPosition(String position);
}
