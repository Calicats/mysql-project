package com.example;

import java.util.Objects;

public class ParticipantActivitate {
    private int idParticipantActivitate;
    private int nrMaximStudenti;
    private int id_activitate_profesor;
    private int id_student;

    public ParticipantActivitate(int idParticipantActivitate, int nrMaximStudenti, int id_activitate_profesor, int id_student) {
        this.idParticipantActivitate = idParticipantActivitate;
        this.nrMaximStudenti = nrMaximStudenti;
        this.id_activitate_profesor = id_activitate_profesor;
        this.id_student = id_student;
    }

    public int getIdParticipantActivitate() {
        return idParticipantActivitate;
    }

    public void setIdParticipantActivitate(int idParticipantActivitate) {
        this.idParticipantActivitate = idParticipantActivitate;
    }

    public int getNrMaximStudenti() {
        return nrMaximStudenti;
    }

    public void setNrMaximStudenti(int nrMaximStudenti) {
        this.nrMaximStudenti = nrMaximStudenti;
    }

    public int getId_activitate_profesor() {
        return id_activitate_profesor;
    }

    public void setId_activitate_profesor(int id_activitate_profesor) {
        this.id_activitate_profesor = id_activitate_profesor;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    @Override
    public String toString() {
        return "ParticipantActivitate{" +
                "idParticipantActivitate=" + idParticipantActivitate +
                ", nrMaximStudenti=" + nrMaximStudenti +
                ", id_activitate_profesor=" + id_activitate_profesor +
                ", id_student=" + id_student +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipantActivitate that)) return false;
        return idParticipantActivitate == that.idParticipantActivitate && nrMaximStudenti == that.nrMaximStudenti && id_activitate_profesor == that.id_activitate_profesor && id_student == that.id_student;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParticipantActivitate, nrMaximStudenti, id_activitate_profesor, id_student);
    }
}

