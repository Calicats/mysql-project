package com.example;

import java.util.Objects;

public class GrupStudiu {
    private int idGrupStudiu;
    private String descriereGrupStudiu;
    private int id_participant_activitate;

    public GrupStudiu(int idGrupStudiu, String descriereGrupStudiu, int id_participant_activitate) {
        this.idGrupStudiu = idGrupStudiu;
        this.descriereGrupStudiu = descriereGrupStudiu;
        this.id_participant_activitate = id_participant_activitate;
    }

    public int getIdGrupStudiu() {
        return idGrupStudiu;
    }

    public void setIdGrupStudiu(int idGrupStudiu) {
        this.idGrupStudiu = idGrupStudiu;
    }

    public String getDescriereGrupStudiu() {
        return descriereGrupStudiu;
    }

    public void setDescriereGrupStudiu(String descriereGrupStudiu) {
        this.descriereGrupStudiu = descriereGrupStudiu;
    }

    public int getId_participant_activitate() {
        return id_participant_activitate;
    }

    public void setId_participant_activitate(int id_participant_activitate) {
        this.id_participant_activitate = id_participant_activitate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupStudiu that)) return false;
        return idGrupStudiu == that.idGrupStudiu && id_participant_activitate == that.id_participant_activitate && descriereGrupStudiu.equals(that.descriereGrupStudiu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGrupStudiu, descriereGrupStudiu, id_participant_activitate);
    }

    @Override
    public String toString() {
        return "GrupStudiu{" +
                "idGrupStudiu=" + idGrupStudiu +
                ", descriereGrupStudiu='" + descriereGrupStudiu + '\'' +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }
}

