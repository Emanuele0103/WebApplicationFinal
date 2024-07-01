package com.example.webapplicationfinal.Model;

public class Utente {
    private long ID_Utente;
    private String Nome;
    private String Cognome;
    private String Email;
    private String password;
    private String tipo;

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }


    public long getID_Utente() {
        return ID_Utente;
    }

    public void setID_Utente(Integer ID_Utente) {
        this.ID_Utente = ID_Utente;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
