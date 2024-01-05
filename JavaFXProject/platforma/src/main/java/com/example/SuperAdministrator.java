package com.example;

import com.example.User;

public class SuperAdministrator extends User {
    public SuperAdministrator(int idSuperAdmin, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol)
    {
        super(idSuperAdmin, cnp, nume, adresa, nrTelefon, email, username, parola, idRol);
    }
}
