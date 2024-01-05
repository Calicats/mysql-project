package com.example;

public class Rol {
    private int idRol;
    private String numeRol;

    // Static initialization of predefined roles
    private static final Rol SUPER_ADMIN_ROLE = new Rol(1, "SuperAdmin");
    private static final Rol ADMIN_ROLE = new Rol(2, "Admin");
    private static final Rol PROFESOR_ROLE = new Rol(3, "Profesor");
    private static final Rol STUDENT_ROLE = new Rol(4, "Student");

    // Constructor
    public Rol(int idRol, String numeRol) {
        this.idRol = idRol;
        this.numeRol = numeRol;
    }

    // Getters for predefined roles
    public static Rol getSuperAdminRole() {
        return SUPER_ADMIN_ROLE;
    }

    public static Rol getAdminRole() {
        return ADMIN_ROLE;
    }

    public static Rol getProfesorRole() {
        return PROFESOR_ROLE;
    }

    public static Rol getStudentRole() {
        return STUDENT_ROLE;
    }

    // Getters and Setters for the above fields
}
