package com.example.sql.roles;

public class SuperAdministrator extends User{
    public SuperAdministrator(int idSuperAdmin, String nume, String cnp, String adresa, String email, String nrTelefon, String parola)
    {
        super(idSuperAdmin, nume, cnp, adresa, email, nrTelefon, parola);
    }
}
