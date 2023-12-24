package com.example;

public class ActivitateProfesor {
    private String nume;
    private String username;
    private String tipActivitate;
    private String descriere;

    public ActivitateProfesor(String nume, String username, String tipActivitate, String descriere) {
        this.nume = nume;
        this.username = username;
        this.tipActivitate = tipActivitate;
        this.descriere = descriere;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTipActivitate() {
        return tipActivitate;
    }

    public void setTipActivitate(String tipActivitate) {
        this.tipActivitate = tipActivitate;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
