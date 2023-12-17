package com.example.sql.roles;

public class User {
    private int id;
    private String nume;
    private String cnp;
    private String adresa;
    private String email;
    private String nrTelefon;
    private String parola;

    public User(int id, String nume, String cnp, String adresa, String email, String nrTelefon, String parola) {
        this.id = id;
        this.nume = nume;
        this.cnp = cnp;
        this.adresa = adresa;
        this.email = email;
        this.nrTelefon = nrTelefon;
        this.parola = parola;
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

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
