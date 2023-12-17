package com.example;

public class Student extends User {
    private int anStudiu;
    private int numarOre;

    public Student(int idStudent, String nume, String cnp, int anStudiu, int numarOre, String adresa, String email, String nrTelefon, String username, String parola, int idRol) {
        super(idStudent, nume, cnp, adresa, email, nrTelefon, username, parola, idRol);
        this.anStudiu = anStudiu;
        this.numarOre = numarOre;
    }

    public int getAnStudiu() {
        return anStudiu;
    }

    public void setAnStudiu(int anStudiu) {
        this.anStudiu = anStudiu;
    }

    public int getNumarOre() {
        return numarOre;
    }

    public void setNumarOre(int numarOre) {
        this.numarOre = numarOre;
    }
}
