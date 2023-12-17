package com.example;

public class Profesor extends User {
    private String departament;
    private int nrMinOre;
    private int nrMaxOre;

    public Profesor(int idProfesor, String cnp, String nume, String departament, int nrMinOre, int nrMaxOre, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        super(idProfesor, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
        this.departament = departament;
        this.nrMinOre = nrMinOre;
        this.nrMaxOre = nrMaxOre;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public int getNrMinOre() {
        return nrMinOre;
    }

    public void setNrMinOre(int nrMinOre) {
        this.nrMinOre = nrMinOre;
    }

    public int getNrMaxOre() {
        return nrMaxOre;
    }

    public void setNrMaxOre(int nrMaxOre) {
        this.nrMaxOre = nrMaxOre;
    }
}
