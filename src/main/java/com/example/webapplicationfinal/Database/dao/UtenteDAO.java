package com.example.webapplicationfinal.Database.dao;

import com.example.webapplicationfinal.Model.Utente;

import java.util.List;

public interface UtenteDAO {
    void save(Utente utente);  // Create
    Utente findByPrimaryKey(Long id);  // Retrieve
    List<Utente> findAll();  // Retrieve all
    void update(Utente utente);  // Update
    void delete(Utente utente);  // Delete
    Utente findByEmail(String username);

    // Metodi specifici
    List<Utente> findByNome(String nome);
}
