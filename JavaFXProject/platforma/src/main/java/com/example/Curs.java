package com.example;

public class Curs {
    private int idCurs;
    private String numeCurs;
    private String descriere;
    private int nrMaximStudenti;

    public Curs(int idCurs, String numeCurs, String descriere, int nrMaximStudenti) {
        this.idCurs = idCurs;
        this.numeCurs = numeCurs;
        this.descriere = descriere;
        this.nrMaximStudenti = nrMaximStudenti;
    }

    public int getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(int idCurs) {
        this.idCurs = idCurs;
    }

    public String getNumeCurs() {
        return numeCurs;
    }

    public void setNumeCurs(String numeCurs) {
        this.numeCurs = numeCurs;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getNrMaximStudenti() {
        return nrMaximStudenti;
    }

    public void setNrMaximStudenti(int nrMaximStudenti) {
        this.nrMaximStudenti = nrMaximStudenti;
    }
}
