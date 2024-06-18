package com.example.webapplicationfinal.Model;

import java.util.ArrayList;

public class Annuncio {

    private Long id;
    private String titolo;
    private String tipoDiImmobile;
    private String descrizione;
    private Integer prezzo;
    private Long utenteId;
    private ArrayList<String> images;
    private String position;

    // Costruttore senza argomenti
    public Annuncio() {}

    // Costruttore con argomenti
    public Annuncio(String titolo, String tipoDiImmobile, Integer prezzo, String descrizione, ArrayList<String> images, String position, Long utenteId) {
        this.titolo = titolo;
        this.tipoDiImmobile = tipoDiImmobile;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.images = images;
        this.position = position;
        this.utenteId = utenteId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getTipoDiImmobile() { return tipoDiImmobile; }
    public void setTipoDiImmobile(String tipoDiImmobile) { this.tipoDiImmobile = tipoDiImmobile; }

    public Integer getPrezzo() { return prezzo; }
    public void setPrezzo(Integer prezzo) { this.prezzo = prezzo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Long getUtenteId() { return utenteId; }
    public void setUtenteId(Long utenteId) { this.utenteId = utenteId; }

    public ArrayList<String> getImages() { return images; }
    public void setImages(ArrayList<String> images) { this.images = images; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
