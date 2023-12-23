package com.example;

public class User {
    private int id;
    private String nume;
    private String cnp;
    private String adresa;
    private String nrTelefon;
    private String email;
    private String username;
    private String parola;
    private int idRol;

    public User(int id, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        this.id = id;
        this.cnp = cnp;
        this.nume = nume;
        this.adresa = adresa;
        this.nrTelefon = nrTelefon;
        this.email = email;
        this.username = username;
        this.parola = parola;
        this.idRol = idRol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
