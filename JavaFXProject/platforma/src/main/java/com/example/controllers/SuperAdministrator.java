package com.example.controllers;

import com.example.User;

public class SuperAdministrator extends User {
    public SuperAdministrator(int idSuperAdmin, String nume, String cnp, String adresa, String email, String nrTelefon, String username, String parola, int idRol)
    {
        super(idSuperAdmin, nume, cnp, adresa, email, nrTelefon, username, parola, idRol);
    }
}
