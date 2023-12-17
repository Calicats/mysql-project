package com.example.sql.roles;

public class Administrator extends User{
    public Administrator(int idAdmin, String nume, String cnp, String adresa, String email, String nrTelefon, String parola)
    {
        super(idAdmin, nume, cnp, adresa, email, nrTelefon, parola);
    }
}
