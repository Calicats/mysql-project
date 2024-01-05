package com.example;

import java.util.Objects;

public class NoteStudent {
    private int idNota;
    private int nota;
    private int id_student;
    private int id_participant_activitate;

    public NoteStudent(int idNota, int nota, int id_student, int id_participant_activitate) {
        this.idNota = idNota;
        this.nota = nota;
        this.id_student = id_student;
        this.id_participant_activitate = id_participant_activitate;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getId_participant_activitate() {
        return id_participant_activitate;
    }

    public void setId_participant_activitate(int id_participant_activitate) {
        this.id_participant_activitate = id_participant_activitate;
    }

    @Override
    public String toString() {
        return "NoteStudent{" +
                "idNota=" + idNota +
                ", nota=" + nota +
                ", id_student=" + id_student +
                ", id_participant_activitate=" + id_participant_activitate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteStudent that)) return false;
        return idNota == that.idNota && nota == that.nota && id_student == that.id_student && id_participant_activitate == that.id_participant_activitate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNota, nota, id_student, id_participant_activitate);
    }
}

